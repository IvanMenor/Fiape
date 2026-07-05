package com.example.demo.controller;

import com.example.demo.config.JwtUtil;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("El correo ya está registrado"));
        }
        
        if (request.getDni() != null && !request.getDni().isEmpty() && usuarioRepository.findByDni(request.getDni()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("El DNI ya está registrado"));
        }

        Usuario usuario = new Usuario();
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(request.getContrasena());
        usuario.setDni(request.getDni());
        usuario.setTelefono(request.getTelefono());
        
        String requestedRole = request.getRol();
        if (requestedRole != null && (requestedRole.equalsIgnoreCase("ROLE_ADMIN") || requestedRole.equalsIgnoreCase("ADMIN"))) {
            usuario.setRol("ROLE_ADMIN");
            usuario.setPin(null);
            usuario.setDni(null);
            usuario.setTelefono(null);
            usuario.setNivelActual(null);
            usuario.setPuntos(null);
            usuario.setHistorialCrediticio(null);
            usuario.setReputacion(null);
        } else {
            usuario.setRol("ROLE_USER");
            usuario.setPin("1234"); // PIN por defecto para desembolso
            usuario.setNivelActual(1);
            usuario.setPuntos(100);
            usuario.setReputacion(100);
            usuario.setHistorialCrediticio(false);
        }

        usuarioRepository.save(usuario);
        
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());
        return ResponseEntity.ok(new AuthResponse(token, usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(request.getCorreo());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Usuario o contraseña incorrectos"));
        }

        Usuario usuario = usuarioOpt.get();
        if (!usuario.getContrasena().equals(request.getContrasena())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Usuario o contraseña incorrectos"));
        }

        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());
        return ResponseEntity.ok(new AuthResponse(token, usuario));
    }

    // DTOs
    public static class RegisterRequest {
        private String nombres;
        private String apellidos;
        private String correo;
        
        @JsonProperty("contraseña")
        private String contrasena;
        
        private String dni;
        private String telefono;
        private String rol;

        // Getters & Setters
        public String getNombres() { return nombres; }
        public void setNombres(String nombres) { this.nombres = nombres; }
        public String getApellidos() { return apellidos; }
        public void setApellidos(String apellidos) { this.apellidos = apellidos; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getContrasena() { return contrasena; }
        public void setContrasena(String contrasena) { this.contrasena = contrasena; }
        public String getDni() { return dni; }
        public void setDni(String dni) { this.dni = dni; }
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
        public String getRol() { return rol; }
        public void setRol(String rol) { this.rol = rol; }
    }

    public static class LoginRequest {
        private String correo;
        
        @JsonProperty("contraseña")
        private String contrasena;

        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getContrasena() { return contrasena; }
        public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    }

    public static class AuthResponse {
        private String token;
        private Long id;
        private String nombres;
        private String apellidos;
        private String correo;
        private String dni;
        private String telefono;
        private String rol;
        private Integer nivelActual;
        private Integer puntos;
        private Boolean historialCrediticio;

        public AuthResponse(String token, Usuario usuario) {
            this.token = token;
            this.id = usuario.getId();
            this.nombres = usuario.getNombres();
            this.apellidos = usuario.getApellidos();
            this.correo = usuario.getCorreo();
            this.dni = usuario.getDni();
            this.telefono = usuario.getTelefono();
            this.rol = usuario.getRol();
            this.nivelActual = usuario.getNivelActual();
            this.puntos = usuario.getPuntos();
            this.historialCrediticio = usuario.getHistorialCrediticio();
        }

        public String getToken() { return token; }
        public Long getId() { return id; }
        public String getNombres() { return nombres; }
        public String getApellidos() { return apellidos; }
        public String getCorreo() { return correo; }
        public String getDni() { return dni; }
        public String getTelefono() { return telefono; }
        public String getRol() { return rol; }
        public Integer getNivelActual() { return nivelActual; }
        public Integer getPuntos() { return puntos; }
        public Boolean getHistorialCrediticio() { return historialCrediticio; }
    }

    public static class MessageResponse {
        private String mensaje;
        public MessageResponse(String mensaje) { this.mensaje = mensaje; }
        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    }
}