package co.edu.unbosque.dto.response;


import java.util.List;

public class UsuarioResponseDTO {

    private Long id;
    private String correoUsuario;
    private List<String> roles;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}