package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.entity.Rol;
import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.repository.RolRepository;
import co.edu.unbosque.repository.UsuarioRepository;
@Service
public class UsuarioServiceImpl implements  UsuarioService{

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
     @Transactional(readOnly = true)
    public List<Usuario> findAll() {
       return (List<Usuario>)repository.findAll();
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        Optional<Rol> rolOptional =rolRepository.findByNombre("ROLE_USUARIO");
        List<Rol> listaRoles = new ArrayList<>();
        listaRoles.add(rolOptional.get());

        if(usuario.isAdmin()){
               Optional<Rol> rolOptional_admin =rolRepository.findByNombre("ROLE_ADMIN");
               listaRoles.add(rolOptional_admin.get());
        }
        usuario.setRoles(listaRoles);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
     return repository.save(usuario);
    }

}
