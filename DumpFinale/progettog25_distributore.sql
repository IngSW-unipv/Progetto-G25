-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: progettog25
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `distributore`
--

DROP TABLE IF EXISTS `distributore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `distributore` (
  `ID_Distributore` int NOT NULL AUTO_INCREMENT,
  `Tipo_D` enum('Bevande Calde','Cibi e Bevande Fredde') NOT NULL,
  `Citta` varchar(50) NOT NULL,
  `Via` varchar(50) NOT NULL,
  `N_civico` varchar(5) NOT NULL,
  `ID_Inventario` int DEFAULT NULL,
  `LAT` double NOT NULL,
  `LON` double NOT NULL,
  PRIMARY KEY (`ID_Distributore`),
  KEY `distributore_ibfk_1` (`ID_Inventario`),
  CONSTRAINT `distributore_ibfk_1` FOREIGN KEY (`ID_Inventario`) REFERENCES `inventario` (`ID_Inventario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distributore`
--

LOCK TABLES `distributore` WRITE;
/*!40000 ALTER TABLE `distributore` DISABLE KEYS */;
INSERT INTO `distributore` VALUES (1,'Bevande Calde','Pavia','Corso Cavour','15',1,45.1860317,9.1527992),(2,'Cibi e Bevande Fredde','Pavia','Corso Cavour','15',2,45.1860317,9.1527992),(3,'Bevande Calde','Pavia','Via A. Ferrata','5',3,45.2017974,9.1355989),(4,'Cibi e Bevande Fredde','Pavia','Via A. Ferrata','5',4,45.2017974,9.1355989),(5,'Bevande Calde','Pavia','Corso Strada Nuova','106',5,45.1863914,9.1555417),(6,'Cibi e Bevande Fredde','Pavia','Corso Strada Nuova','106',6,45.1863914,9.1555417);
/*!40000 ALTER TABLE `distributore` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-27 15:17:39
