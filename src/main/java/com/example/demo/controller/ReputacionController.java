package com.example.demo.controller;

import com.example.demo.service.ReputacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReputacionController {

    @Autowired
    private ReputacionService reputacionService;

    @GetMapping("/reputacion")
    public String reputacion(Model model) {

        Long usuarioId = 1L;

        int progreso =
                reputacionService.obtenerProgreso(usuarioId);

        model.addAttribute("progreso", progreso);

        return "reputacion";
    }
}