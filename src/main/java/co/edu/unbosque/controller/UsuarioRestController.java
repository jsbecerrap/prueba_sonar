package co.edu.unbosque.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService service;


    @GetMapping
    public List<Usuario> listar(){
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){
         if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
    }
     private ResponseEntity<?> validation(BindingResult result) {
    Map<String,String> json_errors = new HashMap<>();
   result.getFieldErrors().forEach(err -> {
    json_errors.put(
        err.getField(),
        "el campo " + err.getField() + " " + err.getDefaultMessage()
    );
});
return ResponseEntity.status(400).body(json_errors);
    
    }
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario, BindingResult result){
         if(result.hasFieldErrors()){
            return validation(result);
        }
        usuario.setAdmin(false);
        return crear(usuario,result);
    }
}
