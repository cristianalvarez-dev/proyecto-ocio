-- ============================================================
-- Proyecto Ocio – Consultas útiles para el negocio
-- ============================================================

USE proyecto_ocio;

-- ------------------------------------------------------------
-- 1. Listado completo de clientes con su tipo
-- ------------------------------------------------------------
SELECT
    c.id_cliente,
    CONCAT(c.nombre, ' ', c.apellidos) AS cliente,
    tc.nombre                           AS tipo_cliente,
    c.email,
    c.telefono,
    c.fecha_registro
FROM cliente c
JOIN tipo_cliente tc ON c.id_tipo = tc.id_tipo
ORDER BY c.fecha_registro DESC;

-- ------------------------------------------------------------
-- 2. Actividades disponibles con categoría, instalación y precio
-- ------------------------------------------------------------
SELECT
    a.id_actividad,
    a.nombre                  AS actividad,
    cat.nombre                AS categoria,
    i.nombre                  AS instalacion,
    a.precio_base             AS precio_por_persona,
    a.plazas_max,
    a.duracion_horas
FROM actividad a
JOIN categoria   cat ON a.id_categoria   = cat.id_categoria
JOIN instalacion i   ON a.id_instalacion = i.id_instalacion
ORDER BY cat.nombre, a.precio_base;

-- ------------------------------------------------------------
-- 3. Reservas con datos del cliente y actividad (JOIN múltiple)
-- ------------------------------------------------------------
SELECT
    r.id_reserva,
    CONCAT(c.nombre, ' ', c.apellidos) AS cliente,
    tc.nombre                           AS tipo_cliente,
    a.nombre                            AS actividad,
    r.fecha_actividad,
    r.num_participantes,
    r.estado
FROM reserva r
JOIN cliente      c  ON r.id_cliente   = c.id_cliente
JOIN tipo_cliente tc ON c.id_tipo      = tc.id_tipo
JOIN actividad    a  ON r.id_actividad = a.id_actividad
ORDER BY r.fecha_actividad;

-- ------------------------------------------------------------
-- 4. Estado de pagos por reserva (JOIN con importe calculado)
-- ------------------------------------------------------------
SELECT
    r.id_reserva,
    CONCAT(c.nombre, ' ', c.apellidos) AS cliente,
    a.nombre                            AS actividad,
    p.importe,
    p.metodo,
    p.estado                            AS estado_pago,
    p.fecha_pago
FROM pago p
JOIN reserva   r ON p.id_reserva   = r.id_reserva
JOIN cliente   c ON r.id_cliente   = c.id_cliente
JOIN actividad a ON r.id_actividad = a.id_actividad
ORDER BY p.fecha_pago DESC;

-- ------------------------------------------------------------
-- 5. Monitores asignados a cada actividad
-- ------------------------------------------------------------
SELECT
    a.nombre                                    AS actividad,
    CONCAT(m.nombre, ' ', m.apellidos)          AS monitor,
    m.especialidad
FROM actividad_monitor am
JOIN actividad a ON am.id_actividad = a.id_actividad
JOIN monitor   m ON am.id_monitor   = m.id_monitor
ORDER BY a.nombre;

-- ------------------------------------------------------------
-- 6. Valoración media por actividad (estadística)
-- ------------------------------------------------------------
SELECT
    a.nombre                        AS actividad,
    COUNT(v.id_valoracion)          AS num_valoraciones,
    ROUND(AVG(v.puntuacion), 2)     AS puntuacion_media,
    MIN(v.puntuacion)               AS minima,
    MAX(v.puntuacion)               AS maxima
FROM actividad a
LEFT JOIN valoracion v ON a.id_actividad = v.id_actividad
GROUP BY a.id_actividad, a.nombre
ORDER BY puntuacion_media DESC;

-- ------------------------------------------------------------
-- 7. Actividades más reservadas
-- ------------------------------------------------------------
SELECT
    a.nombre                   AS actividad,
    COUNT(r.id_reserva)        AS total_reservas,
    SUM(r.num_participantes)   AS total_participantes
FROM actividad a
LEFT JOIN reserva r ON a.id_actividad = r.id_actividad
GROUP BY a.id_actividad, a.nombre
ORDER BY total_reservas DESC;

-- ------------------------------------------------------------
-- 8. Ingresos totales por actividad
-- ------------------------------------------------------------
SELECT
    a.nombre                     AS actividad,
    COUNT(p.id_pago)             AS pagos_completados,
    SUM(p.importe)               AS ingresos_totales
FROM actividad a
JOIN reserva r ON a.id_actividad = r.id_actividad
JOIN pago    p ON r.id_reserva   = p.id_reserva
WHERE p.estado = 'completado'
GROUP BY a.id_actividad, a.nombre
ORDER BY ingresos_totales DESC;

-- ------------------------------------------------------------
-- 9. Reservas pendientes de confirmación
-- ------------------------------------------------------------
SELECT
    r.id_reserva,
    CONCAT(c.nombre, ' ', c.apellidos) AS cliente,
    a.nombre                            AS actividad,
    r.fecha_actividad,
    r.num_participantes
FROM reserva r
JOIN cliente   c ON r.id_cliente   = c.id_cliente
JOIN actividad a ON r.id_actividad = a.id_actividad
WHERE r.estado = 'pendiente'
ORDER BY r.fecha_actividad;

-- ------------------------------------------------------------
-- 10. Control de stock de materiales (bajo mínimos)
-- ------------------------------------------------------------
SELECT
    a.nombre                    AS actividad,
    mat.nombre                  AS material,
    mat.cantidad_disponible,
    mat.cantidad_necesaria,
    (mat.cantidad_disponible - mat.cantidad_necesaria) AS diferencia
FROM material mat
JOIN actividad a ON mat.id_actividad = a.id_actividad
WHERE mat.cantidad_disponible < mat.cantidad_necesaria
ORDER BY diferencia;
