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
-- Table structure for table `fornitura`
--

DROP TABLE IF EXISTS `fornitura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornitura` (
  `ID_Fornitore` int NOT NULL,
  `ID_Prodotto` int NOT NULL,
  `ppu` decimal(3,2) NOT NULL,
  PRIMARY KEY (`ID_Fornitore`,`ID_Prodotto`),
  KEY `ID_Prodotto` (`ID_Prodotto`),
  CONSTRAINT `fornitura_ibfk_1` FOREIGN KEY (`ID_Fornitore`) REFERENCES `fornitore` (`ID_Fornitore`),
  CONSTRAINT `fornitura_ibfk_2` FOREIGN KEY (`ID_Prodotto`) REFERENCES `prodotto` (`ID_Prodotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitura`
--

LOCK TABLES `fornitura` WRITE;
/*!40000 ALTER TABLE `fornitura` DISABLE KEYS */;
INSERT INTO `fornitura` VALUES (1,1,0.40),(1,2,0.30),(1,4,0.45),(1,8,0.40),(1,12,0.40),(1,17,0.40),(1,22,0.40),(1,26,0.35),(1,31,0.40),(1,37,0.30),(1,41,0.40),(1,44,0.20),(1,45,0.30),(1,46,0.35),(1,47,0.25),(1,48,0.30),(2,1,0.35),(2,3,0.45),(2,9,0.35),(2,13,0.35),(2,18,0.35),(2,22,0.45),(2,26,0.40),(2,32,0.35),(2,35,0.35),(2,39,0.30),(2,59,0.15),(2,60,0.20),(3,2,0.40),(3,5,0.30),(3,10,0.35),(3,15,0.45),(3,19,0.40),(3,23,0.20),(3,29,0.45),(3,33,0.30),(3,37,0.25),(3,41,0.35),(3,44,0.30),(3,45,0.20),(3,46,0.30),(3,47,0.25),(3,48,0.25),(4,3,0.40),(4,6,0.40),(4,11,0.40),(4,16,0.45),(4,20,0.30),(4,23,0.15),(4,27,0.45),(4,35,0.40),(4,40,0.30),(4,49,0.40),(4,50,0.30),(4,51,0.25),(4,52,0.30),(4,53,0.25),(5,4,0.40),(5,7,0.40),(5,14,0.40),(5,19,0.45),(5,25,0.40),(5,30,0.40),(5,33,0.25),(5,38,0.35),(6,5,0.35),(6,8,0.45),(6,13,0.30),(6,18,0.30),(6,24,0.30),(6,28,0.45),(6,31,0.45),(6,34,0.35),(6,42,0.45),(6,54,0.30),(6,55,0.30),(6,56,0.25),(6,57,0.20),(6,58,0.25),(7,6,0.45),(7,9,0.40),(7,14,0.35),(7,21,0.35),(7,27,0.40),(7,32,0.40),(7,34,0.30),(7,38,0.30),(7,43,0.45),(7,59,0.35),(7,60,0.35),(8,7,0.35),(8,10,0.40),(8,15,0.40),(8,20,0.35),(8,24,0.25),(8,28,0.40),(8,36,0.35),(8,43,0.40),(8,49,0.30),(8,50,0.25),(8,51,0.15),(8,52,0.20),(8,53,0.20),(9,11,0.45),(9,16,0.40),(9,21,0.40),(9,29,0.40),(9,39,0.35),(9,42,0.40),(10,12,0.45),(10,17,0.45),(10,25,0.45),(10,30,0.45),(10,36,0.30),(10,40,0.35),(10,54,0.10),(10,55,0.20),(10,56,0.20),(10,57,0.10),(10,58,0.20);
/*!40000 ALTER TABLE `fornitura` ENABLE KEYS */;
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
