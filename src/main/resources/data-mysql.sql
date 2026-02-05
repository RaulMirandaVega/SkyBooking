-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: skybooking
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aviones`
--

DROP TABLE IF EXISTS `aviones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aviones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `matricula` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `modelo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `capacidad_turista` int NOT NULL,
  `capacidad_business` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `matricula` (`matricula`),
  CONSTRAINT `chk_capacidad_business` CHECK ((`capacidad_business` >= 0)),
  CONSTRAINT `chk_capacidad_turista` CHECK ((`capacidad_turista` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aviones`
--

LOCK TABLES `aviones` WRITE;
/*!40000 ALTER TABLE `aviones` DISABLE KEYS */;
INSERT INTO `aviones` VALUES (1,'EC-MXV','Boeing 737-800',150,20),(2,'EC-LYE','Airbus A320',140,24),(3,'EC-JFN','Boeing 787 Dreamliner',200,40),(4,'EC-KDT','Airbus A350',250,50),(5,'EC-LVT','Boeing 737 MAX',160,16),(6,'EC-MMX','Airbus A321',180,30),(7,'EC-NDB','Boeing 777-300ER',300,60),(8,'EC-MHL','Airbus A319',120,16);
/*!40000 ALTER TABLE `aviones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pasajeros`
--

DROP TABLE IF EXISTS `pasajeros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pasajeros` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellidos` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dni` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fecha_nacimiento` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_dni` (`dni`),
  KEY `idx_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pasajeros`
--

LOCK TABLES `pasajeros` WRITE;
/*!40000 ALTER TABLE `pasajeros` DISABLE KEYS */;
INSERT INTO `pasajeros` VALUES (1,'Juan','García López','12345678A','juan.garcia@email.com','600111222','1985-03-15'),(2,'María','Martínez Sánchez','23456789B','maria.martinez@email.com','600222333','1990-07-22'),(3,'Pedro','Rodríguez Fernández','34567890C','pedro.rodriguez@email.com','600333444','1988-11-10'),(4,'Ana','López González','45678901D','ana.lopez@email.com','600444555','1992-05-18'),(5,'Carlos','Hernández Ruiz','56789012E','carlos.hernandez@email.com','600555666','1987-09-25'),(6,'Laura','González Díaz','67890123F','laura.gonzalez@email.com','600666777','1995-01-30'),(7,'David','Pérez Moreno','78901234G','david.perez@email.com','600777888','1983-12-08'),(8,'Sara','Sánchez Jiménez','89012345H','sara.sanchez@email.com','600888999','1991-04-14'),(9,'Miguel','Ramírez Castro','90123456I','miguel.ramirez@email.com','600999000','1986-08-20'),(10,'Elena','Torres Vargas','01234567J','elena.torres@email.com','601000111','1993-06-12'),(11,'Javier','Flores Ortiz','11223344K','javier.flores@email.com','601111222','1989-02-28'),(12,'Carmen','Ruiz Molina','22334455L','carmen.ruiz@email.com','601222333','1994-10-05'),(13,'Roberto','Moreno Herrera','33445566M','roberto.moreno@email.com','601333444','1984-07-17'),(14,'Isabel','Jiménez Vega','44556677N','isabel.jimenez@email.com','601444555','1996-03-22'),(15,'Francisco','Castro Romero','55667788O','francisco.castro@email.com','601555666','1982-11-30');
/*!40000 ALTER TABLE `pasajeros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo_reserva` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `vuelo_id` bigint NOT NULL,
  `pasajero_id` bigint NOT NULL,
  `fecha_reserva` datetime NOT NULL,
  `clase` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `precio_total` decimal(38,2) NOT NULL,
  `estado` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `asiento` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_reserva` (`codigo_reserva`),
  KEY `idx_codigo_reserva` (`codigo_reserva`),
  KEY `idx_vuelo` (`vuelo_id`),
  KEY `idx_pasajero` (`pasajero_id`),
  KEY `idx_estado_reserva` (`estado`),
  CONSTRAINT `fk_reserva_pasajero` FOREIGN KEY (`pasajero_id`) REFERENCES `pasajeros` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_reserva_vuelo` FOREIGN KEY (`vuelo_id`) REFERENCES `vuelos` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_precio_total` CHECK ((`precio_total` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
INSERT INTO `reservas` VALUES (1,'SKY1738234567890',1,1,'2025-01-15 10:30:00','TURISTA',89.99,'CONFIRMADA','12A'),(2,'SKY1738234567891',1,2,'2025-01-15 11:00:00','TURISTA',89.99,'CONFIRMADA','12B'),(3,'SKY1738234567892',1,3,'2025-01-16 09:15:00','BUSINESS',189.99,'CONFIRMADA','2A'),(4,'SKY1738234567893',2,4,'2025-01-16 14:20:00','TURISTA',95.99,'CONFIRMADA','15C'),(5,'SKY1738234567894',2,5,'2025-01-16 15:30:00','TURISTA',95.99,'CONFIRMADA','15D'),(6,'SKY1738234567895',3,6,'2025-01-17 08:00:00','TURISTA',75.99,'CONFIRMADA','8A'),(7,'SKY1738234567896',3,7,'2025-01-17 10:45:00','BUSINESS',165.99,'CONFIRMADA','1A'),(8,'SKY1738234567897',11,8,'2025-01-18 12:00:00','BUSINESS',399.99,'CONFIRMADA','5B'),(9,'SKY1738234567898',11,9,'2025-01-18 13:30:00','TURISTA',149.99,'CONFIRMADA','20A'),(10,'SKY1738234567899',12,10,'2025-01-19 09:00:00','BUSINESS',379.99,'CONFIRMADA','3C'),(11,'SKY1738234567900',16,11,'2025-01-20 11:00:00','BUSINESS',2499.99,'CONFIRMADA','1A'),(12,'SKY1738234567901',16,12,'2025-01-20 11:30:00','TURISTA',599.99,'CONFIRMADA','25B'),(13,'SKY1738234567902',16,13,'2025-01-20 12:00:00','TURISTA',599.99,'CONFIRMADA','25C'),(14,'SKY1738234567903',5,14,'2025-01-21 10:00:00','TURISTA',79.99,'CANCELADA','10A'),(15,'SKY1738234567904',6,15,'2025-01-21 14:00:00','TURISTA',85.99,'CANCELADA','18B'),(16,'SKY1738234567905',7,1,'2025-01-22 08:00:00','TURISTA',89.99,'CONFIRMADA','14A'),(17,'SKY1738234567906',8,2,'2025-01-22 10:00:00','TURISTA',55.99,'CONFIRMADA','9B'),(18,'SKY1738234567907',9,3,'2025-01-23 09:00:00','TURISTA',49.99,'CONFIRMADA','11C'),(19,'SKY1738234567908',10,4,'2025-01-23 11:00:00','BUSINESS',169.99,'CONFIRMADA','2B'),(20,'SKY1738234567909',13,5,'2025-01-24 08:30:00','TURISTA',159.99,'CONFIRMADA','22A'),(21,'SKY1738234567910',14,6,'2025-01-24 15:00:00','TURISTA',169.99,'PENDIENTE_PAGO',NULL),(22,'SKY1738234567911',15,7,'2025-01-25 10:00:00','BUSINESS',404.99,'PENDIENTE_PAGO',NULL),(23,'SKY-1769432197118',1,1,'2026-01-26 12:56:37','TURISTA',89.99,'CONFIRMADA','12A'),(24,'SKY-1769432198897',1,1,'2026-01-26 12:56:39','TURISTA',89.99,'CONFIRMADA','12A'),(25,'SKY-1769432199464',1,1,'2026-01-26 12:56:39','TURISTA',89.99,'CONFIRMADA','12A'),(26,'SKY-1769432199940',1,1,'2026-01-26 12:56:40','TURISTA',89.99,'CONFIRMADA','12A'),(27,'SKY-1769432200364',1,2,'2026-01-26 12:56:40','TURISTA',89.99,'CONFIRMADA','12B'),(28,'SKY-1769432201035',1,3,'2026-01-26 12:56:41','BUSINESS',189.99,'CONFIRMADA','2A'),(29,'SKY-1769432201506',2,4,'2026-01-26 12:56:42','TURISTA',95.99,'CONFIRMADA','15C'),(30,'SKY-1769432201948',2,5,'2026-01-26 12:56:42','TURISTA',95.99,'CONFIRMADA','15D'),(31,'SKY-1769432202479',3,6,'2026-01-26 12:56:42','TURISTA',75.99,'CONFIRMADA','8A'),(32,'SKY-1769432205510',1,1,'2026-01-26 12:56:46','TURISTA',89.99,'CONFIRMADA','12A'),(33,'SKY-1769432207702',14,6,'2026-01-26 12:56:48','TURISTA',169.99,'CONFIRMADA',NULL),(34,'SKY-1769432210398',14,6,'2026-01-26 12:56:50','TURISTA',169.99,'CONFIRMADA',NULL),(35,'SKY-1769499254392',1,1,'2026-01-27 07:34:14','BUSINESS',189.99,'CONFIRMADA','12A'),(36,'SKY-1769499294959',13,1,'2026-01-27 07:34:55','BUSINESS',419.99,'CONFIRMADA','12A');
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'ROLE_ADMIN'),(3,'ROLE_EMPLEADO'),(1,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin','$2a$10$9JimVnyYLjeBfLVvgyy9ouo.gEXDjyhaAZ/bPw0RRn.I39RJJCLam','admin@skybooking.com','Administrador','Sistema',1),(2,'empleado','$2a$10$9JimVnyYLjeBfLVvgyy9ouo.gEXDjyhaAZ/bPw0RRn.I39RJJCLam','empleado@skybooking.com','Empleado','Prueba',1),(3,'usuario','$2a$10$9JimVnyYLjeBfLVvgyy9ouo.gEXDjyhaAZ/bPw0RRn.I39RJJCLam','usuario@skybooking.com','Usuario','Normal',1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_roles`
--

DROP TABLE IF EXISTS `usuarios_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_roles` (
  `usuario_id` bigint NOT NULL,
  `rol_id` bigint NOT NULL,
  PRIMARY KEY (`usuario_id`,`rol_id`),
  KEY `fk_usuarios_roles_rol` (`rol_id`),
  CONSTRAINT `fk_usuarios_roles_rol` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_usuarios_roles_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_roles`
--

LOCK TABLES `usuarios_roles` WRITE;
/*!40000 ALTER TABLE `usuarios_roles` DISABLE KEYS */;
INSERT INTO `usuarios_roles` VALUES (1,1),(2,1),(3,1),(1,2),(1,3),(2,3);
/*!40000 ALTER TABLE `usuarios_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vuelos`
--

DROP TABLE IF EXISTS `vuelos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vuelos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `numero_vuelo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `origen` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `destino` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_salida` datetime NOT NULL,
  `fecha_llegada` datetime NOT NULL,
  `precio_turista` decimal(38,2) NOT NULL,
  `precio_business` decimal(38,2) NOT NULL,
  `estado` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `avion_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_vuelo` (`numero_vuelo`),
  KEY `fk_vuelo_avion` (`avion_id`),
  KEY `idx_numero_vuelo` (`numero_vuelo`),
  KEY `idx_origen_destino` (`origen`,`destino`),
  KEY `idx_fecha_salida` (`fecha_salida`),
  KEY `idx_estado` (`estado`),
  CONSTRAINT `fk_vuelo_avion` FOREIGN KEY (`avion_id`) REFERENCES `aviones` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_fechas` CHECK ((`fecha_llegada` > `fecha_salida`)),
  CONSTRAINT `chk_precio_business` CHECK ((`precio_business` > 0)),
  CONSTRAINT `chk_precio_turista` CHECK ((`precio_turista` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vuelos`
--

LOCK TABLES `vuelos` WRITE;
/*!40000 ALTER TABLE `vuelos` DISABLE KEYS */;
INSERT INTO `vuelos` VALUES (1,'IB3001','Madrid','Barcelona','2026-01-29 11:00:00','2026-02-06 11:00:00',70.99,189.99,'PROGRAMADO',1),(2,'IB3002','Madrid','Barcelona','2025-02-01 14:00:00','2025-02-01 15:30:00',95.99,195.99,'PROGRAMADO',2),(3,'IB3003','Madrid','Sevilla','2025-02-02 10:00:00','2025-02-02 11:15:00',75.99,165.99,'PROGRAMADO',1),(4,'IB3004','Madrid','Valencia','2025-02-02 12:00:00','2025-02-02 13:00:00',69.99,149.99,'PROGRAMADO',5),(5,'IB3005','Madrid','Bilbao','2025-02-03 09:00:00','2025-02-03 10:15:00',79.99,169.99,'PROGRAMADO',2),(6,'IB3006','Madrid','Málaga','2025-02-03 15:00:00','2025-02-03 16:15:00',85.99,175.99,'PROGRAMADO',6),(7,'VY2001','Barcelona','Madrid','2025-02-01 10:00:00','2025-02-01 11:30:00',89.99,189.99,'PROGRAMADO',3),(8,'VY2002','Barcelona','Palma de Mallorca','2025-02-01 16:00:00','2025-02-01 17:00:00',55.99,125.99,'PROGRAMADO',8),(9,'VY2003','Barcelona','Valencia','2025-02-02 08:30:00','2025-02-02 09:30:00',49.99,119.99,'PROGRAMADO',8),(10,'VY2004','Barcelona','Sevilla','2025-02-02 13:00:00','2025-02-02 14:30:00',79.99,169.99,'PROGRAMADO',2),(11,'IB6001','Madrid','Londres','2025-02-04 11:00:00','2025-02-04 13:30:00',149.99,399.99,'PROGRAMADO',3),(12,'IB6002','Madrid','París','2025-02-04 14:00:00','2025-02-04 16:15:00',139.99,379.99,'PROGRAMADO',4),(13,'IB6003','Madrid','Roma','2025-02-05 09:30:00','2025-02-05 12:00:00',159.99,419.99,'PROGRAMADO',3),(14,'IB6004','Madrid','Frankfurt','2025-02-05 15:00:00','2025-02-05 17:45:00',169.99,439.99,'PROGRAMADO',4),(15,'IB6005','Madrid','Ámsterdam','2025-02-06 10:00:00','2025-02-06 13:00:00',154.99,404.99,'PROGRAMADO',6),(16,'IB7001','Madrid','Nueva York','2025-02-07 12:00:00','2025-02-07 18:30:00',599.99,2499.99,'PROGRAMADO',7),(17,'IB7002','Madrid','Buenos Aires','2025-02-08 22:00:00','2025-02-09 08:30:00',799.99,2999.99,'PROGRAMADO',7),(18,'IB7003','Madrid','México DF','2025-02-09 14:00:00','2025-02-09 21:00:00',699.99,2699.99,'PROGRAMADO',7),(19,'IB3010','Barcelona','Madrid','2025-02-01 18:00:00','2025-02-01 19:30:00',89.99,189.99,'PROGRAMADO',1),(20,'IB3011','Sevilla','Madrid','2025-02-02 17:00:00','2025-02-02 18:15:00',75.99,165.99,'PROGRAMADO',1),(21,'IB3012','Valencia','Madrid','2025-02-02 19:00:00','2025-02-02 20:00:00',69.99,149.99,'PROGRAMADO',5),(22,'IB9001','Madrid','Bilbao','2025-01-15 10:00:00','2025-01-15 11:15:00',79.99,169.99,'FINALIZADO',2),(23,'IB9002','Madrid','Barcelona','2025-01-20 14:00:00','2025-01-20 15:30:00',89.99,189.99,'CANCELADO',1);
/*!40000 ALTER TABLE `vuelos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'skybooking'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-04 14:06:09
