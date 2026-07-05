package com.example.demo.controller;

import com.example.demo.entity.EvaluacionCrediticia;
import com.example.demo.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluacion")
@CrossOrigin(origins = "*")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping("/evaluar")
    public ResponseEntity<EvaluacionCrediticia> evaluar(@RequestBody EvaluacionRequest request) {
        EvaluacionCrediticia resultado = evaluacionService.evaluar(
                request.getNombres(),
                request.getApellidos(),
                request.getCorreo(),
                request.getDni(),
                request.getTelefono()
        );
        return ResponseEntity.ok(resultado);
    }

    // DTO para recibir la solicitud
    public static class EvaluacionRequest {
        private String nombres;
        private String apellidos;
        private String correo;
        private String dni;
        private String telefono;

        // Getters & Setters
        public String getNombres() { return nombres; }
        public void setNombres(String nombres) { this.nombres = nombres; }
        public String getApellidos() { return apellidos; }
        public void setApellidos(String apellidos) { this.apellidos = apellidos; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getDni() { return dni; }
        public void setDni(String dni) { this.dni = dni; }
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }
}