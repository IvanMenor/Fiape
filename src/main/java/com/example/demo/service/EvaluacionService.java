package com.example.demo.service;

import com.example.demo.entity.EvaluacionCrediticia;
import com.example.demo.ia.AsistenteIAService;
import com.example.demo.ia.ResultadoIA;
import com.example.demo.repository.EvaluacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EvaluacionService {

    @Autowired
    private AsistenteIAService iaService;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private com.example.demo.repository.UsuarioRepository usuarioRepository;

    public EvaluacionCrediticia evaluar(String nombres, String apellidos, String correo, String dni, String telefono) {

        ResultadoIA resultadoIA =
                iaService.evaluarCliente(nombres + " " + apellidos, correo, dni, telefono);

        EvaluacionCrediticia evaluacion =
                new EvaluacionCrediticia();

        evaluacion.setDni(dni);
        evaluacion.setNivelAsignado(resultadoIA.getNivel());
        evaluacion.setMontoAprobado(resultadoIA.getMonto());
        evaluacion.setModalidad(resultadoIA.getModalidad());
        evaluacion.setExplicacionIA(resultadoIA.getExplicacion());
        evaluacion.setRecomendacionIA(resultadoIA.getRecomendacion());
        evaluacion.setFechaEvaluacion(LocalDateTime.now());

        // Si el usuario ya está registrado, le actualizamos su nivel asignado por la IA
        usuarioRepository.findByDni(dni).ifPresent(usuario -> {
            usuario.setNivelActual(resultadoIA.getNivel());
            usuario.setHistorialCrediticio(resultadoIA.getNivel() > 1);
            usuarioRepository.save(usuario);
        });

        return evaluacionRepository.save(evaluacion);
    }
}