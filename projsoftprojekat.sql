/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.22 : Database - projsoftprojekat
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`projsoftprojekat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `projsoftprojekat`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(30) NOT NULL,
  `ime` varchar(25) NOT NULL,
  `prezime` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `admin` */

insert  into `admin`(`id`,`email`,`password`,`ime`,`prezime`) values 
(1,'zecicmirko@gmail.com','mirko','Mirko','Zecic'),
(2,'djordjNik@hotmail.com','nikola','Nikola','Djordjevic');

/*Table structure for table `dnevniraspored` */

DROP TABLE IF EXISTS `dnevniraspored`;

CREATE TABLE `dnevniraspored` (
  `dnevnirasporedid` bigint NOT NULL AUTO_INCREMENT,
  `datum` date NOT NULL,
  `manifestacijaid` bigint NOT NULL,
  PRIMARY KEY (`dnevnirasporedid`),
  UNIQUE KEY `datum` (`datum`),
  KEY `manifestacija` (`manifestacijaid`),
  CONSTRAINT `manifestacija` FOREIGN KEY (`manifestacijaid`) REFERENCES `manifestacija` (`manifestacijaid`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `dnevniraspored` */

insert  into `dnevniraspored`(`dnevnirasporedid`,`datum`,`manifestacijaid`) values 
(240,'2021-02-15',6),
(253,'2021-07-04',8);

/*Table structure for table `dr_i` */

DROP TABLE IF EXISTS `dr_i`;

CREATE TABLE `dr_i` (
  `dnevnirasporedid` bigint NOT NULL,
  `izlagacid` bigint NOT NULL,
  `halaid` bigint NOT NULL,
  `stand` int DEFAULT NULL,
  PRIMARY KEY (`dnevnirasporedid`,`izlagacid`,`halaid`),
  UNIQUE KEY `halaIStandIzlagac` (`dnevnirasporedid`,`halaid`,`stand`),
  KEY `hala` (`halaid`),
  KEY `izlagac` (`izlagacid`),
  CONSTRAINT `dnevniRaspored` FOREIGN KEY (`dnevnirasporedid`) REFERENCES `dnevniraspored` (`dnevnirasporedid`) ON UPDATE RESTRICT,
  CONSTRAINT `hala` FOREIGN KEY (`halaid`) REFERENCES `hala` (`halaid`) ON UPDATE RESTRICT,
  CONSTRAINT `izlagac` FOREIGN KEY (`izlagacid`) REFERENCES `izlagac` (`izlagacid`) ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `dr_i` */

insert  into `dr_i`(`dnevnirasporedid`,`izlagacid`,`halaid`,`stand`) values 
(240,7,3,4),
(240,5,3,23),
(253,3,3,6),
(253,26,7,8);

/*Table structure for table `hala` */

DROP TABLE IF EXISTS `hala`;

CREATE TABLE `hala` (
  `halaid` bigint NOT NULL,
  `brStandova` int DEFAULT NULL,
  PRIMARY KEY (`halaid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `hala` */

insert  into `hala`(`halaid`,`brStandova`) values 
(1,70),
(2,45),
(3,52),
(4,31),
(5,62),
(6,25),
(7,62),
(8,24),
(9,11),
(10,53),
(11,25);

/*Table structure for table `izlagac` */

DROP TABLE IF EXISTS `izlagac`;

CREATE TABLE `izlagac` (
  `izlagacid` bigint NOT NULL AUTO_INCREMENT,
  `ime` varchar(20) NOT NULL,
  `prezime` varchar(20) NOT NULL,
  `kontakttelefon` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `kompanijaid` bigint NOT NULL,
  PRIMARY KEY (`izlagacid`),
  UNIQUE KEY `emailUnique` (`email`),
  KEY `kompanijaid` (`kompanijaid`),
  CONSTRAINT `kompanija` FOREIGN KEY (`kompanijaid`) REFERENCES `kompanija` (`kompanijaid`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `izlagac` */

insert  into `izlagac`(`izlagacid`,`ime`,`prezime`,`kontakttelefon`,`email`,`kompanijaid`) values 
(1,'Matija','Nenadic','0642121339','matNen@gmail.com',6),
(2,'Tanja','Vidic','0661233310','tanjavidic@gmail.com',5),
(3,'Darko','Zecic','0641231331','darko373@hotmail.com',4),
(4,'Filip','Kovacevic','0692308961','kovacFilip@yahoo.com',3),
(5,'Andrijana','Matic','0659846745','andjaM@hotmail.com',2),
(6,'Stevan','Popovic','0623215876','stevaPopovic@gmail.com',4),
(7,'Tatjana','Starcevic','0634853195','starcevicTat85@gmail.com',6),
(26,'Nenad','Nenadic','0641232213','nesaNenadic@yahoo.com',5);

/*Table structure for table `kompanija` */

DROP TABLE IF EXISTS `kompanija`;

CREATE TABLE `kompanija` (
  `kompanijaid` bigint NOT NULL AUTO_INCREMENT,
  `naziv` varchar(30) NOT NULL,
  `adresa` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `kontakttelefon` varchar(20) NOT NULL,
  PRIMARY KEY (`kompanijaid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `kompanija` */

insert  into `kompanija`(`kompanijaid`,`naziv`,`adresa`,`kontakttelefon`) values 
(1,'ABC','Mateje Nenadovica 77','064 1243213'),
(2,'Agro Srbija','Nikole pasica 4','060 4219832'),
(3,'Elektra','Dimitrija Tucovica 85','069 9761239'),
(4,'Prvi partizan','Pavla Vujica 44','066 7123491'),
(5,'Valjaonica bakra','Djordja Markovica 8','069 9721311'),
(6,'Tuti Fruti','Ilije Matica 9','061 1852025');

/*Table structure for table `manifestacija` */

DROP TABLE IF EXISTS `manifestacija`;

CREATE TABLE `manifestacija` (
  `manifestacijaid` bigint NOT NULL AUTO_INCREMENT,
  `naziv` varchar(70) NOT NULL,
  `datumod` date NOT NULL,
  `datumdo` date NOT NULL,
  `tipid` bigint NOT NULL,
  PRIMARY KEY (`manifestacijaid`),
  UNIQUE KEY `nazivManifestacije` (`naziv`),
  KEY `tipid` (`tipid`),
  CONSTRAINT `tipmanifestacije` FOREIGN KEY (`tipid`) REFERENCES `tipmanifestacije` (`tipid`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `manifestacija` */

insert  into `manifestacija`(`manifestacijaid`,`naziv`,`datumod`,`datumdo`,`tipid`) values 
(4,'Sajam automobila','2021-05-13','2021-05-17',5),
(6,'Sajam knjiga','2021-02-10','2021-02-17',7),
(8,'Sajam gastronomije','2021-06-29','2021-07-04',3),
(12,'Sajam medicine','2021-08-10','2021-08-17',4),
(16,'Sajam nauke','2021-05-19','2021-05-22',6);

/*Table structure for table `tipmanifestacije` */

DROP TABLE IF EXISTS `tipmanifestacije`;

CREATE TABLE `tipmanifestacije` (
  `tipid` bigint NOT NULL AUTO_INCREMENT,
  `nazivtipa` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`tipid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tipmanifestacije` */

insert  into `tipmanifestacije`(`tipid`,`nazivtipa`) values 
(1,'Tehnika'),
(2,'Kultura'),
(3,'Ishrana'),
(4,'Medicina'),
(5,'Vozila'),
(6,'Nauka'),
(7,'Knjizevnost');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
