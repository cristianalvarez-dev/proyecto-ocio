-- ============================================================
-- Proyecto Ocio – Script de creación de tablas
-- Base de datos: proyecto_ocio
-- ============================================================

CREATE DATABASE IF NOT EXISTS proyecto_ocio
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_spanish_ci;

USE proyecto_ocio;

-- ------------------------------------------------------------
-- 1. TIPO_CLIENTE
-- ------------------------------------------------------------
CREATE TABLE tipo_cliente (
    id_tipo        INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(50)  NOT NULL,
    descripcion    VARCHAR(200)
);

-- ------------------------------------------------------------
-- 2. INSTALACION
-- ------------------------------------------------------------
CREATE TABLE instalacion (
    id_instalacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    direccion      VARCHAR(200) NOT NULL,
    tipo           VARCHAR(50)  NOT NULL,
    aforo_max      INT          NOT NULL CHECK (aforo_max > 0)
);

-- ------------------------------------------------------------
-- 3. CATEGORIA
-- ------------------------------------------------------------
CREATE TABLE categoria (
    id_categoria   INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(80)  NOT NULL,
    descripcion    VARCHAR(300)
);

-- ------------------------------------------------------------
-- 4. MONITOR
-- ------------------------------------------------------------
CREATE TABLE monitor (
    id_monitor     INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(80)  NOT NULL,
    apellidos      VARCHAR(100) NOT NULL,
    especialidad   VARCHAR(100),
    email          VARCHAR(150) NOT NULL UNIQUE
);

-- ------------------------------------------------------------
-- 5. ACTIVIDAD
-- ------------------------------------------------------------
CREATE TABLE actividad (
    id_actividad   INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria   INT          NOT NULL,
    id_instalacion INT          NOT NULL,
    nombre         VARCHAR(120) NOT NULL,
    descripcion    TEXT,
    precio_base    DECIMAL(8,2) NOT NULL CHECK (precio_base >= 0),
    plazas_max     INT          NOT NULL CHECK (plazas_max > 0),
    duracion_horas INT          NOT NULL CHECK (duracion_horas > 0),
    CONSTRAINT fk_act_cat  FOREIGN KEY (id_categoria)   REFERENCES categoria(id_categoria),
    CONSTRAINT fk_act_inst FOREIGN KEY (id_instalacion) REFERENCES instalacion(id_instalacion)
);

-- ------------------------------------------------------------
-- 6. ACTIVIDAD_MONITOR (tabla intermedia N:M)
-- ------------------------------------------------------------
CREATE TABLE actividad_monitor (
    id_actividad   INT NOT NULL,
    id_monitor     INT NOT NULL,
    PRIMARY KEY (id_actividad, id_monitor),
    CONSTRAINT fk_am_act FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad),
    CONSTRAINT fk_am_mon FOREIGN KEY (id_monitor)   REFERENCES monitor(id_monitor)
);

-- ------------------------------------------------------------
-- 7. MATERIAL
-- ------------------------------------------------------------
CREATE TABLE material (
    id_material        INT AUTO_INCREMENT PRIMARY KEY,
    id_actividad       INT          NOT NULL,
    nombre             VARCHAR(100) NOT NULL,
    cantidad_disponible INT         NOT NULL DEFAULT 0 CHECK (cantidad_disponible >= 0),
    cantidad_necesaria  INT         NOT NULL DEFAULT 1 CHECK (cantidad_necesaria > 0),
    CONSTRAINT fk_mat_act FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad)
);

-- ------------------------------------------------------------
-- 8. CLIENTE
-- ------------------------------------------------------------
CREATE TABLE cliente (
    id_cliente     INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo        INT          NOT NULL,
    nombre         VARCHAR(80)  NOT NULL,
    apellidos      VARCHAR(100) NOT NULL,
    email          VARCHAR(150) NOT NULL UNIQUE,
    telefono       VARCHAR(20),
    fecha_registro DATE         NOT NULL DEFAULT (CURRENT_DATE),
    CONSTRAINT fk_cli_tipo FOREIGN KEY (id_tipo) REFERENCES tipo_cliente(id_tipo)
);

-- ------------------------------------------------------------
-- 9. RESERVA
-- ------------------------------------------------------------
CREATE TABLE reserva (
    id_reserva       INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente       INT          NOT NULL,
    id_actividad     INT          NOT NULL,
    fecha_reserva    DATE         NOT NULL DEFAULT (CURRENT_DATE),
    fecha_actividad  DATE         NOT NULL,
    num_participantes INT         NOT NULL DEFAULT 1 CHECK (num_participantes > 0),
    estado           VARCHAR(30)  NOT NULL DEFAULT 'pendiente',
    CONSTRAINT fk_res_cli FOREIGN KEY (id_cliente)   REFERENCES cliente(id_cliente),
    CONSTRAINT fk_res_act FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad),
    CONSTRAINT chk_estado CHECK (estado IN ('pendiente','confirmada','cancelada','completada'))
);

-- ------------------------------------------------------------
-- 10. PARTICIPANTE
-- ------------------------------------------------------------
CREATE TABLE participante (
    id_participante INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva      INT          NOT NULL,
    nombre          VARCHAR(80)  NOT NULL,
    apellidos       VARCHAR(100) NOT NULL,
    edad            INT          CHECK (edad >= 0 AND edad <= 120),
    CONSTRAINT fk_par_res FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva)
);

-- ------------------------------------------------------------
-- 11. PAGO
-- ------------------------------------------------------------
CREATE TABLE pago (
    id_pago       INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva    INT          NOT NULL UNIQUE,
    importe       DECIMAL(8,2) NOT NULL CHECK (importe >= 0),
    fecha_pago    DATE         NOT NULL DEFAULT (CURRENT_DATE),
    metodo        VARCHAR(40)  NOT NULL DEFAULT 'tarjeta',
    estado        VARCHAR(30)  NOT NULL DEFAULT 'pendiente',
    CONSTRAINT fk_pag_res FOREIGN KEY (id_reserva) REFERENCES reserva(id_reserva),
    CONSTRAINT chk_metodo CHECK (metodo IN ('tarjeta','transferencia','efectivo','bizum')),
    CONSTRAINT chk_estado_pago CHECK (estado IN ('pendiente','completado','reembolsado'))
);

-- ------------------------------------------------------------
-- 12. VALORACION
-- ------------------------------------------------------------
CREATE TABLE valoracion (
    id_valoracion  INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente     INT          NOT NULL,
    id_actividad   INT          NOT NULL,
    puntuacion     INT          NOT NULL CHECK (puntuacion BETWEEN 1 AND 5),
    comentario     TEXT,
    fecha          DATE         NOT NULL DEFAULT (CURRENT_DATE),
    CONSTRAINT fk_val_cli FOREIGN KEY (id_cliente)   REFERENCES cliente(id_cliente),
    CONSTRAINT fk_val_act FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad),
    UNIQUE KEY uq_valoracion (id_cliente, id_actividad)
);
