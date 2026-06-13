package com.example.demo.service;

import com.example.demo.entity.Credito;
import com.example.demo.repository.CreditoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditoService {

    @Autowired
    private CreditoRepository creditoRepository;

    public Optional<Credito> buscarPorId(Long id) {
        return creditoRepository.findById(id);
    }

    public Credito guardar(Credito credito) {
        return creditoRepository.save(credito);
    }
}