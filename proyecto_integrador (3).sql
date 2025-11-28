-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-11-2025 a las 22:54:53
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto_integrador`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL,
  `nombre_categoria` varchar(255) DEFAULT NULL,
  `estado_categoria` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `nombre_categoria`, `estado_categoria`) VALUES
(1, 'Vino', 1),
(2, 'Singani', 1),
(3, 'Materia prima', 1),
(11, 'Insumos', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `id_compra` int(11) NOT NULL,
  `nombre_proveedor` varchar(255) DEFAULT NULL,
  `fecha_compra` datetime NOT NULL,
  `total` double NOT NULL,
  `estado_compra` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `compra`
--

INSERT INTO `compra` (`id_compra`, `nombre_proveedor`, `fecha_compra`, `total`, `estado_compra`) VALUES
(4, 'Edprint', '2025-11-27 17:50:53', 1125, 1),
(5, 'El portillo', '2025-11-27 17:53:00', 5075, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_compra`
--

CREATE TABLE `detalle_compra` (
  `id_detalle_compra` bigint(20) NOT NULL,
  `id_compra` int(11) NOT NULL,
  `total` double NOT NULL,
  `cantidad` int(11) NOT NULL,
  `costo_repuesto` double NOT NULL,
  `id_repuesto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_compra`
--

INSERT INTO `detalle_compra` (`id_detalle_compra`, `id_compra`, `total`, `cantidad`, `costo_repuesto`, `id_repuesto`) VALUES
(8, 4, 1125, 75, 15, 30),
(9, 5, 1150, 230, 5, 21),
(10, 5, 900, 180, 5, 22),
(11, 5, 1000, 200, 5, 23),
(12, 5, 1075, 215, 5, 24),
(13, 5, 950, 190, 5, 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `id_detalle_venta` bigint(20) NOT NULL,
  `id_venta` int(11) NOT NULL,
  `total` double NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_unitario_repuesto` double NOT NULL,
  `precio_sugerido_repuesto` double NOT NULL,
  `id_repuesto` int(11) NOT NULL,
  `costo_repuesto` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_venta`
--

INSERT INTO `detalle_venta` (`id_detalle_venta`, `id_venta`, `total`, `cantidad`, `precio_unitario_repuesto`, `precio_sugerido_repuesto`, `id_repuesto`, `costo_repuesto`) VALUES
(5001, 1001, 130, 2, 65, 65, 14, 0),
(5002, 1001, 65, 1, 65, 65, 20, 0),
(5003, 1002, 180, 2, 90, 90, 13, 0),
(5004, 1002, 20, 1, 20, 20, 17, 0),
(5005, 1003, 210, 2, 105, 105, 19, 0),
(5006, 1004, 1440, 16, 90, 90, 12, 0),
(5007, 1004, 1040, 52, 20, 20, 18, 0),
(5008, 1004, 360, 18, 20, 20, 18, 0),
(5009, 1004, 2679, 19, 141, 141, 9, 0),
(5010, 1005, 180, 2, 90, 90, 13, 0),
(5011, 1005, 270, 3, 90, 90, 13, 0),
(5012, 1006, 105, 1, 105, 105, 19, 0),
(5013, 1007, 65, 1, 65, 65, 16, 0),
(5014, 1007, 130, 2, 65, 65, 16, 0),
(5015, 1008, 105, 1, 105, 105, 19, 0),
(5016, 1008, 141, 1, 141, 141, 10, 0),
(5017, 1009, 845, 13, 65, 65, 15, 0),
(5018, 1009, 910, 14, 65, 65, 14, 0),
(5019, 1009, 900, 10, 90, 90, 13, 0),
(5020, 1009, 1692, 12, 141, 141, 9, 0),
(5021, 1010, 141, 1, 141, 141, 10, 0),
(5022, 1010, 105, 1, 105, 105, 19, 0),
(5023, 1011, 1080, 12, 90, 90, 13, 0),
(5024, 1011, 1080, 12, 90, 90, 12, 0),
(5025, 1011, 1350, 15, 90, 90, 13, 0),
(5026, 1011, 760, 38, 20, 20, 17, 0),
(5027, 1012, 141, 1, 141, 141, 10, 0),
(5028, 1012, 65, 1, 65, 65, 14, 0),
(5029, 1013, 195, 3, 65, 65, 16, 0),
(5030, 1014, 210, 2, 105, 105, 19, 0),
(5031, 1014, 40, 2, 20, 20, 18, 0),
(5032, 1014, 60, 3, 20, 20, 17, 0),
(5033, 1015, 282, 2, 141, 141, 10, 0),
(5034, 1015, 141, 1, 141, 141, 10, 0),
(5035, 1016, 210, 2, 105, 105, 19, 0),
(5036, 1016, 90, 1, 90, 90, 12, 0),
(5037, 1016, 20, 1, 20, 20, 18, 0),
(5038, 1017, 65, 1, 65, 65, 16, 0),
(5039, 1017, 130, 2, 65, 65, 16, 0),
(5040, 1018, 105, 1, 105, 105, 19, 0),
(5041, 1018, 90, 1, 90, 90, 13, 0),
(5042, 1019, 1300, 20, 65, 65, 16, 0),
(5043, 1019, 780, 12, 65, 65, 20, 0),
(5044, 1019, 480, 24, 20, 20, 18, 0),
(5045, 1019, 600, 30, 20, 20, 17, 0),
(5046, 1020, 65, 1, 65, 65, 20, 0),
(5047, 1020, 65, 1, 65, 65, 20, 0),
(5048, 1021, 960, 48, 20, 20, 17, 0),
(5049, 1021, 1040, 16, 65, 65, 14, 0),
(5050, 1021, 1692, 12, 141, 141, 10, 0),
(5051, 1021, 1620, 18, 90, 90, 12, 0),
(5052, 1021, 560, 28, 20, 20, 18, 0),
(5053, 1022, 141, 1, 141, 141, 9, 0),
(5054, 1022, 20, 1, 20, 20, 18, 0),
(5055, 1022, 20, 1, 20, 20, 17, 0),
(5056, 1023, 65, 1, 65, 65, 20, 0),
(5057, 1023, 65, 1, 65, 65, 15, 0),
(5058, 1024, 141, 1, 141, 141, 9, 0),
(5059, 1025, 65, 1, 65, 65, 16, 0),
(5060, 1025, 130, 2, 65, 65, 15, 0),
(5061, 1026, 141, 1, 141, 141, 9, 0),
(5062, 1026, 65, 1, 65, 65, 16, 0),
(5063, 1027, 1260, 12, 105, 105, 19, 0),
(5064, 1027, 1440, 16, 90, 90, 12, 0),
(5065, 1027, 900, 45, 20, 20, 17, 0),
(5066, 1027, 900, 10, 90, 90, 12, 0),
(5067, 1028, 180, 2, 90, 90, 12, 0),
(5068, 1029, 141, 1, 141, 141, 10, 0),
(5069, 1030, 90, 1, 90, 90, 13, 0),
(5070, 1030, 65, 1, 65, 65, 15, 0),
(5071, 1031, 90, 1, 90, 90, 13, 0),
(5072, 1031, 180, 2, 90, 90, 12, 0),
(5073, 1032, 105, 1, 105, 105, 19, 0),
(5074, 1032, 210, 2, 105, 105, 19, 0),
(5075, 1033, 282, 2, 141, 141, 10, 0),
(5076, 1033, 141, 1, 141, 141, 10, 0),
(5077, 1034, 141, 1, 141, 141, 10, 0),
(5078, 1034, 141, 1, 141, 141, 10, 0),
(5079, 1035, 141, 1, 141, 141, 9, 0),
(5080, 1036, 180, 2, 90, 90, 12, 0),
(5081, 1037, 1800, 20, 90, 90, 12, 0),
(5082, 1037, 1260, 12, 105, 105, 19, 0),
(5083, 1037, 640, 32, 20, 20, 18, 0),
(5084, 1037, 460, 23, 20, 20, 18, 0),
(5085, 1038, 65, 1, 65, 65, 16, 0),
(5086, 1039, 195, 3, 65, 65, 14, 0),
(5087, 1040, 180, 2, 90, 90, 12, 0),
(5088, 1041, 65, 1, 65, 65, 15, 0),
(5089, 1041, 130, 2, 65, 65, 14, 0),
(5090, 1042, 130, 2, 65, 65, 20, 0),
(5091, 1043, 1128, 8, 141, 141, 9, 0),
(5092, 1043, 1260, 14, 90, 90, 12, 0),
(5093, 1043, 1470, 14, 105, 105, 19, 0),
(5094, 1043, 900, 10, 90, 90, 13, 0),
(5095, 1043, 1300, 20, 65, 65, 14, 0),
(5096, 1044, 210, 2, 105, 105, 19, 0),
(5097, 1044, 105, 1, 105, 105, 19, 0),
(5098, 1045, 1410, 10, 141, 141, 9, 0),
(5099, 1045, 960, 48, 20, 20, 17, 0),
(5100, 1045, 1080, 12, 90, 90, 12, 0),
(5101, 1045, 650, 10, 65, 65, 16, 0),
(5102, 1046, 65, 1, 65, 65, 20, 0),
(5103, 1046, 130, 2, 65, 65, 16, 0),
(5104, 1047, 40, 2, 20, 20, 18, 0),
(5105, 1047, 130, 2, 65, 65, 14, 0),
(5106, 1048, 130, 2, 65, 65, 15, 0),
(5107, 1049, 210, 2, 105, 105, 19, 0),
(5108, 1049, 90, 1, 90, 90, 12, 0),
(5109, 1050, 141, 1, 141, 141, 9, 0),
(5110, 1050, 105, 1, 105, 105, 19, 0),
(5111, 1051, 105, 1, 105, 105, 19, 0),
(5112, 1051, 65, 1, 65, 65, 16, 0),
(5113, 1052, 210, 2, 105, 105, 19, 0),
(5114, 1052, 130, 2, 65, 65, 20, 0),
(5115, 1053, 270, 3, 90, 90, 13, 0),
(5116, 1054, 105, 1, 105, 105, 19, 0),
(5117, 1055, 270, 3, 90, 90, 13, 0),
(5118, 1055, 141, 1, 141, 141, 9, 0),
(5119, 1056, 315, 3, 105, 105, 19, 0),
(5120, 1057, 210, 2, 105, 105, 19, 0),
(5121, 1058, 270, 3, 90, 90, 12, 0),
(5122, 1059, 210, 2, 105, 105, 19, 0),
(5123, 1059, 105, 1, 105, 105, 19, 0),
(5124, 1060, 130, 2, 65, 65, 20, 0),
(5125, 1060, 65, 1, 65, 65, 20, 0),
(5126, 1061, 315, 3, 105, 105, 19, 0),
(5127, 1062, 564, 4, 141, 141, 9, 0),
(5128, 1062, 200, 10, 20, 20, 17, 0),
(5129, 1062, 1980, 22, 90, 90, 13, 0),
(5130, 1062, 1260, 12, 105, 105, 19, 0),
(5131, 1063, 315, 3, 105, 105, 19, 0),
(5132, 1064, 315, 3, 105, 105, 19, 0),
(5133, 1065, 315, 3, 105, 105, 19, 0),
(5134, 1066, 910, 14, 65, 65, 14, 0),
(5135, 1066, 1575, 15, 105, 105, 19, 0),
(5136, 1066, 440, 22, 20, 20, 17, 0),
(5137, 1066, 1170, 18, 65, 65, 20, 0),
(5138, 1067, 180, 2, 90, 90, 13, 0),
(5139, 1068, 65, 1, 65, 65, 15, 0),
(5140, 1068, 130, 2, 65, 65, 20, 0),
(5141, 1069, 360, 4, 90, 90, 12, 0),
(5142, 1070, 282, 2, 141, 141, 10, 0),
(5143, 1071, 840, 42, 20, 20, 17, 0),
(5144, 1071, 780, 12, 65, 65, 14, 0),
(5145, 1071, 1040, 16, 65, 65, 20, 0),
(5146, 1071, 910, 14, 65, 65, 16, 0),
(5147, 1072, 180, 2, 90, 90, 13, 0),
(5148, 1073, 195, 3, 65, 65, 15, 0),
(5149, 1074, 180, 2, 90, 90, 12, 0),
(5150, 1075, 65, 1, 65, 65, 14, 0),
(5151, 1076, 195, 3, 65, 65, 14, 0),
(5152, 1077, 480, 24, 20, 20, 18, 0),
(5153, 1077, 1692, 12, 141, 141, 9, 0),
(5154, 1077, 400, 20, 20, 20, 17, 0),
(5155, 1078, 180, 2, 90, 90, 13, 0),
(5156, 1079, 40, 2, 20, 20, 18, 0),
(5157, 1080, 105, 1, 105, 105, 19, 0),
(5158, 1081, 1620, 18, 90, 90, 13, 0),
(5159, 1081, 845, 13, 65, 65, 14, 0),
(5160, 1081, 1260, 12, 105, 105, 19, 0),
(5161, 1081, 700, 35, 20, 20, 18, 0),
(5162, 1082, 210, 2, 105, 105, 19, 0),
(5163, 1083, 195, 3, 65, 65, 15, 0),
(5164, 1084, 315, 3, 105, 105, 19, 0),
(5165, 1085, 130, 2, 65, 65, 14, 0),
(5166, 1086, 650, 10, 65, 65, 20, 0),
(5167, 1086, 650, 10, 65, 65, 16, 0),
(5168, 1086, 560, 28, 20, 20, 18, 0),
(5169, 1086, 960, 48, 20, 20, 17, 0),
(5170, 1087, 270, 3, 90, 90, 13, 0),
(5171, 1088, 282, 2, 141, 141, 9, 0),
(5172, 1089, 195, 3, 65, 65, 16, 0),
(5173, 1090, 141, 1, 141, 141, 10, 0),
(5174, 1090, 141, 1, 141, 141, 9, 0),
(5175, 1091, 130, 2, 65, 65, 16, 0),
(5176, 1092, 195, 3, 65, 65, 16, 0),
(5177, 1093, 105, 1, 105, 105, 19, 0),
(5178, 1094, 210, 2, 105, 105, 19, 0),
(5179, 1095, 105, 1, 105, 105, 19, 0),
(5180, 1096, 315, 3, 105, 105, 19, 0),
(5181, 1097, 282, 2, 141, 141, 9, 0),
(5182, 1098, 141, 1, 141, 141, 10, 0),
(5183, 1099, 195, 3, 65, 65, 16, 0),
(5184, 1100, 282, 2, 141, 141, 10, 0),
(5185, 1101, 423, 3, 141, 141, 10, 0),
(5186, 1102, 315, 3, 105, 105, 19, 0),
(5187, 1103, 65, 1, 65, 65, 20, 0),
(5188, 1104, 282, 2, 141, 141, 10, 0),
(5189, 1105, 282, 2, 141, 141, 9, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `repuesto`
--

CREATE TABLE `repuesto` (
  `id_repuesto` int(11) NOT NULL,
  `nombre_repuesto` varchar(255) DEFAULT NULL,
  `stock_actual` int(11) NOT NULL,
  `costo_repuesto` int(11) DEFAULT NULL,
  `precio_sugerido` double NOT NULL,
  `estado_repuesto` tinyint(1) DEFAULT 1,
  `tiempo_entrega` int(11) NOT NULL DEFAULT 1 COMMENT 'Lead Time en días',
  `stock_seguridad` int(11) NOT NULL DEFAULT 5 COMMENT 'Stock mínimo de colchón',
  `costo_pedido` double NOT NULL DEFAULT 10 COMMENT 'Costo S: Cuánto cuesta hacer un pedido',
  `costo_almacenamiento` double NOT NULL DEFAULT 2 COMMENT 'Costo H: Costo de mantener inventario anual',
  `demanda_anual_estimada` int(11) NOT NULL DEFAULT 1000 COMMENT 'D: Para calcular EOQ fácilmente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `repuesto`
--

INSERT INTO `repuesto` (`id_repuesto`, `nombre_repuesto`, `stock_actual`, `costo_repuesto`, `precio_sugerido`, `estado_repuesto`, `tiempo_entrega`, `stock_seguridad`, `costo_pedido`, `costo_almacenamiento`, `demanda_anual_estimada`) VALUES
(9, 'Tannat Reserva', 100, 85, 141, 1, 5, 5, 50, 2, 1000),
(10, 'Syrah Reserva', 100, 85, 141, 1, 5, 5, 50, 2, 1000),
(12, 'Syrah Barbacana', 100, 54, 90, 1, 5, 5, 50, 2, 1000),
(13, 'Tannat Barbacana', 100, 54, 90, 1, 5, 5, 50, 2, 1000),
(14, 'Cofradia Rose', 100, 39, 65, 1, 5, 5, 50, 2, 1000),
(15, 'Cofradia Blanco', 100, 39, 65, 1, 5, 5, 50, 2, 1000),
(16, 'Cofradia Tinto', 100, 39, 65, 1, 5, 5, 50, 2, 1000),
(17, 'Querencia Blanco', 100, 12, 20, 1, 5, 5, 50, 2, 1000),
(18, 'Querencia Tinto', 100, 12, 20, 1, 5, 5, 50, 2, 1000),
(19, 'Singani Barbacana Premium', 100, 58, 105, 1, 7, 5, 60, 2, 1000),
(20, 'Singani Barbacana Single', 100, 36, 65, 1, 7, 5, 60, 2, 1000),
(21, 'Uva Moscatel Alejandria', 230, 5, 0, 1, 1, 5, 10, 2, 1000),
(22, 'Uva Tannat', 180, 5, 0, 1, 1, 5, 10, 2, 1000),
(23, 'Uva Malbec', 200, 5, 0, 1, 1, 5, 10, 2, 1000),
(24, 'Uva Cabernet Sauvignon', 215, 5, 0, 1, 1, 5, 10, 2, 1000),
(25, 'Uva Syrah', 190, 5, 0, 1, 1, 5, 10, 2, 1000),
(26, 'Azucar', 530, 0, 0, 1, 1, 5, 10, 2, 1000),
(27, 'Botellas', 320, 4, 0, 1, 2, 5, 10, 2, 5000),
(28, 'Corchos', 170, 1, 0, 1, 15, 5, 10, 2, 5000),
(29, 'Cajas de vinos', 97, 5, 0, 1, 1, 5, 10, 2, 200),
(30, 'Etiquetas', 75, 15, 0, 1, 3, 5, 10, 2, 5000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `repuesto_categoria`
--

CREATE TABLE `repuesto_categoria` (
  `id_repuesto_categoria` int(11) NOT NULL,
  `id_repuesto` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `repuesto_categoria`
--

INSERT INTO `repuesto_categoria` (`id_repuesto_categoria`, `id_repuesto`, `id_categoria`) VALUES
(31, 9, 1),
(32, 10, 1),
(33, 12, 1),
(34, 13, 1),
(35, 14, 1),
(36, 15, 1),
(37, 16, 1),
(38, 17, 1),
(39, 18, 1),
(40, 19, 2),
(41, 20, 2),
(23, 21, 3),
(21, 22, 3),
(24, 23, 3),
(25, 24, 3),
(22, 25, 3),
(26, 26, 11),
(27, 27, 11),
(29, 28, 11),
(28, 29, 11),
(30, 30, 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `id_venta` int(11) NOT NULL,
  `nombre_cliente` varchar(255) DEFAULT NULL,
  `fecha_venta` datetime NOT NULL,
  `total` double NOT NULL,
  `descuento_total` double NOT NULL,
  `estado_venta` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`id_venta`, `nombre_cliente`, `fecha_venta`, `total`, `descuento_total`, `estado_venta`) VALUES
(1001, 'Elena Flores', '2025-11-20 00:00:00', 195, 0, 1),
(1002, 'Jorge Rodríguez', '2025-10-31 00:00:00', 185, 0, 1),
(1003, 'Daniel López', '2025-10-18 00:00:00', 210, 0, 1),
(1004, 'Eventos La Casona', '2025-10-15 00:00:00', 3213, 0, 1),
(1005, 'Patricia Gareca', '2025-11-04 00:00:00', 450, 0, 1),
(1006, 'Diego Rojas', '2025-11-01 00:00:00', 105, 0, 1),
(1007, 'Valentina Pérez', '2025-11-14 00:00:00', 195, 0, 1),
(1008, 'Luis Martínez', '2025-11-14 00:00:00', 246, 0, 1),
(1009, 'Restaurante El Fogón', '2025-10-27 00:00:00', 3747, 0, 1),
(1010, 'Carlos Martínez', '2025-11-09 00:00:00', 246, 0, 1),
(1011, 'Supermercado Urkupiña', '2025-10-15 00:00:00', 4536, 0, 1),
(1012, 'Andrés Gareca', '2025-10-14 00:00:00', 206, 0, 1),
(1013, 'Ana Martínez', '2025-10-21 00:00:00', 195, 0, 1),
(1014, 'Andrés Rodríguez', '2025-10-09 00:00:00', 396, 0, 1),
(1015, 'Pedro Rodríguez', '2025-11-04 00:00:00', 423, 0, 1),
(1016, 'Juan Rodríguez', '2025-10-11 00:00:00', 271, 0, 1),
(1017, 'Roberto Ruiz', '2025-10-22 00:00:00', 195, 0, 1),
(1018, 'Patricia Gómez', '2025-10-14 00:00:00', 155, 0, 1),
(1019, 'Licorería Central', '2025-11-02 00:00:00', 3406, 0, 1),
(1020, 'María Daza', '2025-10-31 00:00:00', 130, 0, 1),
(1021, 'Hotel Los Parrales', '2025-10-23 00:00:00', 5928, 0, 1),
(1022, 'Carlos Mamani', '2025-11-06 00:00:00', 211, 0, 1),
(1023, 'Jorge Rojas', '2025-11-01 00:00:00', 130, 0, 1),
(1024, 'Fernando Vargas', '2025-11-12 00:00:00', 141, 0, 1),
(1025, 'Mateo Gómez', '2025-10-03 00:00:00', 195, 0, 1),
(1026, 'Pedro Arias', '2025-10-31 00:00:00', 206, 0, 1),
(1027, 'Vinos y Licores El Valle', '2025-11-23 00:00:00', 4390, 0, 1),
(1028, 'Fernando Arias', '2025-10-06 00:00:00', 180, 0, 1),
(1029, 'Ana Vargas', '2025-10-12 00:00:00', 141, 0, 1),
(1030, 'Camila Daza', '2025-10-07 00:00:00', 155, 0, 1),
(1031, 'Valentina Mamani', '2025-10-21 00:00:00', 270, 0, 1),
(1032, 'Andrés Villarroel', '2025-10-27 00:00:00', 315, 0, 1),
(1033, 'Pedro Gómez', '2025-11-19 00:00:00', 423, 0, 1),
(1034, 'Camila Villarroel', '2025-10-18 00:00:00', 282, 0, 1),
(1035, 'Fernando López', '2025-11-14 00:00:00', 141, 0, 1),
(1036, 'Roberto Rodríguez', '2025-11-20 00:00:00', 180, 0, 1),
(1037, 'Peña Folklorica El Molino', '2025-11-10 00:00:00', 4866, 0, 1),
(1038, 'Andrés Vargas', '2025-11-16 00:00:00', 45, 0, 1),
(1039, 'Elena Pérez', '2025-10-04 00:00:00', 195, 0, 1),
(1040, 'María Mamani', '2025-10-31 00:00:00', 180, 0, 1),
(1041, 'Carlos Mamani', '2025-10-19 00:00:00', 195, 0, 1),
(1042, 'Roberto Salinas', '2025-10-25 00:00:00', 130, 0, 1),
(1043, 'Vinos y Licores El Valle', '2025-11-17 00:00:00', 6432, 0, 1),
(1044, 'Gabriela Ruiz', '2025-10-02 00:00:00', 411, 0, 1),
(1045, 'Supermercado Urkupiña', '2025-11-21 00:00:00', 4280, 0, 1),
(1046, 'Lucía Mamani', '2025-10-31 00:00:00', 195, 0, 1),
(1047, 'María Mamani', '2025-10-05 00:00:00', 170, 0, 1),
(1048, 'Sofía Fernández', '2025-10-04 00:00:00', 130, 0, 1),
(1049, 'María Arias', '2025-11-21 00:00:00', 291, 0, 1),
(1050, 'Andrés Salinas', '2025-10-18 00:00:00', 246, 0, 1),
(1051, 'Gabriela Gómez', '2025-11-18 00:00:00', 170, 0, 1),
(1052, 'Roberto Villarroel', '2025-10-14 00:00:00', 336, 0, 1),
(1053, 'Elena Fernández', '2025-10-15 00:00:00', 270, 0, 1),
(1054, 'Patricia Rodríguez', '2025-11-21 00:00:00', 105, 0, 1),
(1055, 'Fernando Salinas', '2025-11-13 00:00:00', 411, 0, 1),
(1056, 'Valentina Gareca', '2025-11-14 00:00:00', 315, 0, 1),
(1057, 'Mateo Villarroel', '2025-11-06 00:00:00', 210, 0, 1),
(1058, 'Carlos Villarroel', '2025-10-10 00:00:00', 270, 0, 1),
(1059, 'María Gareca', '2025-11-07 00:00:00', 321, 0, 1),
(1060, 'Luis Villarroel', '2025-11-03 00:00:00', 195, 0, 1),
(1061, 'Fernando Salinas', '2025-10-21 00:00:00', 375, 0, 1),
(1062, 'Licorería Central', '2025-10-01 00:00:00', 3632, 0, 1),
(1063, 'Juan Mamani', '2025-11-01 00:00:00', 321, 0, 1),
(1064, 'Mateo Salinas', '2025-10-28 00:00:00', 321, 0, 1),
(1065, 'Juan Pérez', '2025-10-28 00:00:00', 321, 0, 1),
(1066, 'Licorería Central', '2025-10-25 00:00:00', 4505, 0, 1),
(1067, 'Valentina Ruiz', '2025-11-20 00:00:00', 180, 0, 1),
(1068, 'Carlos Fernández', '2025-10-19 00:00:00', 195, 0, 1),
(1069, 'Elena Ruiz', '2025-10-19 00:00:00', 360, 0, 1),
(1070, 'Fernando Villarroel', '2025-10-14 00:00:00', 282, 0, 1),
(1071, 'Vinos y Licores El Valle', '2025-10-19 00:00:00', 3672, 0, 1),
(1072, 'Ana Villarroel', '2025-10-20 00:00:00', 180, 0, 1),
(1073, 'Valentina Salinas', '2025-10-08 00:00:00', 195, 0, 1),
(1074, 'Roberto Salinas', '2025-11-03 00:00:00', 180, 0, 1),
(1075, 'Andrés Daza', '2025-11-09 00:00:00', 45, 0, 1),
(1076, 'Luis Mamani', '2025-11-04 00:00:00', 231, 0, 1),
(1077, 'Hotel Los Parrales', '2025-10-26 00:00:00', 3212, 0, 1),
(1078, 'Juan López', '2025-10-31 00:00:00', 180, 0, 1),
(1079, 'Valentina Mamani', '2025-11-13 00:00:00', 40, 0, 1),
(1080, 'Ana Gareca', '2025-10-30 00:00:00', 205, 0, 1),
(1081, 'Restaurante El Fogón', '2025-11-17 00:00:00', 4545, 0, 1),
(1082, 'Gabriela Pérez', '2025-11-16 00:00:00', 210, 0, 1),
(1083, 'Pedro Villarroel', '2025-11-19 00:00:00', 231, 0, 1),
(1084, 'Daniel Ruiz', '2025-11-23 00:00:00', 315, 0, 1),
(1085, 'Ana Gareca', '2025-10-29 00:00:00', 130, 0, 1),
(1086, 'Restaurante La Cabaña', '2025-11-15 00:00:00', 2862, 0, 1),
(1087, 'Fernando Pérez', '2025-11-23 00:00:00', 270, 0, 1),
(1088, 'Mateo Rojas', '2025-10-29 00:00:00', 282, 0, 1),
(1089, 'Diego Gómez', '2025-11-02 00:00:00', 195, 0, 1),
(1090, 'Sofía Martínez', '2025-11-04 00:00:00', 282, 0, 1),
(1091, 'Juan Villarroel', '2025-10-25 00:00:00', 130, 0, 1),
(1092, 'Valentina López', '2025-11-12 00:00:00', 195, 0, 1),
(1093, 'Diego Arias', '2025-11-09 00:00:00', 245, 0, 1),
(1094, 'Carlos Rojas', '2025-11-03 00:00:00', 210, 0, 1),
(1095, 'Lucía Fernández', '2025-10-05 00:00:00', 105, 0, 1),
(1096, 'Valentina Daza', '2025-10-18 00:00:00', 315, 0, 1),
(1097, 'Jorge Rodríguez', '2025-10-22 00:00:00', 170, 0, 1),
(1098, 'Valentina Salinas', '2025-11-20 00:00:00', 170, 0, 1),
(1099, 'Roberto Villarroel', '2025-11-10 00:00:00', 231, 0, 1),
(1100, 'Roberto Gómez', '2025-11-06 00:00:00', 170, 0, 1),
(1101, 'Lucía Rojas', '2025-11-20 00:00:00', 255, 0, 1),
(1102, 'Ana Gómez', '2025-10-27 00:00:00', 375, 0, 1),
(1103, 'Diego Gutiérrez', '2025-11-09 00:00:00', 45, 0, 1),
(1104, 'Fernando Vargas', '2025-10-11 00:00:00', 246, 0, 1),
(1105, 'Lucía Martínez', '2025-11-23 00:00:00', 282, 0, 1);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vista_trazabilidad`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `vista_trazabilidad` (
`fecha` datetime
,`producto` varchar(255)
,`tipo_movimiento` varchar(7)
,`cantidad` bigint(12)
,`id_repuesto` int(11)
);

-- --------------------------------------------------------

--
-- Estructura para la vista `vista_trazabilidad`
--
DROP TABLE IF EXISTS `vista_trazabilidad`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vista_trazabilidad`  AS   (select `c`.`fecha_compra` AS `fecha`,`r`.`nombre_repuesto` AS `producto`,'ENTRADA' AS `tipo_movimiento`,`dc`.`cantidad` AS `cantidad`,`r`.`id_repuesto` AS `id_repuesto` from ((`detalle_compra` `dc` join `compra` `c` on(`dc`.`id_compra` = `c`.`id_compra`)) join `repuesto` `r` on(`dc`.`id_repuesto` = `r`.`id_repuesto`))) union all (select `v`.`fecha_venta` AS `fecha`,`r`.`nombre_repuesto` AS `producto`,'SALIDA' AS `tipo_movimiento`,`dv`.`cantidad` * -1 AS `cantidad`,`r`.`id_repuesto` AS `id_repuesto` from ((`detalle_venta` `dv` join `venta` `v` on(`dv`.`id_venta` = `v`.`id_venta`)) join `repuesto` `r` on(`dv`.`id_repuesto` = `r`.`id_repuesto`))) order by `fecha` desc  ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id_compra`);

--
-- Indices de la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  ADD PRIMARY KEY (`id_detalle_compra`),
  ADD KEY `id_compra` (`id_compra`),
  ADD KEY `id_repuesto` (`id_repuesto`);

--
-- Indices de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD PRIMARY KEY (`id_detalle_venta`),
  ADD KEY `id_venta` (`id_venta`),
  ADD KEY `id_repuesto` (`id_repuesto`);

--
-- Indices de la tabla `repuesto`
--
ALTER TABLE `repuesto`
  ADD PRIMARY KEY (`id_repuesto`);

--
-- Indices de la tabla `repuesto_categoria`
--
ALTER TABLE `repuesto_categoria`
  ADD PRIMARY KEY (`id_repuesto_categoria`),
  ADD UNIQUE KEY `idx_repuesto_categoria_unique` (`id_repuesto`,`id_categoria`),
  ADD KEY `id_categoria` (`id_categoria`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`id_venta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `id_compra` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  MODIFY `id_detalle_compra` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id_detalle_venta` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5190;

--
-- AUTO_INCREMENT de la tabla `repuesto`
--
ALTER TABLE `repuesto`
  MODIFY `id_repuesto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `repuesto_categoria`
--
ALTER TABLE `repuesto_categoria`
  MODIFY `id_repuesto_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1106;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_compra`
--
ALTER TABLE `detalle_compra`
  ADD CONSTRAINT `detalle_compra_ibfk_1` FOREIGN KEY (`id_compra`) REFERENCES `compra` (`id_compra`),
  ADD CONSTRAINT `detalle_compra_ibfk_2` FOREIGN KEY (`id_repuesto`) REFERENCES `repuesto` (`id_repuesto`);

--
-- Filtros para la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`id_venta`) REFERENCES `venta` (`id_venta`),
  ADD CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`id_repuesto`) REFERENCES `repuesto` (`id_repuesto`);

--
-- Filtros para la tabla `repuesto_categoria`
--
ALTER TABLE `repuesto_categoria`
  ADD CONSTRAINT `repuesto_categoria_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`),
  ADD CONSTRAINT `repuesto_categoria_ibfk_2` FOREIGN KEY (`id_repuesto`) REFERENCES `repuesto` (`id_repuesto`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
