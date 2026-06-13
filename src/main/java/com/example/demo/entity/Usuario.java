package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false, length = 4)
    private String pin;

    private Integer reputacion;

    private Integer nivelActual;

    public Usuario() {
    }

    public Usuario(String nombres, String dni, String telefono, String pin,
                   Integer reputacion, Integer nivelActual) {
        this.nombres = nombres;
        this.dni = dni;
        this.telefono = telefono;
        this.pin = pin;
        this.reputacion = reputacion;
        this.nivelActual = nivelActual;
    }

    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getReputacion() {
        return reputacion;
    }

    public void setReputacion(Integer reputacion) {
        this.reputacion = reputacion;
    }

    public Integer getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(Integer nivelActual) {
        this.nivelActual = nivelActual;
    }
}