package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.repository.UsuarioRepository;
@Service
public class UsuarioDetalleService implements UserDetailsService{


    @Autowired
    private UsuarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     

        Optional<Usuario> usOptional=repository.findByUsuario(username);
        if(!usOptional.isPresent()){

            throw new UsernameNotFoundException("No existe el usuario en el sistema" + username);
        }
            Usuario u = usOptional.get();
            List<GrantedAuthority> authorities = u.getRoles().stream()
            .map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
           return new org.springframework.security.core.userdetails.User(
    u.getUsuario(),
    u.getContrasena(),
    u.isActivo(),
    true,
    true,
    true,
    authorities
);
    }

    }


