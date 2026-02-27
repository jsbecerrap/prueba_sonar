package co.edu.unbosque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.unbosque.dto.request.UsuarioRequestDTO;
import co.edu.unbosque.dto.response.UsuarioResponseDTO;
import co.edu.unbosque.entity.Rol;
import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.repository.RolRepository;
import co.edu.unbosque.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repository,
                               RolRepository rolRepository,
                               PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

   @Override
@Transactional(readOnly = true)
public List<UsuarioResponseDTO> findAll() {
    List<Usuario> usuarios = (List<Usuario>) repository.findAll();
    return usuarios.stream()
            .map(this::toResponseDTO)
            .toList();
}

    @Override
    @Transactional
    public UsuarioResponseDTO save(UsuarioRequestDTO dto) {
        Optional<Rol> rolOptional = rolRepository.findByNombre("ROLE_USUARIO");
        List<Rol> listaRoles = new ArrayList<>();
        listaRoles.add(rolOptional.get());

        if (dto.isAdmin()) {
            Optional<Rol> rolOptionalAdmin = rolRepository.findByNombre("ROLE_ADMIN");
            listaRoles.add(rolOptionalAdmin.get());
        }

        Usuario usuario = new Usuario();
        usuario.setCorreoUsuario(dto.getCorreoUsuario());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRoles(listaRoles);

        return toResponseDTO(repository.save(usuario));
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setCorreoUsuario(usuario.getCorreoUsuario());
        dto.setRoles(usuario.getRoles().stream()
                .map(Rol::getNombre)
                .toList());
        return dto;
    }
}