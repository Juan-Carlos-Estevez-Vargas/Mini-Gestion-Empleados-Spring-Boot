-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-08-2022 a las 18:26:30
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestion_empleados`
--
CREATE DATABASE IF NOT EXISTS `gestion_empleados` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gestion_empleados`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `id` bigint(20) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fecha` date NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `salario` double NOT NULL,
  `sexo` varchar(255) DEFAULT NULL,
  `telefono` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`id`, `apellido`, `email`, `fecha`, `nombre`, `salario`, `sexo`, `telefono`) VALUES
(1, 'Estevez', 'juan@example.com', '2001-07-10', 'Juan Carlos', 1600000, 'M', 3213213213),
(2, 'Perez', 'perez@mail.com', '2002-08-13', 'Martín', 1350000, 'M', 3214567821),
(3, 'Peralta', 'peralta@mail.com', '1992-08-27', 'Mayra', 980000, 'F', 3145679000),
(4, 'Neruda', 'neruda@mail.com', '1996-06-11', 'Pablo', 1750000, 'M', 3133187658),
(5, 'Pineda', 'pineda@mail.com', '1998-12-09', 'Alejandra', 4000000, 'F', 3115556788),
(6, 'Rodriguez', 'rodriguez@mail.com', '1982-02-01', 'Mireya', 8735000, 'F', 3103234512),
(7, 'Laiton', 'laiton@mail.com', '1973-06-20', 'Fernanda', 1700000, 'F', 3158906766),
(8, 'Coca', 'geral@mail.com', '2004-06-07', 'Geraldine', 1200000, 'F', 3112113111);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
