package com.example.demo.service;

import com.example.demo.entity.Credito;
import com.example.demo.entity.Desembolso;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.CreditoRepository;
import com.example.demo.repository.DesembolsoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DesembolsoService {

    @Autowired
    private DesembolsoRepository desembolsoRepository;

    @Autowired
    private CreditoRepository creditoRepository;

    public boolean validarPin(Usuario usuario, String pin) {

        return usuario.getPin().equals(pin);
    }

    public Desembolso desembolsar(Credito credito) {

        credito.setEstado("DESEMBOLSADO");
        creditoRepository.save(credito);

        Desembolso desembolso = new Desembolso();

        desembolso.setCredito(credito);
        desembolso.setMonto(credito.getMonto());
        desembolso.setEstado("DESEMBOLSADO");
        desembolso.setFechaDesembolso(LocalDateTime.now());

        return desembolsoRepository.save(desembolso);
    }
}