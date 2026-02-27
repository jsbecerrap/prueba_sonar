package co.edu.unbosque.service;

import java.util.List;

import co.edu.unbosque.entity.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    
}
