package com.example.demo.service;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.PagoRepository;
import com.example.demo.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReputacionService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void actualizarNivel(Usuario usuario) {

        long pagosPuntuales =
                pagoRepository.countByCreditoUsuarioIdAndPuntualTrue(
                        usuario.getId());

        if (pagosPuntuales >= 3 &&
                usuario.getNivelActual() == 1) {

            usuario.setNivelActual(2);
            usuarioRepository.save(usuario);
        }
    }

    public int obtenerProgreso(Long usuarioId) {

        long pagos =
                pagoRepository.countByCreditoUsuarioIdAndPuntualTrue(usuarioId);

        return (int) Math.min((pagos * 100) / 3, 100);
    }
}