package co.edu.unbosque.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.dto.request.UsuarioRequestDTO;
import co.edu.unbosque.dto.response.UsuarioResponseDTO;
import co.edu.unbosque.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    private final UsuarioService service;

    public UsuarioRestController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> crear(@Valid @RequestBody UsuarioRequestDTO dto, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> registrar(@Valid @RequestBody UsuarioRequestDTO dto, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        dto.setAdmin(false);
        return crear(dto, result);
    }

    private ResponseEntity<Object> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->
            errors.put(err.getField(), "el campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.status(400).body(errors);
    }
}