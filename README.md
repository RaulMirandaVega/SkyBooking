# SkyBooking - Sistema de Gestión de Aerolínea

## Descripción

SkyBooking es una solución integral para la gestión de aerolíneas desarrollada como proyecto para el módulo de Acceso a Datos, realizada por Raúl Miranda, Lucas Fagúndez, Manuel Mena y Pedro Estévez.

El sistema implementa una arquitectura híbrida que ofrece:

1. **API RESTful**: Segura y escalable para integraciones externas.
2. **Interfaz Web Administrativa**: Un panel de control en el servidor para la gestión de la aerolínea.

## Stack Tecnológico

- **Backend**: Java, Spring Boot (Web, Data JPA, Security, Validation).
- **Base de Datos**: MySQL con diseño relacional normalizado.
- **Seguridad**: Implementación híbrida (JWT para API + Sesiones Stateful para Web).
- **Frontend**: Thymeleaf (Motor de plantillas).
- **Herramientas**: Maven, Lombok, Spring DevTools.

## Funcionalidades Principales

### Interfaz Web (Administración)

- **Dashboard**: Vista general del sistema.
- **Gestión de Vuelos**: Creación, edición y cancelación de vuelos.
- **Control de Reservas**: Visualización de estado (Confirmada, Cancelada, Pendiente).
- **Gestión de Flota**: Administración de aviones y capacidades.
- **Pasajeros**: Base de datos de clientes.

### API REST (Backend)

- **Endpoints Seguros**: Protección mediante Tokens JWT.
- **CRUD Completo**: Operaciones sobre las entidades del sistema.
- **Validaciones**: Control estricto de datos de entrada.
- **Lógica de Negocio**: Control de asientos, estados de vuelo y disponibilidad.

## Usuarios de Prueba

| Usuario   | Contraseña | Rol       | Acceso              |
|-----------|------------|-----------|---------------------|
| admin     | 123456     | ADMIN     | Control Total       |
| empleado  | 123456     | EMPLEADO  | Gestión diaria      |
| usuario   | 123456     | USER      | Solo lectura básica |





CREATE USER 'skyuser'@'localhost' IDENTIFIED BY 'skypass';
GRANT ALL PRIVILEGES ON skybooking.* TO 'skyuser'@'localhost';
FLUSH PRIVILEGES;
