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

    private String apellidos;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = true, length = 8) // Nullable for admin who might not need DNI
    private String dni;

    @Column(nullable = true) // Nullable for admin who might not need phone
    private String telefono;

    @Column(nullable = true, length = 4) // Nullable for admin, defaults to "1234" for users
    private String pin;

    private Integer reputacion;

    private Integer nivelActual;

    private Integer puntos;

    private Boolean historialCrediticio;

    private String rol; // ROLE_USER or ROLE_ADMIN

    public Usuario() {
        this.reputacion = 100;
        this.nivelActual = 1;
        this.puntos = 100;
        this.historialCrediticio = false;
        this.rol = "ROLE_USER";
        this.pin = "1234";
    }

    public Usuario(String nombres, String apellidos, String correo, String contrasena, String dni, String telefono,
                   String pin, Integer reputacion, Integer nivelActual, Integer puntos, Boolean historialCrediticio, String rol) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasena = contrasena;
        this.dni = dni;
        this.telefono = telefono;
        this.pin = pin != null ? pin : "1234";
        this.reputacion = reputacion != null ? reputacion : 100;
        this.nivelActual = nivelActual != null ? nivelActual : 1;
        this.puntos = puntos != null ? puntos : 100;
        this.historialCrediticio = historialCrediticio != null ? historialCrediticio : false;
        this.rol = rol != null ? rol : "ROLE_USER";
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Boolean getHistorialCrediticio() {
        return historialCrediticio;
    }

    public void setHistorialCrediticio(Boolean historialCrediticio) {
        this.historialCrediticio = historialCrediticio;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}