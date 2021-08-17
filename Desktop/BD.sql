CREATE DATABASE  IF NOT EXISTS `Parking4All` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `Parking4All`;
-- MySQL dump 10.16  Distrib 10.1.36-MariaDB, for osx10.10 (x86_64)
--
-- Host: 127.0.0.1    Database: Parking4All
-- ------------------------------------------------------
-- Server version	10.1.36-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Caja`
--

DROP TABLE IF EXISTS `Caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Caja` (
  `idCaja` int(11) NOT NULL AUTO_INCREMENT,
  `FechaCierre` datetime DEFAULT NULL,
  `FechaApertura` datetime NOT NULL,
  `Total` int(11) DEFAULT NULL,
  `IdUsuarioApertura` int(11) NOT NULL,
  `IdUsuarioCierre` int(11) DEFAULT NULL,
  `Cerrada` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idCaja`),
  KEY `fk_Caja_Usuarios1_idx` (`IdUsuarioApertura`),
  CONSTRAINT `fk_Caja_Usuarios1` FOREIGN KEY (`IdUsuarioApertura`) REFERENCES `Usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Configuraciones`
--

DROP TABLE IF EXISTS `Configuraciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Configuraciones` (
  `idConfiguracion` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) DEFAULT NULL,
  `Valor` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`idConfiguracion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Mensualidades`
--

DROP TABLE IF EXISTS `Mensualidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mensualidades` (
  `idMensualidad` int(11) NOT NULL AUTO_INCREMENT,
  `Placa` varchar(7) DEFAULT NULL,
  `FechaPago` date DEFAULT NULL,
  `NumeroDocumento` varchar(20) DEFAULT NULL,
  `Nombres` varchar(45) DEFAULT NULL,
  `Telefono` varchar(45) DEFAULT NULL,
  `ValorTotal` int(11) NOT NULL,
  PRIMARY KEY (`idMensualidad`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Movimientos`
--

DROP TABLE IF EXISTS `Movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Movimientos` (
  `idMovimiento` int(11) NOT NULL AUTO_INCREMENT,
  `Placa` varchar(7) NOT NULL,
  `FechaEntrada` datetime NOT NULL,
  `FechaSalida` datetime DEFAULT NULL,
  `Finalizado` tinyint(1) DEFAULT NULL,
  `Usuarios_idUsuarioEntrada` int(11) NOT NULL,
  `Usuarios_idUsuarioSalida` int(11) DEFAULT NULL,
  `TipoTarifa` varchar(20) NOT NULL,
  `ValorTotal` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idMovimiento`),
  KEY `fk_Movimientos_Usuarios_idx` (`Usuarios_idUsuarioEntrada`),
  KEY `fk_Movimientos_Usuarios2_idx` (`Usuarios_idUsuarioSalida`),
  CONSTRAINT `fk_Movimientos_Usuarios` FOREIGN KEY (`Usuarios_idUsuarioEntrada`) REFERENCES `Usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Movimientos_Usuarios2` FOREIGN KEY (`Usuarios_idUsuarioSalida`) REFERENCES `Usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Tarifas`
--

DROP TABLE IF EXISTS `Tarifas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tarifas` (
  `idTarifa` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(20) NOT NULL,
  `Tarifa` int(11) NOT NULL,
  `Dias` int(11) DEFAULT NULL,
  `Horas` int(11) DEFAULT NULL,
  `Minutos` int(11) DEFAULT NULL,
  `TipoTarifa` varchar(20) NOT NULL,
  PRIMARY KEY (`idTarifa`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Usuarios`
--

DROP TABLE IF EXISTS `Usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Usuarios` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(20) DEFAULT NULL,
  `Username` varchar(20) DEFAULT NULL,
  `Password` varchar(20) DEFAULT NULL,
  `Administrador` tinyint(1) DEFAULT NULL,
  `Inactivo` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-10  9:01:24
