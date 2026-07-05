package com.example.demo.controller;

import com.example.demo.entity.Credito;
import com.example.demo.entity.Pago;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.CreditoRepository;
import com.example.demo.repository.PagoRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.CreditoService;
import com.example.demo.service.DesembolsoService;
import com.example.demo.ia.AsistenteIAService;
import com.example.demo.service.ReputacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/creditos")
@CrossOrigin(origins = "*")
public class CreditoController {

    @Autowired
    private CreditoService creditoService;

    @Autowired
    private DesembolsoService desembolsoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private AsistenteIAService iaService;

    @Autowired
    private ReputacionService reputacionService;

    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitar(@RequestBody SolicitudRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(request.getUsuarioId());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Usuario no encontrado"));
        }

        Usuario usuario = usuarioOpt.get();

        // Validar regla de negocio: no puede solicitar otro préstamo si tiene uno activo (no pagado/rechazado)
        List<Credito> creditos = creditoRepository.findByUsuario(usuario);
        boolean tieneActivo = creditos.stream()
                .anyMatch(c -> c.getEstado().equalsIgnoreCase("PENDIENTE_DESEMBOLSO") 
                            || c.getEstado().equalsIgnoreCase("DESEMBOLSADO"));

        if (tieneActivo) {
            return ResponseEntity.badRequest().body(new MessageResponse("Ya cuentas con un préstamo activo. Debes cancelarlo antes de solicitar uno nuevo."));
        }

        // Validar que el monto no exceda el límite del nivel
        double montoMaximo = iaService.obtenerMontoPorNivel(usuario.getNivelActual());
        if (request.getMonto() > montoMaximo) {
            return ResponseEntity.badRequest().body(new MessageResponse("El monto solicitado supera el límite permitido de S/. " + montoMaximo + " para tu Nivel " + usuario.getNivelActual()));
        }

        Credito credito = new Credito();
        credito.setUsuario(usuario);
        credito.setMonto(request.getMonto());
        credito.setNivel(usuario.getNivelActual());
        credito.setEstado("PENDIENTE_DESEMBOLSO");
        credito.setFechaSolicitud(LocalDate.now());
        credito.setFechaVencimiento(LocalDate.now().plusDays(30)); // Plazo de 30 días

        creditoService.guardar(credito);
        return ResponseEntity.ok(credito);
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestBody ConfirmarRequest request) {
        Optional<Credito> creditoOpt = creditoService.buscarPorId(request.getCreditoId());
        if (creditoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Crédito no encontrado"));
        }

        Credito credito = creditoOpt.get();
        
        if (!credito.getEstado().equals("PENDIENTE_DESEMBOLSO")) {
            return ResponseEntity.badRequest().body(new MessageResponse("El crédito no está en estado pendiente de desembolso"));
        }

        Usuario usuario = credito.getUsuario();
        boolean valido = desembolsoService.validarPin(usuario, request.getPin());

        if (!valido) {
            return ResponseEntity.badRequest().body(new MessageResponse("PIN incorrecto"));
        }

        desembolsoService.desembolsar(credito);
        return ResponseEntity.ok(credito);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerCreditosUsuario(@PathVariable Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Usuario no encontrado"));
        }

        List<Credito> creditos = creditoRepository.findByUsuario(usuarioOpt.get());
        List<CreditoDto> dtos = creditos.stream().map(c -> {
            double tasa = iaService.obtenerTasaPorNivel(c.getNivel());
            double interes = c.getMonto() * tasa;
            double totalPagar = c.getMonto() + interes;
            return new CreditoDto(
                    c.getId(),
                    c.getMonto(),
                    c.getEstado(),
                    c.getFechaSolicitud(),
                    c.getFechaVencimiento(),
                    tasa,
                    interes,
                    totalPagar
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<?> pagarPrestamo(@PathVariable Long id) {
        Optional<Credito> creditoOpt = creditoService.buscarPorId(id);
        if (creditoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Crédito no encontrado"));
        }

        Credito credito = creditoOpt.get();
        if (!credito.getEstado().equalsIgnoreCase("DESEMBOLSADO")) {
            return ResponseEntity.badRequest().body(new MessageResponse("El préstamo no está desembolsado o ya fue pagado"));
        }

        // Marcar préstamo como PAGADO
        credito.setEstado("PAGADO");
        creditoRepository.save(credito);

        // Calcular total a pagar
        double tasa = iaService.obtenerTasaPorNivel(credito.getNivel());
        double totalPagar = credito.getMonto() * (1 + tasa);

        // Guardar el registro de Pago
        Pago pago = new Pago();
        pago.setCredito(credito);
        pago.setMonto(totalPagar);
        pago.setFechaPago(LocalDate.now());
        pago.setPuntual(true);
        pagoRepository.save(pago);

        // Actualizar nivel, puntos y reputación
        reputacionService.registrarPagoExitoso(credito.getUsuario());

        return ResponseEntity.ok(new MessageResponse("Pago procesado con éxito. ¡Felicidades! Has ganado puntos y subido de nivel."));
    }

    // DTOs
    public static class SolicitudRequest {
        private Long usuarioId;
        private Double monto;

        public Long getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
        public Double getMonto() { return monto; }
        public void setMonto(Double monto) { this.monto = monto; }
    }

    public static class ConfirmarRequest {
        private Long creditoId;
        private String pin;

        public Long getCreditoId() { return creditoId; }
        public void setCreditoId(Long creditoId) { this.creditoId = creditoId; }
        public String getPin() { return pin; }
        public void setPin(String pin) { this.pin = pin; }
    }

    public static class CreditoDto {
        private Long id;
        private Double monto;
        private String estado;
        private LocalDate fechaSolicitud;
        private LocalDate fechaVencimiento;
        private Double tasa;
        private Double interes;
        private Double totalPagar;

        public CreditoDto(Long id, Double monto, String estado, LocalDate fechaSolicitud, LocalDate fechaVencimiento, Double tasa, Double interes, Double totalPagar) {
            this.id = id;
            this.monto = monto;
            this.estado = estado;
            this.fechaSolicitud = fechaSolicitud;
            this.fechaVencimiento = fechaVencimiento;
            this.tasa = tasa;
            this.interes = interes;
            this.totalPagar = totalPagar;
        }

        public Long getId() { return id; }
        public Double getMonto() { return monto; }
        public String getEstado() { return estado; }
        public LocalDate getFechaSolicitud() { return fechaSolicitud; }
        public LocalDate getFechaVencimiento() { return fechaVencimiento; }
        public Double getTasa() { return tasa; }
        public Double getInteres() { return interes; }
        public Double getTotalPagar() { return totalPagar; }
    }

    public static class MessageResponse {
        private String mensaje;
        public MessageResponse(String mensaje) { this.mensaje = mensaje; }
        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    }
}