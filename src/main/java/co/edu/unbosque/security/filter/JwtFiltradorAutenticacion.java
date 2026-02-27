package co.edu.unbosque.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import co.edu.unbosque.entity.Usuario;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;
import static co.edu.unbosque.security.TokenJwt.*;
public class JwtFiltradorAutenticacion extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;


    public JwtFiltradorAutenticacion(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
                Usuario u =null;

                String correo_usuario=null;
                String contrasena=null;

                try {
                    u= new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
                    correo_usuario=u.getUsuario();
                contrasena=u.getContrasena();
                } catch ( IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(correo_usuario, contrasena);
                return authenticationManager.authenticate(authenticationToken);
    
            }
@Override
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException, ServletException {

    String username = authResult.getName();
    Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();


    String token = Jwts.builder()
    .subject(username)
    .claim("authorities", roles.stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList()))
    .signWith(SECRET_KEY)
    .compact();
    
    response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token); 
    
    Map<String, String> json = new HashMap<>();
    json.put("token", token);
    json.put("username", username);
    json.put("mensaje", "Hola " + username + " sesión iniciada correctamente");
    
    response.getWriter().write(new ObjectMapper().writeValueAsString(json));
    response.setContentType(CONTENT_TYPE);
}

 @Override
 protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
     Map<String, String> json = new HashMap<>();
     json.put("Message", "Error en la autenticacion username/password incorrecto");
     json.put("error", failed.getMessage());
     response.getWriter().write(new ObjectMapper().writeValueAsString(json));
     response.setContentType(CONTENT_TYPE);
     response.setStatus(401);
            
 }

   
}
