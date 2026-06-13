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

    public EvaluacionCrediticia evaluar(String dni) {

        ResultadoIA resultadoIA =
                iaService.evaluarCliente(dni);

        EvaluacionCrediticia evaluacion =
                new EvaluacionCrediticia();

        evaluacion.setDni(dni);
        evaluacion.setNivelAsignado(resultadoIA.getNivel());
        evaluacion.setMontoAprobado(resultadoIA.getMonto());
        evaluacion.setModalidad(resultadoIA.getModalidad());
        evaluacion.setExplicacionIA(resultadoIA.getExplicacion());
        evaluacion.setRecomendacionIA(resultadoIA.getRecomendacion());
        evaluacion.setFechaEvaluacion(LocalDateTime.now());

        return evaluacionRepository.save(evaluacion);
    }
}