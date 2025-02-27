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
-- Table structure for table `stock_dettagli`
--

DROP TABLE IF EXISTS `stock_dettagli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_dettagli` (
  `ID_Inventario` int NOT NULL,
  `ID_Prodotto` int NOT NULL,
  `Q_disp` int NOT NULL DEFAULT '0',
  `Qmax_inseribile` int NOT NULL,
  `Stato` enum('Disponibile','Non disponibile','Esaurito') DEFAULT 'Disponibile',
  PRIMARY KEY (`ID_Inventario`,`ID_Prodotto`),
  KEY `ID_Prodotto` (`ID_Prodotto`),
  CONSTRAINT `stock_dettagli_ibfk_1` FOREIGN KEY (`ID_Inventario`) REFERENCES `inventario` (`ID_Inventario`),
  CONSTRAINT `stock_dettagli_ibfk_2` FOREIGN KEY (`ID_Prodotto`) REFERENCES `prodotto` (`ID_Prodotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_dettagli`
--

LOCK TABLES `stock_dettagli` WRITE;
/*!40000 ALTER TABLE `stock_dettagli` DISABLE KEYS */;
INSERT INTO `stock_dettagli` VALUES (1,33,50,50,'Disponibile'),(1,34,50,50,'Disponibile'),(1,35,50,50,'Disponibile'),(1,36,50,50,'Disponibile'),(1,37,50,50,'Disponibile'),(1,38,50,50,'Disponibile'),(1,39,50,50,'Disponibile'),(1,40,50,50,'Disponibile'),(2,1,10,10,'Disponibile'),(2,2,10,10,'Disponibile'),(2,3,10,10,'Disponibile'),(2,4,10,10,'Disponibile'),(2,5,10,10,'Disponibile'),(2,6,10,10,'Disponibile'),(2,7,10,10,'Disponibile'),(2,8,10,10,'Disponibile'),(2,9,10,10,'Disponibile'),(2,10,10,10,'Disponibile'),(2,11,10,10,'Disponibile'),(2,12,10,10,'Disponibile'),(2,13,10,10,'Disponibile'),(2,14,10,10,'Disponibile'),(2,15,10,10,'Disponibile'),(2,16,10,10,'Disponibile'),(2,17,10,10,'Disponibile'),(2,18,10,10,'Disponibile'),(2,19,10,10,'Disponibile'),(2,20,10,10,'Disponibile'),(2,21,10,10,'Disponibile'),(2,22,10,10,'Disponibile'),(2,23,10,10,'Disponibile'),(2,24,10,10,'Disponibile'),(2,26,8,10,'Disponibile'),(3,33,50,50,'Disponibile'),(3,34,50,50,'Disponibile'),(3,37,50,50,'Disponibile'),(3,38,50,50,'Disponibile'),(3,39,50,50,'Disponibile'),(3,40,50,50,'Disponibile'),(3,41,50,50,'Disponibile'),(3,43,50,50,'Disponibile'),(4,1,10,10,'Disponibile'),(4,2,10,10,'Disponibile'),(4,3,10,10,'Disponibile'),(4,4,10,10,'Disponibile'),(4,5,10,10,'Disponibile'),(4,6,10,10,'Disponibile'),(4,7,10,10,'Disponibile'),(4,8,10,10,'Disponibile'),(4,9,10,10,'Disponibile'),(4,10,10,10,'Disponibile'),(4,11,10,10,'Disponibile'),(4,12,10,10,'Disponibile'),(4,13,10,10,'Disponibile'),(4,14,10,10,'Disponibile'),(4,15,10,10,'Disponibile'),(4,16,10,10,'Disponibile'),(4,17,10,10,'Disponibile'),(4,18,10,10,'Disponibile'),(4,19,10,10,'Disponibile'),(4,23,10,10,'Disponibile'),(4,24,10,10,'Disponibile'),(4,25,10,10,'Disponibile'),(4,26,10,10,'Disponibile'),(4,31,10,10,'Disponibile'),(4,32,10,10,'Disponibile'),(5,33,50,50,'Disponibile'),(5,34,50,50,'Disponibile'),(5,35,50,50,'Disponibile'),(5,36,50,50,'Disponibile'),(5,37,50,50,'Disponibile'),(5,38,50,50,'Disponibile'),(5,39,50,50,'Disponibile'),(5,40,50,50,'Disponibile'),(6,1,10,10,'Disponibile'),(6,2,10,10,'Disponibile'),(6,3,10,10,'Disponibile'),(6,4,10,10,'Disponibile'),(6,5,10,10,'Disponibile'),(6,6,10,10,'Disponibile'),(6,7,10,10,'Disponibile'),(6,8,10,10,'Disponibile'),(6,9,10,10,'Disponibile'),(6,10,10,10,'Disponibile'),(6,11,10,10,'Disponibile'),(6,12,10,10,'Disponibile'),(6,13,10,10,'Disponibile'),(6,14,10,10,'Disponibile'),(6,15,10,10,'Disponibile'),(6,16,10,10,'Disponibile'),(6,17,10,10,'Disponibile'),(6,18,10,10,'Disponibile'),(6,19,10,10,'Disponibile'),(6,20,10,10,'Disponibile'),(6,21,10,10,'Disponibile'),(6,22,10,10,'Disponibile'),(6,23,10,10,'Disponibile'),(6,24,10,10,'Disponibile'),(6,25,10,10,'Disponibile');
/*!40000 ALTER TABLE `stock_dettagli` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-27 15:17:38
