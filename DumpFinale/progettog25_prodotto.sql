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
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodotto` (
  `ID_Prodotto` int NOT NULL AUTO_INCREMENT,
  `Nome_p` varchar(75) NOT NULL,
  `Prezzo` decimal(3,2) NOT NULL,
  `Categoria_P` enum('bevanda calda','bevanda fredda','snack dolce','snack salato') DEFAULT NULL,
  PRIMARY KEY (`ID_Prodotto`),
  UNIQUE KEY `Nome_p` (`Nome_p`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES (1,'Fonzies',1.00,'snack salato'),(2,'Barretta kinder',0.80,'snack dolce'),(3,'Patatine Classiche',1.00,'snack salato'),(4,'Popcorn Salati',1.20,'snack salato'),(5,'Arachidi Tostate',0.60,'snack salato'),(6,'Crackers Salati',0.80,'snack salato'),(7,'Nachos Piccanti',1.00,'snack salato'),(8,'Salatini Mix',1.00,'snack salato'),(9,'Crostini',0.90,'snack salato'),(10,'Mandorle Salate',0.90,'snack salato'),(11,'Chipster',1.00,'snack salato'),(12,'Snack al Formaggio',1.50,'snack salato'),(13,'Barretta Milka',0.90,'snack dolce'),(14,'Barretta ai Cereali',1.00,'snack dolce'),(15,'Cioccolato Fondente',1.50,'snack dolce'),(16,'Wafer alla Nocciola',1.10,'snack dolce'),(17,'KitKat',1.10,'snack dolce'),(18,'Barretta Proteica',0.80,'snack dolce'),(19,'Muffin al Cioccolato',1.20,'snack dolce'),(20,'Caramelle Gelatine',0.80,'snack dolce'),(21,'Ringo',1.00,'snack dolce'),(22,'Baiocchi',1.20,'snack dolce'),(23,'Acqua Naturale 50cl',0.50,'bevanda fredda'),(24,'Acqua Frizzante 50cl',0.60,'bevanda fredda'),(25,'Cola 33cl',1.10,'bevanda fredda'),(26,'Aranciata 33cl',1.00,'bevanda fredda'),(27,'Thè al Limone 33cl',1.10,'bevanda fredda'),(28,'Thè alla Pesca 33cl',1.10,'bevanda fredda'),(29,'Succo di Frutta Misto',1.30,'bevanda fredda'),(30,'Succo di Pera',1.30,'bevanda fredda'),(31,'Red Bull',2.50,'bevanda fredda'),(32,'Limonata 33cl',1.20,'bevanda fredda'),(33,'Espresso',0.60,'bevanda calda'),(34,'Cappuccino',0.80,'bevanda calda'),(35,'Thè Caldo',0.90,'bevanda calda'),(36,'Cioccolata Calda',1.00,'bevanda calda'),(37,'Latte',0.60,'bevanda calda'),(38,'Latte Macchiato',0.75,'bevanda calda'),(39,'Caffè Lungo',0.80,'bevanda calda'),(40,'Caffè Corto',0.70,'bevanda calda'),(41,'Ginseng',1.00,'bevanda calda'),(42,'Orzo',1.10,'bevanda calda'),(43,'Thè Verde',1.10,'bevanda calda'),(44,'Succo Albicocca',0.50,'bevanda fredda'),(45,'Succo Mela',0.50,'bevanda fredda'),(46,'Tarallini',0.60,'snack salato'),(47,'Croissant Albicocca',0.45,'snack dolce'),(48,'Croissant Cioccolato',0.45,'snack dolce'),(49,'Patatine Bio',0.65,'snack salato'),(50,'Tuc Cracker',1.00,'snack salato'),(51,'M&M\'s',1.10,'snack dolce'),(52,'Grissini ',0.80,'snack salato'),(53,'Tortillas',0.90,'snack salato'),(54,'Wafer Vaniglia',0.90,'snack dolce'),(55,'Crostatina Cioccolato',0.70,'snack dolce'),(56,'Kinder Delice',0.85,'snack dolce'),(57,'Biscotti',0.90,'snack dolce'),(58,'Chinò',0.90,'bevanda fredda'),(59,'Gatorade',1.00,'bevanda fredda'),(60,'Cocktail SP',0.90,'bevanda fredda');
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
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
