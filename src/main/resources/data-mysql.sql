-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-02-2026 a las 18:02:00
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `skybooking`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aviones`
--

CREATE TABLE `aviones` (
                           `id` bigint(20) NOT NULL,
                           `matricula` varchar(255) NOT NULL,
                           `modelo` varchar(255) NOT NULL,
                           `capacidad_turista` int(11) NOT NULL,
                           `capacidad_business` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `aviones`
--

INSERT INTO `aviones` (`id`, `matricula`, `modelo`, `capacidad_turista`, `capacidad_business`) VALUES
                                                                                                   (1, 'EC-MYA', 'Airbus A320neo', 150, 12),
                                                                                                   (2, 'EC-LVO', 'Boeing 737-800', 180, 8),
                                                                                                   (3, 'EC-NBF', 'Airbus A350-900', 300, 30);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pasajeros`
--

CREATE TABLE `pasajeros` (
                             `id` bigint(20) NOT NULL,
                             `nombre` varchar(255) NOT NULL,
                             `apellidos` varchar(255) NOT NULL,
                             `dni` varchar(255) NOT NULL,
                             `email` varchar(255) NOT NULL,
                             `telefono` varchar(255) DEFAULT NULL,
                             `fecha_nacimiento` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `pasajeros`
--

INSERT INTO `pasajeros` (`id`, `nombre`, `apellidos`, `dni`, `email`, `telefono`, `fecha_nacimiento`) VALUES
                                                                                                          (1, 'Juan', 'García Pérez', '12345678Z', 'juan.garcia@email.com', '600111222', '1985-05-15'),
                                                                                                          (2, 'María', 'López Iturbide', '87654321X', 'm.lopez@email.com', '600333444', '1992-10-20');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
                            `id` bigint(20) NOT NULL,
                            `codigo_reserva` varchar(255) NOT NULL,
                            `vuelo_id` bigint(20) NOT NULL,
                            `pasajero_id` bigint(20) NOT NULL,
                            `fecha_reserva` datetime NOT NULL,
                            `clase` varchar(20) NOT NULL,
                            `precio_total` decimal(38,2) NOT NULL,
                            `estado` varchar(20) NOT NULL,
                            `asiento` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `reservas`
--

INSERT INTO `reservas` (`id`, `codigo_reserva`, `vuelo_id`, `pasajero_id`, `fecha_reserva`, `clase`, `precio_total`, `estado`, `asiento`) VALUES
                                                                                                                                              (1, 'RES-001', 1, 1, '2026-02-05 13:34:15', 'TURISTA', 45.00, 'CONFIRMADA', '12A'),
                                                                                                                                              (2, 'RES-002', 1, 2, '2026-02-05 13:34:15', 'BUSINESS', 120.00, 'CONFIRMADA', '1C');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
                         `id` bigint(20) NOT NULL,
                         `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `nombre`) VALUES
                                         (1, 'ROLE_ADMIN'),
                                         (2, 'ROLE_EMPLEADO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
                            `id` bigint(20) NOT NULL,
                            `username` varchar(255) NOT NULL,
                            `password` varchar(255) NOT NULL,
                            `email` varchar(255) NOT NULL,
                            `nombre` varchar(255) DEFAULT NULL,
                            `apellido` varchar(255) DEFAULT NULL,
                            `enabled` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `username`, `password`, `email`, `nombre`, `apellido`, `enabled`) VALUES
                                                                                                    (1, 'admin', '$2a$12$FBlrsNE6dITwMNZXq8yEueHKgckenkzfIxaojPAldzGWwnsllAOQW', 'admin@skybooking.es', 'Admin General', NULL, b'0'),
                                                                                                    (2, 'empleado', '$2a$12$FBlrsNE6dITwMNZXq8yEueHKgckenkzfIxaojPAldzGWwnsllAOQW', 'user@skybooking.es', 'Empleado Ventas', NULL, b'0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_roles`
--

CREATE TABLE `usuarios_roles` (
                                  `usuario_id` bigint(20) NOT NULL,
                                  `rol_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios_roles`
--

INSERT INTO `usuarios_roles` (`usuario_id`, `rol_id`) VALUES
                                                          (1, 1),
                                                          (1, 2),
                                                          (2, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vuelos`
--

CREATE TABLE `vuelos` (
                          `id` bigint(20) NOT NULL,
                          `numero_vuelo` varchar(255) NOT NULL,
                          `origen` varchar(255) NOT NULL,
                          `destino` varchar(255) NOT NULL,
                          `fecha_salida` datetime NOT NULL,
                          `fecha_llegada` datetime NOT NULL,
                          `precio_turista` decimal(38,2) NOT NULL,
                          `precio_business` decimal(38,2) NOT NULL,
                          `estado` varchar(20) NOT NULL,
                          `avion_id` bigint(20) NOT NULL
) ;

--
-- Volcado de datos para la tabla `vuelos`
--

INSERT INTO `vuelos` (`id`, `numero_vuelo`, `origen`, `destino`, `fecha_salida`, `fecha_llegada`, `precio_turista`, `precio_business`, `estado`, `avion_id`) VALUES
                                                                                                                                                                 (1, 'IB3001', 'Madrid (MAD)', 'Barcelona (BCN)', '2026-06-15 08:00:00', '2026-06-15 09:15:00', 45.00, 120.00, 'PROGRAMADO', 1),
                                                                                                                                                                 (2, 'RY2044', 'Sevilla (SVQ)', 'Londres (LHR)', '2026-06-16 12:30:00', '2026-06-16 15:00:00', 75.50, 180.00, 'EN_VUELO', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aviones`
--
ALTER TABLE `aviones`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `matricula` (`matricula`);

--
-- Indices de la tabla `pasajeros`
--
ALTER TABLE `pasajeros`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `dni` (`dni`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codigo_reserva` (`codigo_reserva`),
  ADD KEY `fk_reserva_vuelo` (`vuelo_id`),
  ADD KEY `fk_reserva_pasajero` (`pasajero_id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `usuarios_roles`
--
ALTER TABLE `usuarios_roles`
    ADD PRIMARY KEY (`usuario_id`,`rol_id`),
  ADD KEY `fk_ur_rol` (`rol_id`);

--
-- Indices de la tabla `vuelos`
--
ALTER TABLE `vuelos`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `numero_vuelo` (`numero_vuelo`),
  ADD KEY `fk_vuelo_avion` (`avion_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aviones`
--
ALTER TABLE `aviones`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pasajeros`
--
ALTER TABLE `pasajeros`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `reservas`
--
ALTER TABLE `reservas`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `vuelos`
--
ALTER TABLE `vuelos`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
    ADD CONSTRAINT `fk_reserva_pasajero` FOREIGN KEY (`pasajero_id`) REFERENCES `pasajeros` (`id`),
  ADD CONSTRAINT `fk_reserva_vuelo` FOREIGN KEY (`vuelo_id`) REFERENCES `vuelos` (`id`);

--
-- Filtros para la tabla `usuarios_roles`
--
ALTER TABLE `usuarios_roles`
    ADD CONSTRAINT `fk_ur_rol` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `fk_ur_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `vuelos`
--
ALTER TABLE `vuelos`
    ADD CONSTRAINT `fk_vuelo_avion` FOREIGN KEY (`avion_id`) REFERENCES `aviones` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
