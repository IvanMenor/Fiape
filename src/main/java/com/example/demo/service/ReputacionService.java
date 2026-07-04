package com.example.demo.service;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReputacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void registrarPagoExitoso(Usuario usuario) {
        // Sumar 50 puntos por pago puntual
        usuario.setPuntos(usuario.getPuntos() + 50);
        
        // Subir reputación
        usuario.setReputacion(Math.min(usuario.getReputacion() + 10, 100));

        // Subir de nivel (máximo 10)
        if (usuario.getNivelActual() < 10) {
            usuario.setNivelActual(usuario.getNivelActual() + 1);
        }

        usuarioRepository.save(usuario);
    }

    public void actualizarNivel(Usuario usuario) {
        // Se mantiene por retrocompatibilidad
        registrarPagoExitoso(usuario);
    }

    public int obtenerProgreso(Long usuarioId) {
        // Progreso basado en los puntos hacia la siguiente centena (ej: 150 puntos -> 50% de progreso)
        return usuarioRepository.findById(usuarioId)
                .map(u -> u.getPuntos() % 100)
                .orElse(0);
    }
}