package com.example.demo.controller;

import com.example.demo.entity.Credito;
import com.example.demo.entity.Usuario;
import com.example.demo.service.CreditoService;
import com.example.demo.service.DesembolsoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CreditoController {

    @Autowired
    private CreditoService creditoService;

    @Autowired
    private DesembolsoService desembolsoService;

    @GetMapping("/credito/pin")
    public String mostrarPin() {

        return "validar-pin";
    }

    @PostMapping("/credito/confirmar")
    public String confirmar(
            @RequestParam Long creditoId,
            @RequestParam String pin,
            Model model) {

        Credito credito =
                creditoService.buscarPorId(creditoId)
                        .orElse(null);

        if (credito == null) {

            model.addAttribute(
                    "error",
                    "Crédito no encontrado");

            return "validar-pin";
        }

        Usuario usuario =
                credito.getUsuario();

        boolean valido =
                desembolsoService
                        .validarPin(usuario, pin);

        if (!valido) {

            model.addAttribute(
                    "error",
                    "PIN incorrecto");

            return "validar-pin";
        }

        desembolsoService.desembolsar(credito);

        model.addAttribute("credito", credito);

        return "operacion-exitosa";
    }
}