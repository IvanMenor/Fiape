package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "desembolsos")
public class Desembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "credito_id")
    private Credito credito;

    private Double monto;

    private LocalDateTime fechaDesembolso;

    private String estado;

    public Desembolso() {
    }

    public Long getId() {
        return id;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(LocalDateTime fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}