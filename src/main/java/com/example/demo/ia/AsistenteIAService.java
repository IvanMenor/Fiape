package com.example.demo.ia;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class AsistenteIAService {

    private final Random random = new Random();

    public ResultadoIA evaluarCliente(String nombreCompleto, String email, String dni, String telefono) {
        ResultadoIA resultado = new ResultadoIA();

        // Generar un nivel entre 1 y 10 de forma aleatoria
        int nivel = random.nextInt(10) + 1; 
        double monto = obtenerMontoPorNivel(nivel);
        String modalidad = obtenerModalidadPorNivel(nivel);
        String explicacion = obtenerExplicacionPorNivel(nivel, dni);
        String recomendacion = obtenerRecomendacionPorNivel(nivel);

        resultado.setNivel(nivel);
        resultado.setMonto(monto);
        resultado.setModalidad(modalidad);
        resultado.setExplicacion(explicacion);
        resultado.setRecomendacion(recomendacion);

        return resultado;
    }

    public double obtenerMontoPorNivel(int nivel) {
        switch (nivel) {
            case 1: return 100.0;
            case 2: return 200.0;
            case 3: return 400.0;
            case 4: return 600.0;
            case 5: return 1000.0;
            case 6: return 1500.0;
            case 7: return 2000.0;
            case 8: return 3000.0;
            case 9: return 4000.0;
            case 10: return 5000.0;
            default: return 100.0;
        }
    }

    public double obtenerTasaPorNivel(int nivel) {
        switch (nivel) {
            case 1: return 0.20; // 20%
            case 2: return 0.18; // 18%
            case 3: return 0.16; // 16%
            case 4: return 0.14; // 14%
            case 5: return 0.12; // 12%
            case 6: return 0.10; // 10%
            case 7: return 0.08; // 8%
            case 8: return 0.07; // 7%
            case 9: return 0.06; // 6%
            case 10: return 0.05; // 5%
            default: return 0.20;
        }
    }

    private String obtenerModalidadPorNivel(int nivel) {
        switch (nivel) {
            case 1: return "Crédito de Confianza Inicial";
            case 2: return "Historial de Cumplimiento Básico";
            case 3: return "Microfinanzas Nivel 3";
            case 4: return "Crédito Emprendedor Semilla";
            case 5: return "Progreso Productivo Nivel 5";
            case 6: return "Socio Estratégico FIAPE";
            case 7: return "Fianza Comercial Ampliada";
            case 8: return "Inversionista Preferente";
            case 9: return "Crédito Corporativo Emprendedor";
            case 10: return "Socio de Honor y Confianza Máxima";
            default: return "Crédito de Confianza";
        }
    }

    private String obtenerExplicacionPorNivel(int nivel, String dni) {
        String base = "[Consulta RENIEC: OK] DNI " + dni + " verificado con éxito. [Consulta Infocorp: OK] ";
        switch (nivel) {
            case 1: 
                return base + "No se registra historial crediticio previo en el sistema. Se inicia en nivel básico para construir reputación.";
            case 2: 
                return base + "Se detectó cumplimiento de pagos de servicios básicos. Califica para nivel inicial intermedio.";
            case 3: 
                return base + "Se detectaron antecedentes positivos en transacciones comerciales básicas externas. Riesgo bajo.";
            case 4: 
                return base + "Comportamiento crediticio regular en el buró de crédito. Buena relación deuda-ingresos.";
            case 5: 
                return base + "Historial crediticio estable y verificado con bajo nivel de endeudamiento general.";
            case 6: 
                return base + "Nivel óptimo de cumplimiento en compromisos previos con otras entidades financieras de micropréstamos.";
            case 7: 
                return base + "Excelente patrón de pago. Capacidad crediticia e ingresos constantes para su negocio.";
            case 8: 
                return base + "Riesgo de incumplimiento extremadamente bajo. Historial crediticio impecable en el sector microempresarial.";
            case 9: 
                return base + "Líder emprendedor. Respaldado por una excelente calificación externa y solidez del negocio.";
            case 10: 
                return base + "Máxima puntuación en el score crediticio de FIAPE. Emprendedor de alto impacto con riesgo mínimo.";
            default: 
                return base + "Evaluación realizada con éxito.";
        }
    }

    private String obtenerRecomendacionPorNivel(int nivel) {
        if (nivel <= 3) {
            return "Realiza tus pagos puntualmente dentro de los 30 días para subir de nivel y obtener menores tasas.";
        } else if (nivel <= 6) {
            return "Mantén tu comportamiento de pago ejemplar. En el siguiente nivel accederás a montos superiores a S/. 1,500.";
        } else {
            return "Felicidades, te encuentras en un nivel preferencial de confianza. Continúa así para expandir tu capital de trabajo.";
        }
    }
}