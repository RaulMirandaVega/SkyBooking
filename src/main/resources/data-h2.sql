-- Insertar roles
INSERT INTO roles (id, nombre) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_EMPLEADO');

-- Insertar usuarios (hash de '123456' según guía)
INSERT INTO usuarios (id, username, password, email, nombre, apellido, enabled) VALUES
(1, 'admin', '$2a$10$EqKcp1WFKVQISheBxkQJpuHHpVT.dSFnBTGlLJClRcHSgvGvhEbSK', 'admin@skybooking.com', 'Administrador', 'Sistema', true),
(2, 'empleado', '$2a$10$EqKcp1WFKVQISheBxkQJpuHHpVT.dSFnBTGlLJClRcHSgvGvhEbSK', 'empleado@skybooking.com', 'Empleado', 'Prueba', true),
(3, 'usuario', '$2a$10$EqKcp1WFKVQISheBxkQJpuHHpVT.dSFnBTGlLJClRcHSgvGvhEbSK', 'usuario@skybooking.com', 'Usuario', 'Normal', true);

-- Asignar roles a usuarios
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(1,1),(1,2),(1,3),
(2,1),(2,3),
(3,1);