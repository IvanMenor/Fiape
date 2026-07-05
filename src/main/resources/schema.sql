-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS fiape;
USE fiape;

-- Tabla: usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255),
    correo VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    dni VARCHAR(8) NULL,
    telefono VARCHAR(255) NULL,
    pin VARCHAR(4) NULL,
    reputacion INT NULL,
    nivel_actual INT NULL,
    puntos INT NULL,
    historial_crediticio BOOLEAN NULL,
    rol VARCHAR(255) NULL
);

-- Tabla: creditos
CREATE TABLE IF NOT EXISTS creditos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NULL,
    nivel INT NULL,
    monto DOUBLE NULL,
    estado VARCHAR(255) NULL,
    fecha_solicitud DATE NULL,
    fecha_vencimiento DATE NULL,
    CONSTRAINT fk_creditos_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- Tabla: desembolsos
CREATE TABLE IF NOT EXISTS desembolsos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    credito_id BIGINT NULL,
    monto DOUBLE NULL,
    fecha_desembolso DATETIME NULL,
    estado VARCHAR(255) NULL,
    CONSTRAINT fk_desembolsos_credito FOREIGN KEY (credito_id) REFERENCES creditos(id) ON DELETE SET NULL
);

-- Tabla: evaluaciones
CREATE TABLE IF NOT EXISTS evaluaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(255) NULL,
    nivel_asignado INT NULL,
    monto_aprobado DOUBLE NULL,
    modalidad VARCHAR(255) NULL,
    explicacion_ia VARCHAR(500) NULL,
    recomendacion_ia VARCHAR(500) NULL,
    fecha_evaluacion DATETIME NULL
);

-- Tabla: pagos
CREATE TABLE IF NOT EXISTS pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    credito_id BIGINT NULL,
    monto DOUBLE NULL,
    fecha_pago DATE NULL,
    puntual BOOLEAN NULL,
    CONSTRAINT fk_pagos_credito FOREIGN KEY (credito_id) REFERENCES creditos(id) ON DELETE SET NULL
);

-- Insertar usuario peruano (si no existe por su correo único)
INSERT INTO usuarios (id, nombres, apellidos, correo, contrasena, dni, telefono, pin, reputacion, nivel_actual, puntos, historial_crediticio, rol)
VALUES (1, 'Juan Carlos', 'Quispe Mamani', 'juan.quispe@gmail.com', 'password123', '45896321', '987654321', '1234', 100, 1, 100, FALSE, 'ROLE_USER')
ON DUPLICATE KEY UPDATE correo=correo;

-- Insertar administrador por defecto (si no existe por su correo único)
INSERT INTO usuarios (id, nombres, apellidos, correo, contrasena, dni, telefono, pin, reputacion, nivel_actual, puntos, historial_crediticio, rol)
VALUES (2, 'Administrador', 'Fiape', 'admin@fiape.pe', 'admin123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE correo=correo;
