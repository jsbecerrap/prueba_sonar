package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.entity.Rol;
import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.repository.RolRepository;
import co.edu.unbosque.repository.UsuarioRepository;
@Service
public class UsuarioServiceImpl implements  UsuarioService{

    
    private UsuarioRepository repository;
    
    private RolRepository rolRepository;
    
    private PasswordEncoder passwordEncoder;

 
    public UsuarioServiceImpl(UsuarioRepository repository, 
                               RolRepository rolRepository, 
                               PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
               Optional<Rol> rolOptionalAdmin  =rolRepository.findByNombre("ROLE_ADMIN");
               listaRoles.add(rolOptionalAdmin .get());
        }
        usuario.setRoles(listaRoles);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
     return repository.save(usuario);
    }

}
