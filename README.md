# Proyecto Ocio 

**Gestión de actividades de ocio y tiempo libre**

Proyecto Intermodular de 1º de DAW – Curso 2025/2026  
Prometeo by The Power FP Oficial

---

## Descripción

Proyecto Ocio es un sistema de gestión de actividades de ocio y tiempo libre para una empresa ficticia del mismo nombre. La empresa ofrece servicios dirigidos a cinco segmentos de clientes: particulares adultos, familias, grupos de jóvenes, centros educativos y grupos corporativos.

El proyecto integra todos los módulos de 1º de DAW en un sistema coherente y funcional.

---

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 21 | Aplicación de gestión con arquitectura en capas |
| MySQL 8 (XAMPP) | Base de datos relacional con 12 tablas |
| JDBC | Conexión entre la aplicación Java y MySQL |
| HTML5 + CSS3 | Portal web corporativo (4 páginas) |
| Git + GitHub | Control de versiones |
| IntelliJ IDEA | Entorno de desarrollo |

---

## Estructura del repositorio

```
proyecto-ocio/
├── web/                        → Portal web corporativo
│   ├── index.html              → Página de inicio
│   ├── actividades.html        → Catálogo de actividades
│   ├── nosotros.html           → Quiénes somos
│   ├── contacto.html           → Formulario de contacto
│   └── css/
│       └── estilos.css         → Hoja de estilos principal
│
├── src/proyectoocio/           → Código fuente Java
│   ├── Main.java               → Punto de entrada
│   ├── db/
│   │   └── Conexion.java       → Gestión de conexión JDBC (Singleton)
│   ├── model/
│   │   ├── Cliente.java        → Modelo de cliente
│   │   ├── Actividad.java      → Modelo de actividad
│   │   └── Reserva.java        → Modelo de reserva
│   ├── service/
│   │   ├── ClienteService.java    → CRUD de clientes
│   │   ├── ActividadService.java  → CRUD de actividades
│   │   └── ReservaService.java    → CRUD de reservas
│   └── controller/
│       └── Menu.java           → Menú principal y flujo de la app
│
├── sql/                        → Scripts de base de datos
│   ├── 01_crear_tablas.sql     → Creación de la BD y 12 tablas
│   ├── 02_insertar_datos.sql   → Datos de ejemplo
│   ├── 03_consultas.sql        → Consultas útiles para el negocio
│   └── diagrama_er_proyecto_ocio.drawio  → Diagrama E/R (draw.io)
│
├── docs/                       → Documentación
│   ├── sistemas/
│   │   └── informe_tecnico.docx   → Informe de Sistemas Informáticos
│   └── empleabilidad/
│       ├── 01_perfil_profesional.docx
│       ├── 02_presentacion_proyecto.docx
│       ├── 03_portfolio.docx
│       └── 04_reflexion_personal.docx
│
└── README.md                   → Este archivo
```

---

## Cómo ejecutar la aplicación

### Requisitos previos

- Java JDK 21 instalado
- XAMPP con MySQL en ejecución (puerto 3306)
- IntelliJ IDEA (Community Edition)
- Conector JDBC: `mysql-connector-j` añadido al proyecto

### Pasos

1. **Iniciar MySQL** — Abrir el Panel de Control de XAMPP y pulsar **Start** en MySQL

2. **Crear la base de datos** — En phpMyAdmin (`http://localhost/phpmyadmin`), ejecutar en orden:
   ```
   sql/01_crear_tablas.sql
   sql/02_insertar_datos.sql
   ```

3. **Abrir el proyecto** — Abrir la carpeta `src/` como proyecto Java en IntelliJ IDEA

4. **Añadir el conector JDBC** — `File → Project Structure → Libraries → +` y seleccionar el `.jar` de MySQL Connector

5. **Ejecutar** — Abrir `Main.java` y pulsar **Run** (Shift + F10)

### Verificar que funciona

Al arrancar, la consola debe mostrar:
```
[DB] Conexión establecida correctamente.
╔══════════════════════════════════════╗
║        PROYECTO OCIO – GESTIÓN       ║
╠══════════════════════════════════════╣
║  1. Gestión de clientes              ║
║  2. Gestión de actividades           ║
║  3. Gestión de reservas              ║
║  4. Estadísticas y consultas         ║
║  0. Salir                            ║
╚══════════════════════════════════════╝
```

---

## Cómo ver el portal web

Abrir directamente el archivo `web/index.html` en cualquier navegador. No requiere servidor.

La estructura de carpetas debe mantenerse para que el CSS se aplique correctamente:
```
web/
├── index.html
└── css/
    └── estilos.css
```

---

## Base de datos

La base de datos `proyecto_ocio` contiene 12 tablas:

`tipo_cliente` · `cliente` · `categoria` · `actividad` · `instalacion` · `monitor` · `actividad_monitor` · `reserva` · `participante` · `pago` · `material` · `valoracion`

El diagrama E/R completo se encuentra en `sql/diagrama_er_proyecto_ocio.drawio` y puede abrirse en [app.diagrams.net](https://app.diagrams.net).

---

## Módulos cubiertos

| Módulo | Entregable |
|---|---|
| 0484 – Bases de Datos | Diagrama E/R, modelo relacional, scripts SQL, consultas |
| 0487 – Entornos de Desarrollo | Repositorio GitHub estructurado, commits descriptivos |
| 0373 – Lenguajes de Marcas | Portal web HTML5 + CSS3 (4 páginas, responsive) |
| 0485 – Programación | Aplicación Java con JDBC y CRUD completo |
| 0483 – Sistemas Informáticos | Informe técnico del entorno de ejecución |
| CMO – Ampliación Programación | Arquitectura en capas (modelo/servicio/controlador), POO |
| 1709 – Empleabilidad | Perfil profesional, presentación, portfolio, reflexión |

---

## Autor

**Cristian Alvarez Gonzalez**  
1º DAW – Prometeo by The Power FP Oficial  
GitHub: [github.com/cristianalvarez-dev](https://github.com/cristianalvarez-dev)  
Email: cristianalvarezgonzalez.dev@gmail.com

---

*Proyecto Intermodular de 1º DAW – Curso 2025/2026*
