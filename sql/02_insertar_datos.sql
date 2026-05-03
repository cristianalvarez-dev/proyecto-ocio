-- ============================================================
-- Proyecto Ocio – Script de inserción de datos de ejemplo
-- ============================================================

USE proyecto_ocio;

-- ------------------------------------------------------------
-- Tipos de cliente
-- ------------------------------------------------------------
INSERT INTO tipo_cliente (nombre, descripcion) VALUES
  ('Particular adulto',   'Adulto que contrata actividades de forma individual o en pareja'),
  ('Familia',             'Grupos familiares con niños de distintas edades'),
  ('Grupo de jóvenes',    'Grupos de jóvenes de entre 14 y 25 años'),
  ('Colegio',             'Centros educativos que contratan salidas o talleres'),
  ('Grupo corporativo',   'Empresas que buscan actividades de team building o convivencia');

-- ------------------------------------------------------------
-- Instalaciones
-- ------------------------------------------------------------
INSERT INTO instalacion (nombre, direccion, tipo, aforo_max) VALUES
  ('Sede Central Proyecto Ocio', 'Calle Mayor 12, Madrid',       'Sala polivalente',  80),
  ('Parque Natural El Retiro',   'Paseo del Retiro s/n, Madrid', 'Espacio exterior', 200),
  ('Centro Deportivo Norte',     'Av. de la Paz 45, Madrid',     'Instalación deportiva', 120),
  ('Aula Naturaleza Sur',        'Carretera M-40 km 12, Madrid', 'Aula de naturaleza', 50),
  ('Sala de Talleres A',         'Calle Mayor 12, Madrid',       'Taller interior',   30);

-- ------------------------------------------------------------
-- Categorías
-- ------------------------------------------------------------
INSERT INTO categoria (nombre, descripcion) VALUES
  ('Naturaleza y aventura', 'Rutas, senderismo, escalada y actividades al aire libre'),
  ('Talleres creativos',    'Pintura, manualidades, teatro y expresión artística'),
  ('Deportes y juego',      'Gymkhanas, deportes de equipo y juegos activos'),
  ('Team building',         'Dinámicas de grupo y cohesión para empresas y colegios'),
  ('Educativo ambiental',   'Actividades de conciencia medioambiental para colegios');

-- ------------------------------------------------------------
-- Monitores
-- ------------------------------------------------------------
INSERT INTO monitor (nombre, apellidos, especialidad, email) VALUES
  ('Laura',   'García Ruiz',     'Naturaleza y aventura',  'laura.garcia@proyectoocio.es'),
  ('Carlos',  'Martínez López',  'Talleres creativos',     'carlos.martinez@proyectoocio.es'),
  ('Sofía',   'Fernández Mora',  'Deportes y juego',       'sofia.fernandez@proyectoocio.es'),
  ('Miguel',  'Sánchez Vega',    'Team building',          'miguel.sanchez@proyectoocio.es'),
  ('Elena',   'Torres Díaz',     'Educativo ambiental',    'elena.torres@proyectoocio.es');

-- ------------------------------------------------------------
-- Actividades
-- ------------------------------------------------------------
INSERT INTO actividad (id_categoria, id_instalacion, nombre, descripcion, precio_base, plazas_max, duracion_horas) VALUES
  (1, 2, 'Ruta de senderismo familiar',       'Ruta guiada por el parque con paradas interpretativas',     15.00, 30, 3),
  (2, 5, 'Taller de pintura para niños',       'Taller creativo con técnicas mixtas para grupos de niños',  12.00, 20, 2),
  (3, 3, 'Gymkhana deportiva',                 'Circuito de pruebas físicas y trabajo en equipo',           10.00, 40, 2),
  (4, 1, 'Team building empresarial',          'Dinámicas de cohesión y comunicación para equipos',         40.00, 25, 4),
  (5, 4, 'Taller de educación ambiental',      'Actividades sobre biodiversidad y sostenibilidad',          8.00,  35, 2),
  (1, 2, 'Escape room al aire libre',          'Aventura urbana con misiones y retos en el parque',         18.00, 20, 2),
  (3, 3, 'Torneo de deportes alternativos',    'Competición amistosa de deportes poco convencionales',      12.00, 50, 3),
  (2, 1, 'Taller de teatro y expresión',       'Improvisación y juego dramático para grupos jóvenes',       14.00, 20, 2);

-- ------------------------------------------------------------
-- Asignación actividad-monitor
-- ------------------------------------------------------------
INSERT INTO actividad_monitor (id_actividad, id_monitor) VALUES
  (1, 1), (1, 5),
  (2, 2),
  (3, 3),
  (4, 4), (4, 3),
  (5, 5),
  (6, 1), (6, 4),
  (7, 3),
  (8, 2);

-- ------------------------------------------------------------
-- Materiales
-- ------------------------------------------------------------
INSERT INTO material (id_actividad, nombre, cantidad_disponible, cantidad_necesaria) VALUES
  (1, 'Botiquín de primeros auxilios', 3,  1),
  (1, 'Mapa de ruta plastificado',     35, 30),
  (2, 'Pintura acrílica (juego)',       25, 20),
  (2, 'Lienzo 30x40',                  25, 20),
  (3, 'Petos de colores',              50, 40),
  (3, 'Conos de señalización',         40, 20),
  (4, 'Tarjetas de dinámicas',         30, 25),
  (5, 'Lupas de campo',                40, 35),
  (6, 'Sobres con pistas',             25, 20),
  (7, 'Raquetas frescobol',            20, 20);

-- ------------------------------------------------------------
-- Clientes
-- ------------------------------------------------------------
INSERT INTO cliente (id_tipo, nombre, apellidos, email, telefono, fecha_registro) VALUES
  (1, 'Ana',      'Pérez García',    'ana.perez@email.com',       '612345678', '2024-09-15'),
  (2, 'Familia',  'Rodríguez',       'fam.rodriguez@email.com',   '623456789', '2024-10-01'),
  (3, 'Grupo',    'Jóvenes Sur',     'jovenes.sur@email.com',     '634567890', '2024-10-12'),
  (4, 'CEIP',     'García Lorca',    'ceip.garcialorca@edu.es',   '645678901', '2024-11-03'),
  (5, 'Empresa',  'TechSolutions SL','contacto@techsolutions.es', '656789012', '2024-11-20'),
  (1, 'Pedro',    'López Martín',    'pedro.lopez@email.com',     '667890123', '2025-01-08'),
  (2, 'Familia',  'Sánchez Ruiz',    'fam.sanchez@email.com',     '678901234', '2025-01-15'),
  (4, 'Colegio',  'Cervantes',       'colegio.cervantes@edu.es',  '689012345', '2025-02-01');

-- ------------------------------------------------------------
-- Reservas
-- ------------------------------------------------------------
INSERT INTO reserva (id_cliente, id_actividad, fecha_reserva, fecha_actividad, num_participantes, estado) VALUES
  (1, 6, '2025-03-10', '2025-03-20', 1,  'completada'),
  (2, 1, '2025-03-12', '2025-03-22', 4,  'completada'),
  (3, 3, '2025-03-15', '2025-03-25', 15, 'completada'),
  (4, 5, '2025-03-18', '2025-04-05', 28, 'completada'),
  (5, 4, '2025-03-20', '2025-04-10', 20, 'confirmada'),
  (6, 2, '2025-04-01', '2025-04-15', 1,  'confirmada'),
  (7, 1, '2025-04-05', '2025-04-20', 3,  'pendiente'),
  (8, 8, '2025-04-08', '2025-04-25', 25, 'pendiente');

-- ------------------------------------------------------------
-- Participantes
-- ------------------------------------------------------------
INSERT INTO participante (id_reserva, nombre, apellidos, edad) VALUES
  (1, 'Ana',     'Pérez García',  35),
  (2, 'Tomás',   'Rodríguez',     42),
  (2, 'Carmen',  'Rodríguez',     40),
  (2, 'Lucía',   'Rodríguez',     10),
  (2, 'Pablo',   'Rodríguez',     7),
  (3, 'Marcos',  'Jóvenes Sur',   19),
  (3, 'Julia',   'Jóvenes Sur',   21),
  (3, 'Diego',   'Jóvenes Sur',   18);

-- ------------------------------------------------------------
-- Pagos
-- ------------------------------------------------------------
INSERT INTO pago (id_reserva, importe, fecha_pago, metodo, estado) VALUES
  (1, 18.00,  '2025-03-10', 'tarjeta',       'completado'),
  (2, 60.00,  '2025-03-12', 'bizum',         'completado'),
  (3, 150.00, '2025-03-15', 'transferencia', 'completado'),
  (4, 224.00, '2025-03-18', 'transferencia', 'completado'),
  (5, 800.00, '2025-03-20', 'transferencia', 'pendiente'),
  (6, 12.00,  '2025-04-01', 'tarjeta',       'completado'),
  (7, 45.00,  '2025-04-05', 'bizum',         'pendiente'),
  (8, 350.00, '2025-04-08', 'transferencia', 'pendiente');

-- ------------------------------------------------------------
-- Valoraciones
-- ------------------------------------------------------------
INSERT INTO valoracion (id_cliente, id_actividad, puntuacion, comentario, fecha) VALUES
  (1, 6, 5, 'Experiencia increíble, muy bien organizado y los monitores son estupendos.', '2025-03-21'),
  (2, 1, 5, 'Toda la familia disfrutó muchísimo. Volveremos sin duda.', '2025-03-23'),
  (3, 3, 4, 'Muy divertido, aunque alguna prueba era un poco complicada para los más pequeños.', '2025-03-26'),
  (4, 5, 5, 'Los alumnos aprendieron muchísimo. Recomendamos esta actividad a todos los colegios.', '2025-04-06');
