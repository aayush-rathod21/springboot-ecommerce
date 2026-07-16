-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ecommerce_db
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `available` bit(1) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `main_id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `quantity` int NOT NULL,
  `release_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL,
  `available` bit(1) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `quantity` int NOT NULL,
  `release_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,_binary '','Nike','Shoes','High quality product with manufacturer warranty.','Air Max 270',1818.00,377,'2025-05-08'),(2,_binary '\0','Samsung','Wearables','High quality product with manufacturer warranty.','Galaxy Watch 6',17848.00,111,'2024-07-02'),(3,_binary '','HP','Laptops','High quality product with manufacturer warranty.','Envy x360',51956.00,279,'2024-04-23'),(4,_binary '','Sony','Audio','High quality product with manufacturer warranty.','WH-1000XM5',9359.00,388,'2024-05-26'),(5,_binary '','Dell','Laptops','High quality product with manufacturer warranty.','Alienware M16',8924.00,390,'2022-05-05'),(6,_binary '','Asus','Laptops','High quality product with manufacturer warranty.','ROG Strix G16',69312.00,309,'2023-02-12'),(7,_binary '\0','Lenovo','Laptops','High quality product with manufacturer warranty.','IdeaPad Slim 5',165435.00,150,'2024-02-13'),(8,_binary '\0','Nike','Shoes','High quality product with manufacturer warranty.','Pegasus 40',16507.00,35,'2026-06-19'),(9,_binary '\0','Apple','Smartphones','High quality product with manufacturer warranty.','iPhone 14',52930.00,142,'2020-04-28'),(10,_binary '','LG','Home Appliances','High quality product with manufacturer warranty.','Front Load Washer',48909.00,136,'2021-06-12'),(11,_binary '\0','Adidas','Shoes','High quality product with manufacturer warranty.','Predator League',12617.00,373,'2020-10-21'),(12,_binary '','HP','Laptops','High quality product with manufacturer warranty.','Pavilion 15',166178.00,112,'2023-05-21'),(13,_binary '','Nike','Shoes','High quality product with manufacturer warranty.','Revolution 7',15809.00,412,'2026-01-08'),(14,_binary '','Asus','Laptops','High quality product with manufacturer warranty.','ZenBook 14',115186.00,161,'2020-04-19'),(15,_binary '\0','HP','Laptops','High quality product with manufacturer warranty.','Envy x360',175870.00,71,'2023-11-15'),(16,_binary '','HP','Laptops','High quality product with manufacturer warranty.','Envy x360',113876.00,204,'2025-10-14'),(17,_binary '','Asus','Laptops','High quality product with manufacturer warranty.','ROG Strix G16',81262.00,440,'2024-08-03'),(18,_binary '','Samsung','Smartphones','High quality product with manufacturer warranty.','Galaxy A55',35969.00,197,'2026-11-14'),(19,_binary '','Sony','Television','High quality product with manufacturer warranty.','Bravia 55 TV',152696.00,5,'2024-05-18'),(20,_binary '','Nike','Shoes','High quality product with manufacturer warranty.','Pegasus 40',3876.00,328,'2025-09-25'),(21,_binary '\0','Asus','Laptops','High quality product with manufacturer warranty.','ROG Strix G16',121939.00,369,'2023-03-15'),(22,_binary '','Adidas','Shoes','High quality product with manufacturer warranty.','Duramo SL',17923.00,445,'2024-03-17'),(23,_binary '\0','Nike','Shoes','High quality product with manufacturer warranty.','Revolution 7',15789.00,191,'2025-09-20'),(24,_binary '','Dell','Laptops','High quality product with manufacturer warranty.','Alienware M16',17622.00,250,'2026-09-01'),(25,_binary '\0','Apple','Smartphones','High quality product with manufacturer warranty.','iPhone 15',62576.00,449,'2026-05-08'),(26,_binary '\0','JBL','Audio','High quality product with manufacturer warranty.','Flip 6',3805.00,389,'2025-08-27'),(27,_binary '\0','Boat','Audio','High quality product with manufacturer warranty.','Airdopes 141',5206.00,270,'2025-08-18'),(28,_binary '','JBL','Audio','High quality product with manufacturer warranty.','Charge 5',7939.00,159,'2024-12-23'),(29,_binary '\0','Sony','Television','High quality product with manufacturer warranty.','Bravia 55 TV',127889.00,115,'2023-09-15'),(30,_binary '','Samsung','Audio','High quality product with manufacturer warranty.','Galaxy Buds2',1688.00,3,'2024-09-08'),(31,_binary '','Samsung','Smartphones','High quality product with manufacturer warranty.','Galaxy S24',45007.00,263,'2020-01-28'),(32,_binary '','HP','Laptops','High quality product with manufacturer warranty.','Victus 15',172248.00,451,'2021-09-05'),(33,_binary '','JBL','Audio','High quality product with manufacturer warranty.','Tune 770NC',16487.00,48,'2021-08-26'),(34,_binary '','Samsung','Tablets','High quality product with manufacturer warranty.','Galaxy Tab S9',43219.00,27,'2023-07-15'),(35,_binary '','Nike','Shoes','High quality product with manufacturer warranty.','Pegasus 40',12587.00,409,'2020-01-13'),(36,_binary '\0','Samsung','Smartphones','High quality product with manufacturer warranty.','Galaxy A55',40112.00,93,'2021-09-15'),(37,_binary '','Lenovo','Laptops','High quality product with manufacturer warranty.','Legion 5',110485.00,438,'2026-02-15'),(38,_binary '\0','Boat','Audio','High quality product with manufacturer warranty.','Airdopes 141',2656.00,47,'2025-09-27'),(39,_binary '','HP','Laptops','High quality product with manufacturer warranty.','Pavilion 15',151539.00,462,'2023-08-07'),(40,_binary '','Apple','Smartphones','High quality product with manufacturer warranty.','iPhone 14',64672.00,401,'2020-07-09'),(41,_binary '','LG','Home Appliances','High quality product with manufacturer warranty.','Dual Inverter AC',52722.00,367,'2025-12-26'),(42,_binary '','LG','Television','High quality product with manufacturer warranty.','OLED C3 TV',79780.00,277,'2022-04-02'),(43,_binary '','Apple','Laptops','High quality product with manufacturer warranty.','MacBook Air M2',59985.00,436,'2020-10-16'),(44,_binary '\0','Boat','Audio','High quality product with manufacturer warranty.','Airdopes 141',2862.00,304,'2024-02-28'),(45,_binary '','Samsung','Smartphones','High quality product with manufacturer warranty.','Galaxy A55',67923.00,20,'2020-10-08'),(46,_binary '','JBL','Audio','High quality product with manufacturer warranty.','Flip 6',14736.00,478,'2025-10-19'),(47,_binary '\0','Lenovo','Laptops','High quality product with manufacturer warranty.','IdeaPad Slim 5',127361.00,330,'2021-05-13'),(48,_binary '','Lenovo','Laptops','High quality product with manufacturer warranty.','Legion 5',127883.00,288,'2026-02-01'),(49,_binary '\0','Samsung','Smartphones','High quality product with manufacturer warranty.','Galaxy S24',85468.00,178,'2021-09-09'),(50,_binary '','Samsung','Smartphones','High quality product with manufacturer warranty.','Galaxy A55',63434.00,360,'2022-03-15'),(51,_binary '','Lenovo','Laptops','High quality product with manufacturer warranty.','ThinkPad E14',47050.00,53,'2025-09-10'),(52,_binary '\0','Dell','Laptops','High quality product with manufacturer warranty.','XPS 13',75258.00,144,'2020-12-18'),(53,_binary '','JBL','Audio','High quality product with manufacturer warranty.','Flip 6',24513.00,135,'2022-04-22'),(54,_binary '','Boat','Audio','High quality product with manufacturer warranty.','Stone 650',9227.00,424,'2026-01-03'),(55,_binary '','Lenovo','Laptops','High quality product with manufacturer warranty.','IdeaPad Slim 5',45929.00,82,'2022-03-21'),(56,_binary '\0','Adidas','Shoes','High quality product with manufacturer warranty.','Duramo SL',11038.00,38,'2025-07-18'),(57,_binary '','Adidas','Shoes','High quality product with manufacturer warranty.','Ultraboost Light',10938.00,220,'2020-06-19'),(58,_binary '','Dell','Laptops','High quality product with manufacturer warranty.','Inspiron 15',125808.00,127,'2022-01-12'),(59,_binary '','Nike','Shoes','High quality product with manufacturer warranty.','Air Max 270',12588.00,317,'2026-09-28'),(60,_binary '','Adidas','Shoes','High quality product with manufacturer warranty.','Ultraboost Light',17167.00,451,'2021-03-26'),(61,_binary '','Sony','Audio','High quality product with manufacturer warranty.','WH-1000XM5',4938.00,410,'2025-06-26'),(62,_binary '','Nike','Shoes','High quality product with manufacturer warranty.','Pegasus 40',15283.00,55,'2021-05-06'),(63,_binary '','Sony','Audio','High quality product with manufacturer warranty.','WH-1000XM5',16067.00,235,'2023-04-07'),(64,_binary '\0','Asus','Laptops','High quality product with manufacturer warranty.','ZenBook 14',104662.00,168,'2021-01-22'),(65,_binary '','Lenovo','Laptops','High quality product with manufacturer warranty.','IdeaPad Slim 5',118170.00,431,'2022-11-17'),(66,_binary '','Boat','Audio','High quality product with manufacturer warranty.','Stone 650',1903.00,135,'2020-05-06'),(67,_binary '','Apple','Smartphones','High quality product with manufacturer warranty.','iPhone 15',71959.00,310,'2022-12-26'),(68,_binary '\0','Boat','Audio','High quality product with manufacturer warranty.','Airdopes 141',13621.00,223,'2024-04-09'),(69,_binary '','Apple','Tablets','High quality product with manufacturer warranty.','iPad Air',18642.00,100,'2025-12-24'),(70,_binary '','Asus','Laptops','High quality product with manufacturer warranty.','ZenBook 14',63343.00,434,'2025-06-20'),(71,_binary '','Samsung','Audio','High quality product with manufacturer warranty.','Galaxy Buds2',17616.00,356,'2022-11-14'),(72,_binary '','Lenovo','Laptops','High quality product with manufacturer warranty.','ThinkPad E14',78367.00,346,'2021-07-22'),(73,_binary '','Adidas','Shoes','High quality product with manufacturer warranty.','Ultraboost Light',12084.00,0,'2024-05-13'),(74,_binary '','Lenovo','Laptops','High quality product with manufacturer warranty.','Legion 5',100098.00,238,'2023-10-20'),(75,_binary '','LG','Home Appliances','High quality product with manufacturer warranty.','Dual Inverter AC',69277.00,491,'2021-09-16'),(76,_binary '','Adidas','Shoes','High quality product with manufacturer warranty.','Ultraboost Light',12794.00,317,'2020-05-17'),(77,_binary '','Asus','Laptops','High quality product with manufacturer warranty.','ROG Strix G16',106569.00,75,'2025-05-08'),(78,_binary '','Apple','Smartphones','High quality product with manufacturer warranty.','iPhone 15',47092.00,233,'2023-10-28'),(79,_binary '','Sony','Television','High quality product with manufacturer warranty.','Bravia 55 TV',80970.00,124,'2025-12-13'),(80,_binary '','Dell','Laptops','High quality product with manufacturer warranty.','Alienware M16',13266.00,90,'2020-02-25'),(81,_binary '','Adidas','Shoes','High quality product with manufacturer warranty.','Predator League',9611.00,62,'2020-09-08'),(82,_binary '','LG','Television','High quality product with manufacturer warranty.','OLED C3 TV',151803.00,486,'2025-09-18'),(83,_binary '','LG','Home Appliances','High quality product with manufacturer warranty.','Front Load Washer',58081.00,380,'2023-09-15'),(84,_binary '','LG','Home Appliances','High quality product with manufacturer warranty.','Dual Inverter AC',41986.00,392,'2026-04-27'),(85,_binary '\0','Boat','Audio','High quality product with manufacturer warranty.','Stone 650',21536.00,146,'2021-05-15'),(86,_binary '\0','HP','Laptops','High quality product with manufacturer warranty.','Victus 15',133040.00,118,'2022-09-03'),(87,_binary '','Sony','Television','High quality product with manufacturer warranty.','Bravia 55 TV',70056.00,169,'2025-04-03'),(88,_binary '','Boat','Audio','High quality product with manufacturer warranty.','Stone 650',14623.00,463,'2020-04-27'),(89,_binary '','JBL','Audio','High quality product with manufacturer warranty.','Tune 770NC',1639.00,482,'2026-10-13'),(90,_binary '','Asus','Laptops','High quality product with manufacturer warranty.','ZenBook 14',147232.00,279,'2026-07-18'),(91,_binary '','JBL','Audio','High quality product with manufacturer warranty.','Flip 6',16997.00,199,'2021-05-14'),(92,_binary '','Asus','Laptops','High quality product with manufacturer warranty.','TUF A15',150989.00,65,'2025-03-27'),(93,_binary '','JBL','Audio','High quality product with manufacturer warranty.','Tune 770NC',1882.00,42,'2023-10-19'),(94,_binary '\0','Nike','Shoes','High quality product with manufacturer warranty.','Revolution 7',4223.00,194,'2026-08-06'),(95,_binary '','Asus','Laptops','High quality product with manufacturer warranty.','ROG Strix G16',164196.00,142,'2022-06-25'),(96,_binary '','Sony','Gaming','High quality product with manufacturer warranty.','PlayStation 5',47683.00,487,'2023-01-24'),(97,_binary '','Asus','Laptops','High quality product with manufacturer warranty.','ROG Strix G16',62987.00,486,'2026-11-02'),(98,_binary '\0','HP','Laptops','High quality product with manufacturer warranty.','Pavilion 15',50342.00,342,'2024-03-08'),(99,_binary '','Samsung','Wearables','High quality product with manufacturer warranty.','Galaxy Watch 6',22142.00,85,'2023-12-09'),(100,_binary '\0','JBL','Audio','High quality product with manufacturer warranty.','Tune 770NC',24538.00,13,'2020-03-10');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-15  8:16:16
