package com.example.demo.ia;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AsistenteIAService {

    private final Random random = new Random();

    public ResultadoIA evaluarCliente(String dni) {

        ResultadoIA resultado = new ResultadoIA();

        int opcion = random.nextInt(2);

        if (opcion == 0) {

            resultado.setNivel(1);
            resultado.setMonto(50.0);
            resultado.setModalidad("Crédito de Confianza");

            resultado.setExplicacion(
                    "No se encontró historial crediticio externo. "
                            + "Se aprueba un crédito inicial basado en el modelo de reputación."
            );

            resultado.setRecomendacion(
                    "Realiza tus pagos puntualmente para acceder al Nivel 2."
            );

        } else {

            resultado.setNivel(3);
            resultado.setMonto(200.0);
            resultado.setModalidad("Historial Crediticio");

            resultado.setExplicacion(
                    "Se detectó un historial crediticio favorable en fuentes externas."
            );

            resultado.setRecomendacion(
                    "Mantén tus pagos puntuales para conservar tu nivel."
            );
        }

        return resultado;
    }
}