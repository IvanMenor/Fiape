package com.example.demo.controller;

import com.example.demo.entity.Credito;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.CreditoRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.ia.AsistenteIAService;
import com.example.demo.service.ReputacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private AsistenteIAService iaService;

    @Autowired
    private ReputacionService reputacionService;

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<?> getDashboard(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOpt.get();
        
        // Calcular préstamo activo
        List<Credito> creditos = creditoRepository.findByUsuario(usuario);
        Credito prestamoActivo = creditos.stream()
                .filter(c -> c.getEstado().equalsIgnoreCase("PENDIENTE_DESEMBOLSO") 
                          || c.getEstado().equalsIgnoreCase("DESEMBOLSADO"))
                .findFirst()
                .orElse(null);

        DashboardResponse response = new DashboardResponse();
        response.setId(usuario.getId());
        response.setNombreCompleto(usuario.getNombres() + " " + (usuario.getApellidos() != null ? usuario.getApellidos() : ""));
        response.setCorreo(usuario.getCorreo());
        response.setDni(usuario.getDni());
        response.setTelefono(usuario.getTelefono());
        response.setNivelActual(usuario.getNivelActual());
        response.setPuntos(usuario.getPuntos());
        response.setHistorialCrediticio(usuario.getHistorialCrediticio() != null ? usuario.getHistorialCrediticio() : false);
        
        if (usuario.getNivelActual() != null) {
            response.setMontoDisponible(iaService.obtenerMontoPorNivel(usuario.getNivelActual()));
            response.setTasaInteres(iaService.obtenerTasaPorNivel(usuario.getNivelActual()));
        } else {
            response.setMontoDisponible(0.0);
            response.setTasaInteres(0.0);
        }

        response.setProgresoNivel(reputacionService.obtenerProgreso(usuario.getId()));
        
        if (prestamoActivo != null) {
            double tasa = iaService.obtenerTasaPorNivel(prestamoActivo.getNivel());
            double interes = prestamoActivo.getMonto() * tasa;
            double totalPagar = prestamoActivo.getMonto() + interes;
            
            PrestamoActivoDto dto = new PrestamoActivoDto(
                    prestamoActivo.getId(),
                    prestamoActivo.getMonto(),
                    prestamoActivo.getEstado(),
                    prestamoActivo.getFechaSolicitud(),
                    prestamoActivo.getFechaVencimiento(),
                    tasa,
                    interes,
                    totalPagar
            );
            response.setPrestamoActivo(dto);
        } else {
            response.setPrestamoActivo(null);
        }

        return ResponseEntity.ok(response);
    }

    // DTOs
    public static class DashboardResponse {
        private Long id;
        private String nombreCompleto;
        private String correo;
        private String dni;
        private String telefono;
        private Integer nivelActual;
        private Integer puntos;
        private Boolean historialCrediticio;
        private Double montoDisponible;
        private Double tasaInteres;
        private Integer progresoNivel;
        private PrestamoActivoDto prestamoActivo;

        // Getters & Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNombreCompleto() { return nombreCompleto; }
        public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getDni() { return dni; }
        public void setDni(String dni) { this.dni = dni; }
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
        public Integer getNivelActual() { return nivelActual; }
        public void setNivelActual(Integer nivelActual) { this.nivelActual = nivelActual; }
        public Integer getPuntos() { return puntos; }
        public void setPuntos(Integer puntos) { this.puntos = puntos; }
        public Boolean getHistorialCrediticio() { return historialCrediticio; }
        public void setHistorialCrediticio(Boolean historialCrediticio) { this.historialCrediticio = historialCrediticio; }
        public Double getMontoDisponible() { return montoDisponible; }
        public void setMontoDisponible(Double montoDisponible) { this.montoDisponible = montoDisponible; }
        public Double getTasaInteres() { return tasaInteres; }
        public void setTasaInteres(Double tasaInteres) { this.tasaInteres = tasaInteres; }
        public Integer getProgresoNivel() { return progresoNivel; }
        public void setProgresoNivel(Integer progresoNivel) { this.progresoNivel = progresoNivel; }
        public PrestamoActivoDto getPrestamoActivo() { return prestamoActivo; }
        public void setPrestamoActivo(PrestamoActivoDto prestamoActivo) { this.prestamoActivo = prestamoActivo; }
    }

    public static class PrestamoActivoDto {
        private Long id;
        private Double monto;
        private String estado;
        private java.time.LocalDate fechaSolicitud;
        private java.time.LocalDate fechaVencimiento;
        private Double tasa;
        private Double interes;
        private Double totalPagar;

        public PrestamoActivoDto(Long id, Double monto, String estado, java.time.LocalDate fechaSolicitud, java.time.LocalDate fechaVencimiento, Double tasa, Double interes, Double totalPagar) {
            this.id = id;
            this.monto = monto;
            this.estado = estado;
            this.fechaSolicitud = fechaSolicitud;
            this.fechaVencimiento = fechaVencimiento;
            this.tasa = tasa;
            this.interes = interes;
            this.totalPagar = totalPagar;
        }

        // Getters
        public Long getId() { return id; }
        public Double getMonto() { return monto; }
        public String getEstado() { return estado; }
        public java.time.LocalDate getFechaSolicitud() { return fechaSolicitud; }
        public java.time.LocalDate getFechaVencimiento() { return fechaVencimiento; }
        public Double getTasa() { return tasa; }
        public Double getInteres() { return interes; }
        public Double getTotalPagar() { return totalPagar; }
    }
}