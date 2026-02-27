package co.edu.unbosque.service;

import java.util.List;
import co.edu.unbosque.dto.request.UsuarioRequestDTO;
import co.edu.unbosque.dto.response.UsuarioResponseDTO;

public interface UsuarioService {
    List<UsuarioResponseDTO> findAll();
    UsuarioResponseDTO save(UsuarioRequestDTO dto);
}