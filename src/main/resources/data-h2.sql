-- Copy of SkyBooking init SQL (same content as the file in Downloads)
-- See C:\\Users\\<you>\\Downloads\\skybooking-init.sql for the canonical copy

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS usuarios_roles;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS roles;
SET FOREIGN_KEY_CHECKS=1;

START TRANSACTION;

CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  nombre VARCHAR(100),
  apellidos VARCHAR(100),
  enabled TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE usuarios_roles (
  usuario_id BIGINT NOT NULL,
  rol_id BIGINT NOT NULL,
  PRIMARY KEY (usuario_id, rol_id),
  CONSTRAINT fk_usuarios_roles_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
  CONSTRAINT fk_usuarios_roles_rol FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO roles (id, nombre) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_EMPLEADO');

INSERT INTO usuarios (id, username, password, email, nombre, apellidos, enabled) VALUES
(1, 'admin', '$2a$10$EqKcp1WFKVQISheBxkQJpuHHpVT.dSFnBTGlLJClRcHSgvGvhEbSK', 'admin@skybooking.com', 'Administrador', 'Sistema', 1),
(2, 'empleado', '$2a$10$EqKcp1WFKVQISheBxkQJpuHHpVT.dSFnBTGlLJClRcHSgvGvhEbSK', 'empleado@skybooking.com', 'Empleado', 'Prueba', 1),
(3, 'usuario', '$2a$10$EqKcp1WFKVQISheBxkQJpuHHpVT.dSFnBTGlLJClRcHSgvGvhEbSK', 'usuario@skybooking.com', 'Usuario', 'Normal', 1);

INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES
(1,1),(1,2),(1,3),(2,1),(2,3),(3,1);

ALTER TABLE usuarios AUTO_INCREMENT = 4;
ALTER TABLE roles AUTO_INCREMENT = 4;

COMMIT;