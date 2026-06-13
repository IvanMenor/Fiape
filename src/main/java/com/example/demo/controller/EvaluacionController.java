package com.example.demo.controller;

import com.example.demo.entity.EvaluacionCrediticia;
import com.example.demo.service.EvaluacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @GetMapping("/evaluacion")
    public String mostrarFormulario() {
        return "evaluacion";
    }

    @PostMapping("/evaluacion")
    public String evaluar(
            @RequestParam String dni,
            Model model) {

        EvaluacionCrediticia resultado =
                evaluacionService.evaluar(dni);

        model.addAttribute("resultado", resultado);

        return "resultado-evaluacion";
    }
}