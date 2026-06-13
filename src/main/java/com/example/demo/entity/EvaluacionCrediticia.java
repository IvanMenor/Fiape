package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluaciones")
public class EvaluacionCrediticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dni;

    private Integer nivelAsignado;

    private Double montoAprobado;

    private String modalidad;

    @Column(length = 500)
    private String explicacionIA;

    @Column(length = 500)
    private String recomendacionIA;

    private LocalDateTime fechaEvaluacion;

    public EvaluacionCrediticia() {
    }

    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getNivelAsignado() {
        return nivelAsignado;
    }

    public void setNivelAsignado(Integer nivelAsignado) {
        this.nivelAsignado = nivelAsignado;
    }

    public Double getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(Double montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getExplicacionIA() {
        return explicacionIA;
    }

    public void setExplicacionIA(String explicacionIA) {
        this.explicacionIA = explicacionIA;
    }

    public String getRecomendacionIA() {
        return recomendacionIA;
    }

    public void setRecomendacionIA(String recomendacionIA) {
        this.recomendacionIA = recomendacionIA;
    }

    public LocalDateTime getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDateTime fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }
}