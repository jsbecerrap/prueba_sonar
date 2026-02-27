package co.edu.unbosque.dto.request;


import jakarta.validation.constraints.NotBlank;

public class UsuarioRequestDTO {

    @NotBlank
    private String correoUsuario;

    @NotBlank
    private String contrasena;

    private boolean admin;

    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
}