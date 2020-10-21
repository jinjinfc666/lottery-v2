-- MySQL dump 10.13  Distrib 5.6.47, for linux-glibc2.12 (x86_64)
--
-- Host: localhost    Database: lottery
-- ------------------------------------------------------
-- Server version	5.6.47

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
-- Current Database: `lottery`
--

/*!40000 DROP DATABASE IF EXISTS `lottery`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `lottery` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `lottery`;

--
-- Table structure for table `deposit_application`
--

DROP TABLE IF EXISTS `deposit_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deposit_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_num` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '�û�ID',
  `amount` float DEFAULT NULL COMMENT '��ֵ���',
  `pay_type` int(11) DEFAULT NULL COMMENT '��ֵ����',
  `pay_channel` int(11) DEFAULT NULL COMMENT '��ֵ����',
  `plat_account` varchar(50) DEFAULT NULL COMMENT 'ƽ̨�տ��˻�',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL COMMENT '��ע���߸���',
  `state` int(11) DEFAULT NULL COMMENT '��ֵ״̬:0,�ȴ���ֵ;1,��ֵ�ɹ�;2,��ֵʧ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deposit_application`
--

LOCK TABLES `deposit_application` WRITE;
/*!40000 ALTER TABLE `deposit_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `deposit_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_sequence`
--

DROP TABLE IF EXISTS `gen_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gen_sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq_name` varchar(30) DEFAULT NULL,
  `seq_val` bigint(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_sequence`
--

LOCK TABLES `gen_sequence` WRITE;
/*!40000 ALTER TABLE `gen_sequence` DISABLE KEYS */;
INSERT INTO `gen_sequence` VALUES (1,'seq_num_bjpk10',10000,'2020-06-25'),(2,'seq_num',10061,'2020-07-10'),(3,'seq_num_tc3d',147,'2020-07-15'),(4,'seq_num_fc3d',147,'2020-07-17');
/*!40000 ALTER TABLE `gen_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ip_black_list`
--

DROP TABLE IF EXISTS `ip_black_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_black_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(200) DEFAULT NULL,
  `ip_long` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '�û�״̬:0,����;1,����;2,����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_black_list`
--

LOCK TABLES `ip_black_list` WRITE;
/*!40000 ALTER TABLE `ip_black_list` DISABLE KEYS */;
INSERT INTO `ip_black_list` VALUES (1,'127.0.0.1','2130706433',0),(2,'127.0.0.1','2130706433',0),(3,'127.0.0.1','2130706433',0),(4,'127.0.0.1','2130706433',0),(5,'127.0.0.1','2130706433',0),(6,'127.0.0.1','2130706433',0),(7,'127.0.0.1','2130706433',0),(8,'127.0.0.1','2130706433',0),(9,'127.0.0.1','2130706433',0),(10,'127.0.0.1','2130706433',0),(11,'127.0.0.1','2130706433',0),(12,'127.0.0.1','2130706433',0);
/*!40000 ALTER TABLE `ip_black_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issue`
--

DROP TABLE IF EXISTS `issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `issue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lottery_type` varchar(30) DEFAULT NULL,
  `issue_num` varchar(50) DEFAULT NULL COMMENT '�ڴκ�',
  `ret_num` varchar(30) DEFAULT NULL COMMENT '��������',
  `state` int(11) DEFAULT NULL COMMENT '0,��ʼ״̬;1,Ͷע;2,����Ͷע;3,�ȴ�����;4,�ѿ���;5,���ɽ�',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104845 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
INSERT INTO `issue` VALUES (104784,'fc3d','2020148',NULL,1,'2020-07-16 22:00:00','2020-07-17 20:00:00'),(104785,'tc3','2020149',NULL,1,'2020-07-17 22:00:00','2020-07-18 20:00:00'),(104786,'fc3d','2020147',NULL,1,'2020-07-17 22:00:00','2020-07-18 20:00:00'),(104787,'fc3d','2020152',NULL,0,'2020-07-22 22:00:00','2020-07-23 20:00:00'),(104788,'tc3','2020154',NULL,0,'2020-07-22 22:00:00','2020-07-23 20:00:00'),(104789,'fc3d','2020156',NULL,0,'2020-07-26 22:00:00','2020-07-27 20:00:00'),(104790,'tc3','2020158',NULL,0,'2020-07-26 22:00:00','2020-07-27 20:00:00'),(104791,'fc3d','2020160',NULL,1,'2020-07-30 22:00:00','2020-07-31 20:00:00'),(104792,'tc3','2020162',NULL,1,'2020-07-30 22:00:00','2020-07-31 20:00:00'),(104793,'fc3d','2020161',NULL,1,'2020-07-31 22:00:00','2020-08-01 20:00:00'),(104794,'tc3','2020163',NULL,1,'2020-07-31 22:00:00','2020-08-01 20:00:00'),(104795,'fc3d','2020163',NULL,0,'2020-08-02 22:00:00','2020-08-03 20:00:00'),(104796,'tc3','2020165',NULL,0,'2020-08-02 22:00:00','2020-08-03 20:00:00'),(104797,'fc3d','2020176',NULL,1,'2020-08-15 22:00:00','2020-08-16 20:00:00'),(104798,'tc3','2020178',NULL,1,'2020-08-15 22:00:00','2020-08-16 20:00:00'),(104799,'fc3d','2020177',NULL,1,'2020-08-16 22:00:00','2020-08-17 20:00:00'),(104800,'tc3','2020179',NULL,1,'2020-08-16 22:00:00','2020-08-17 20:00:00'),(104801,'fc3d','2020185',NULL,1,'2020-08-24 22:00:00','2020-08-25 20:00:00'),(104802,'tc3','2020187',NULL,3,'2020-08-24 22:00:00','2020-08-25 20:00:00'),(104803,'fc3d','2020187',NULL,0,'2020-08-26 22:00:00','2020-08-27 20:00:00'),(104804,'tc3','2020189',NULL,0,'2020-08-26 22:00:00','2020-08-27 20:00:00'),(104805,'fc3d','2020198',NULL,0,'2020-09-06 22:00:00','2020-09-07 20:00:00'),(104806,'tc3','2020200',NULL,0,'2020-09-06 22:00:00','2020-09-07 20:00:00'),(104807,'fc3d','2020205',NULL,1,'2020-09-13 22:00:00','2020-09-14 20:00:00'),(104808,'tc3','2020207',NULL,3,'2020-09-13 22:00:00','2020-09-14 20:00:00'),(104809,'fc3d','2020207',NULL,1,'2020-09-15 22:00:00','2020-09-16 20:00:00'),(104810,'tc3','2020209',NULL,3,'2020-09-15 22:00:00','2020-09-16 20:00:00'),(104811,'fc3d','2020209',NULL,1,'2020-09-17 22:00:00','2020-09-18 20:00:00'),(104812,'tc3','2020211',NULL,3,'2020-09-17 22:00:00','2020-09-18 20:00:00'),(104813,'fc3d','2020211',NULL,1,'2020-09-19 22:00:00','2020-09-20 20:00:00'),(104814,'tc3','2020213',NULL,3,'2020-09-19 10:32:35','2020-09-20 20:00:00'),(104815,'tc3','2020213',NULL,0,'2020-09-19 22:00:00','2020-09-20 20:00:00'),(104816,'fc3d','2020212',NULL,1,'2020-09-20 22:00:00','2020-09-21 20:00:00'),(104817,'tc3','2020214',NULL,2,'2020-09-20 21:21:45','2020-09-21 20:00:00'),(104818,'tc3','2020214',NULL,0,'2020-09-20 22:00:00','2020-09-21 20:00:00'),(104819,'fc3d','2020213',NULL,1,'2020-09-21 22:00:00','2020-09-22 20:00:00'),(104820,'tc3','2020215',NULL,2,'2020-09-21 22:00:00','2020-09-22 20:00:00'),(104821,'fc3d','2020214',NULL,1,'2020-09-22 22:00:00','2020-09-23 20:00:00'),(104822,'tc3','2020216',NULL,3,'2020-09-22 22:00:00','2020-09-23 20:00:00'),(104823,'fc3d','2020216',NULL,1,'2020-09-24 22:00:00','2020-09-25 20:00:00'),(104824,'tc3','2020218',NULL,3,'2020-09-25 22:00:00','2020-09-26 20:00:00'),(104825,'fc3d','2020218',NULL,0,'2020-09-26 22:00:00','2020-09-27 20:00:00'),(104828,'tc3','2020220',NULL,1,'2020-09-26 09:09:55','2020-09-27 20:00:00'),(104829,'tc3','2020221',NULL,3,'2020-09-27 22:00:00','2020-09-28 20:00:00'),(104830,'tc3','2020223',NULL,3,'2020-09-29 22:00:00','2020-09-30 20:00:00'),(104831,'tc3','2020226',NULL,3,'2020-10-02 22:00:00','2020-10-03 20:00:00'),(104832,'tc3','2020228',NULL,2,'2020-10-04 10:58:08','2020-10-05 20:00:00'),(104833,'tc3','2020228',NULL,0,'2020-10-04 22:00:00','2020-10-05 20:00:00'),(104834,'tc3','2020229',NULL,3,'2020-10-05 22:00:00','2020-10-06 20:00:00'),(104835,'tc3','2020231',NULL,3,'2020-10-07 09:37:22','2020-10-08 20:00:00'),(104836,'tc3','2020231',NULL,0,'2020-10-07 22:00:00','2020-10-08 20:00:00'),(104837,'tc3','2020234',NULL,3,'2020-10-10 00:54:49','2020-10-11 20:00:00'),(104838,'tc3','2020234',NULL,0,'2020-10-10 22:00:00','2020-10-11 20:00:00'),(104839,'tc3','2020237',NULL,3,'2020-10-13 22:00:00','2020-10-14 20:00:00'),(104840,'tc3','2020238',NULL,3,'2020-10-14 22:00:00','2020-10-15 20:00:00'),(104841,'tc3','2020240',NULL,3,'2020-10-16 08:58:05','2020-10-17 20:00:00'),(104842,'tc3','2020240',NULL,0,'2020-10-16 22:00:00','2020-10-17 20:00:00'),(104843,'tc3','2020241',NULL,3,'2020-10-17 22:00:00','2020-10-18 20:00:00'),(104844,'tc3','2020244',NULL,1,'2020-10-20 22:00:00','2020-10-21 20:00:00');
/*!40000 ALTER TABLE `issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lottery_pl_report`
--

DROP TABLE IF EXISTS `lottery_pl_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lottery_pl_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` date NOT NULL,
  `code_name` varchar(50) DEFAULT NULL COMMENT '��������',
  `user_id` int(11) DEFAULT NULL COMMENT '�û�����:0,ƽ̨�ͻ�;1,����;3,�ܴ�',
  `user_name` varchar(50) DEFAULT NULL COMMENT '�û���',
  `consumption` decimal(19,2) DEFAULT NULL COMMENT '����',
  `cancel_amount` decimal(19,2) DEFAULT NULL COMMENT '����',
  `return_prize` decimal(19,2) DEFAULT NULL COMMENT '����',
  `rebate` decimal(19,2) DEFAULT NULL COMMENT '����',
  `profit` decimal(19,2) DEFAULT NULL COMMENT 'ӯ��',
  `user_type` int(11) DEFAULT NULL COMMENT '�û�����:0,ƽ̨�ͻ�;1,����;3,�ܴ�',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lottery_pl_report`
--

LOCK TABLES `lottery_pl_report` WRITE;
/*!40000 ALTER TABLE `lottery_pl_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `lottery_pl_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lottery_platform_profit`
--

DROP TABLE IF EXISTS `lottery_platform_profit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lottery_platform_profit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` date DEFAULT NULL,
  `code_name` varchar(50) DEFAULT NULL COMMENT '��������',
  `issue_num` varchar(50) DEFAULT NULL COMMENT '�ڴκ�',
  `play_typeid` int(11) DEFAULT NULL COMMENT '�淨id  ������ѯ',
  `play_type` varchar(50) DEFAULT NULL COMMENT '�淨',
  `betting` decimal(19,2) DEFAULT NULL COMMENT 'Ͷע',
  `cancel_amount` decimal(19,2) DEFAULT NULL COMMENT '����',
  `winning` decimal(19,2) DEFAULT NULL COMMENT '�н�',
  `rebate` decimal(19,2) DEFAULT NULL COMMENT '����',
  `platform_profit` decimal(19,2) DEFAULT NULL COMMENT 'ƽ̨ӯ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lottery_platform_profit`
--

LOCK TABLES `lottery_platform_profit` WRITE;
/*!40000 ALTER TABLE `lottery_platform_profit` DISABLE KEYS */;
/*!40000 ALTER TABLE `lottery_platform_profit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_pl_report`
--

DROP TABLE IF EXISTS `member_pl_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member_pl_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` date NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '��ֵ��Ա����',
  `user_name` varchar(50) DEFAULT NULL COMMENT '�û���',
  `deposit` decimal(19,2) DEFAULT NULL COMMENT '�û����',
  `withdrawal` decimal(19,2) DEFAULT NULL COMMENT '�û�ȡ��',
  `transfer` decimal(19,2) DEFAULT NULL COMMENT '�û�ȡ��',
  `transfer_out` decimal(19,2) DEFAULT NULL COMMENT '�û�ȡ��',
  `deduction` decimal(19,2) DEFAULT NULL COMMENT '�۳�',
  `consumption` decimal(19,2) DEFAULT NULL COMMENT '����',
  `cancel_amount` decimal(19,2) DEFAULT NULL COMMENT '����',
  `return_prize` decimal(19,2) DEFAULT NULL COMMENT '����',
  `rebate` decimal(19,2) DEFAULT NULL COMMENT '����',
  `profit` decimal(19,2) DEFAULT NULL COMMENT 'ӯ��',
  `user_type` int(11) DEFAULT NULL COMMENT '�û�����:0,ƽ̨�ͻ�;1,����;3,�ܴ�',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_pl_report`
--

LOCK TABLES `member_pl_report` WRITE;
/*!40000 ALTER TABLE `member_pl_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_pl_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `old_user_card`
--

DROP TABLE IF EXISTS `old_user_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `old_user_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL COMMENT '����',
  `bank_acc` varchar(255) DEFAULT NULL COMMENT 'ƽ̨��������ߴ�����¼��������ҵĵ�',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `old_user_card`
--

LOCK TABLES `old_user_card` WRITE;
/*!40000 ALTER TABLE `old_user_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `old_user_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_info`
--

DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_num` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `issue_id` int(11) DEFAULT NULL,
  `wallet_id` int(11) DEFAULT NULL,
  `play_type` int(11) DEFAULT NULL COMMENT '�淨',
  `bet_num` varchar(300) DEFAULT NULL COMMENT 'Ͷע����',
  `bet_total` int(11) DEFAULT NULL COMMENT 'ע��',
  `bet_amount` float DEFAULT NULL COMMENT 'Ͷע���',
  `win_bet_total` float DEFAULT NULL COMMENT 'Ͷע���',
  `win_amount` float DEFAULT NULL COMMENT 'Ͷע���',
  `times` int(11) DEFAULT NULL COMMENT '����',
  `pattern` decimal(9,3) DEFAULT NULL COMMENT 'ÿע����Ǯ',
  `prize_rate` decimal(9,3) DEFAULT NULL COMMENT '����',
  `state` int(11) DEFAULT NULL COMMENT '0,�ȴ��ɽ�;1,Ӯ;2,��;3,�û�ȡ������;4,ϵͳȡ������;5,׷���н�ȡ��',
  `delay_payout_flag` int(11) DEFAULT NULL COMMENT '�ӳ��ɽ���ʶ:0,���ӳ�;1,�ӳ��ɽ�',
  `is_zh` int(11) DEFAULT NULL COMMENT '�Ƿ�׷��:0,��׷��;1,׷��',
  `is_zh_block` int(11) DEFAULT NULL COMMENT '׷������£��н����Ƿ����',
  `zh_trasaction_num` varchar(300) DEFAULT NULL,
  `terminal_type` int(11) DEFAULT NULL COMMENT '0,pc��;1,�ֻ���',
  `create_time` datetime DEFAULT NULL,
  `play_type_desc` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` VALUES (1,'202009270100093495',27,104828,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-09-27 17:59:00',NULL),(2,'202009270100103050',27,104828,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-09-27 18:05:10',NULL),(3,'202009270100117835',27,104828,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-09-27 18:14:10',NULL),(4,'202009290100121683',27,104829,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-09-29 10:03:37',NULL),(5,'202010030100136157',27,104831,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-10-03 01:18:37',NULL),(6,'202010030100147210',27,104831,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-10-03 01:23:45',NULL),(7,'202010040100158594',27,104832,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-10-04 11:05:36',NULL),(8,'202010040100160099',27,104832,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-10-04 11:52:19',NULL),(9,'202010060100171936',27,104834,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-10-06 00:58:56',NULL),(10,'202010060100180440',27,104834,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-10-06 01:08:27',NULL),(11,'202010060100198375',27,104834,51,227,'0',NULL,100,NULL,NULL,100,1.000,1.900,0,0,0,1,NULL,0,'2020-10-06 10:34:19',NULL),(12,'202010110100204901',27,104837,51,216,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 14:37:27',NULL),(13,'202010110100210173',27,104837,51,216,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 15:32:15',NULL),(14,'202010110100224857',27,104837,51,213,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 16:28:10',NULL),(15,'202010110100238558',27,104837,51,213,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 16:29:08',NULL),(16,'202010110100248257',27,104837,51,219,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 16:29:59',NULL),(17,'202010110100254510',27,104837,51,219,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 16:30:00',NULL),(18,'202010110100269631',27,104837,51,222,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 17:59:40',NULL),(19,'202010110100278421',27,104837,51,222,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 17:59:41',NULL),(20,'202010110100289596',27,104837,51,223,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 17:59:41',NULL),(21,'202010110100296119',27,104837,51,223,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-11 17:59:41',NULL),(22,'202010140100306649',27,104839,51,238,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 09:46:30',NULL),(23,'202010140100319728',27,104839,51,238,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 09:46:43',NULL),(24,'202010140100329199',27,104839,51,239,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 09:46:43',NULL),(25,'202010140100336052',27,104839,51,239,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 09:46:44',NULL),(26,'202010140100340850',27,104839,51,240,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 09:46:44',NULL),(27,'202010140100353432',27,104839,51,240,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 09:46:44',NULL),(28,'202010140100362387',27,104839,51,236,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:15:51',NULL),(29,'202010140100370616',27,104839,51,236,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:15:51',NULL),(30,'202010140100383459',27,104839,51,234,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:15:51',NULL),(31,'202010140100398432',27,104839,51,234,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:15:51',NULL),(32,'202010140100405407',27,104839,51,235,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:15:51',NULL),(33,'202010140100419160',27,104839,51,235,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:15:52',NULL),(34,'202010140100424217',27,104839,51,232,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:31:51',NULL),(35,'202010140100436031',27,104839,51,232,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:31:51',NULL),(36,'202010140100441751',27,104839,51,231,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:31:51',NULL),(37,'202010140100458374',27,104839,51,231,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:31:51',NULL),(38,'202010140100465660',27,104839,51,230,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:31:51',NULL),(39,'202010140100471465',27,104839,51,230,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:31:51',NULL),(40,'202010140100487880',27,104839,51,238,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:40:35',NULL),(41,'202010140100491547',27,104839,51,238,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:40:35',NULL),(42,'202010140100502659',27,104839,51,239,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:40:36',NULL),(43,'202010140100512138',27,104839,51,239,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:40:36',NULL),(44,'202010140100522035',27,104839,51,240,'00',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:40:36',NULL),(45,'202010140100532688',27,104839,51,240,'01',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-14 10:40:36',NULL),(46,'202010180100545429',27,104843,51,227,'0',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-18 18:45:28',NULL),(47,'202010180100553446',27,104843,51,233,'0',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-18 18:45:29',NULL),(48,'202010180100562567',27,104843,51,237,'0',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-18 18:45:29',NULL),(49,'202010180100570844',27,104843,51,241,'0',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-18 18:45:29',NULL),(50,'202010180100583041',27,104843,51,227,'0',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-18 18:49:58',NULL),(51,'202010180100593628',27,104843,51,233,'0',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-18 18:49:58',NULL),(52,'202010180100606633',27,104843,51,237,'0',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-18 18:49:58',NULL),(53,'202010180100617471',27,104843,51,241,'0',NULL,1,NULL,NULL,1,1.000,1.900,0,0,0,1,NULL,0,'2020-10-18 18:49:58',NULL);
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_info_ext`
--

DROP TABLE IF EXISTS `order_info_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_info_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `ext_field_name` varchar(255) DEFAULT NULL,
  `ext_field_val` varchar(255) DEFAULT NULL COMMENT '�Ƿ��������:0,�Ǵ�������;1,��������',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info_ext`
--

LOCK TABLES `order_info_ext` WRITE;
/*!40000 ALTER TABLE `order_info_ext` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_info_ext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_source`
--

DROP TABLE IF EXISTS `order_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` date NOT NULL,
  `code_name` varchar(50) DEFAULT NULL COMMENT '��������',
  `mobile_amount` decimal(19,2) DEFAULT NULL COMMENT '�ֻ���Ͷע��',
  `pc_amount` decimal(19,2) DEFAULT NULL COMMENT 'PC��Ͷע��',
  `sum_amount` decimal(19,2) DEFAULT NULL COMMENT '��Ͷע��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_source`
--

LOCK TABLES `order_source` WRITE;
/*!40000 ALTER TABLE `order_source` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_channel`
--

DROP TABLE IF EXISTS `pay_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(50) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL COMMENT '����',
  `type_class` varchar(50) DEFAULT NULL COMMENT '��ֵ��ʽ����',
  `pay_code` varchar(50) DEFAULT NULL COMMENT '第三方支付代码',
  `pay_type` int(11) DEFAULT NULL COMMENT '��ֵ��ʽ',
  `max_amount` float DEFAULT NULL COMMENT '����ֵ���',
  `enable_max_amount` int(11) DEFAULT NULL COMMENT '�Ƿ񼤻���������:0,������;1,����',
  `seq` int(11) DEFAULT NULL COMMENT '�������',
  `state` int(11) DEFAULT NULL COMMENT '״̬:0,��Ч;1,��Ч',
  `qr_url` varchar(200) DEFAULT NULL COMMENT 'ƽ̨��ά��url',
  `bank_acc` varchar(50) DEFAULT NULL COMMENT 'ƽ̨�����˻�',
  `bank_name` varchar(50) DEFAULT NULL COMMENT 'ƽ̨���п�����',
  `remark` varchar(200) DEFAULT NULL,
  `show_type` int(11) DEFAULT NULL COMMENT '调用支付成功，需展示的类型(PayChannelShowType)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_channel`
--

LOCK TABLES `pay_channel` WRITE;
/*!40000 ALTER TABLE `pay_channel` DISABLE KEYS */;
/*!40000 ALTER TABLE `pay_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_type`
--

DROP TABLE IF EXISTS `pay_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL COMMENT 'ƽ̨֧������',
  `type_class` varchar(30) DEFAULT NULL COMMENT '֧����ʽ����',
  `seq` int(11) DEFAULT NULL COMMENT '�������',
  `state` int(11) DEFAULT NULL COMMENT '֧����ʽ�Ƿ���Ч:0,��Ч;1,��Ч',
  `is_tp` int(11) DEFAULT NULL COMMENT '�Ƿ��3��ƽ̨:0,�ǵ�3��ƽ̨;1,��3��ƽ̨',
  `plat_id` varchar(50) DEFAULT NULL COMMENT '֧��ƽ̨,ϵͳ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_type`
--

LOCK TABLES `pay_type` WRITE;
/*!40000 ALTER TABLE `pay_type` DISABLE KEYS */;
INSERT INTO `pay_type` VALUES (1,'支付宝支付','平台自有支付','1',1,1,0,'self_pay'),(2,'微信支付','平台自有支付','1',2,1,0,'self_pay');
/*!40000 ALTER TABLE `pay_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_type_num`
--

DROP TABLE IF EXISTS `pay_type_num`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_type_num` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `play_type_id` varchar(50) DEFAULT NULL,
  `bet_num` varchar(50) DEFAULT NULL COMMENT 'ƽ̨֧������',
  `a_odds` varchar(30) DEFAULT NULL COMMENT '֧����ʽ����',
  `b_odds` int(11) DEFAULT NULL COMMENT '�������',
  `c_odds` int(11) DEFAULT NULL COMMENT '֧����ʽ�Ƿ���Ч:0,��Ч;1,��Ч',
  `d_odds` int(11) DEFAULT NULL COMMENT '�Ƿ��3��ƽ̨:0,�ǵ�3��ƽ̨;1,��3��ƽ̨',
  `current_odds` varchar(50) DEFAULT NULL COMMENT '֧��ƽ̨,ϵͳ����',
  `create_time` varchar(50) DEFAULT NULL COMMENT '֧��ƽ̨,ϵͳ����',
  `update_time` varchar(50) DEFAULT NULL COMMENT '֧��ƽ̨,ϵͳ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_type_num`
--

LOCK TABLES `pay_type_num` WRITE;
/*!40000 ALTER TABLE `pay_type_num` DISABLE KEYS */;
/*!40000 ALTER TABLE `pay_type_num` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `platform_fund_summary`
--

DROP TABLE IF EXISTS `platform_fund_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `platform_fund_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` date DEFAULT NULL,
  `freezing_funds` decimal(19,2) DEFAULT NULL COMMENT '�����ʽ�',
  `freezing_red_funds` decimal(19,2) DEFAULT NULL COMMENT '�����ʽ�',
  `all_balances` decimal(19,2) DEFAULT NULL COMMENT '�������',
  `all_red_balances` decimal(19,2) DEFAULT NULL COMMENT '�������',
  `recharge` decimal(19,2) DEFAULT NULL COMMENT '��ֵ',
  `withdraw` decimal(19,2) DEFAULT NULL COMMENT '����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `platform_fund_summary`
--

LOCK TABLES `platform_fund_summary` WRITE;
/*!40000 ALTER TABLE `platform_fund_summary` DISABLE KEYS */;
/*!40000 ALTER TABLE `platform_fund_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `play_type`
--

DROP TABLE IF EXISTS `play_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `play_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lottery_type` varchar(30) DEFAULT NULL,
  `classification` varchar(100) DEFAULT NULL COMMENT '���ࣺ����3����|��������������2����|����������1|����',
  `pt_name` varchar(50) DEFAULT NULL COMMENT '����',
  `pt_desc` varchar(250) DEFAULT NULL COMMENT '����',
  `state` int(11) DEFAULT NULL COMMENT '0:��Ч;1,��Ч',
  `mul_sin_flag` int(11) DEFAULT NULL COMMENT '0:��ʽ;1:����',
  `is_hidden` int(11) DEFAULT NULL COMMENT '�����Ƿ���ʾ��0��ʾ 1����',
  `seq` int(11) DEFAULT NULL COMMENT '˳��',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `play_type`
--

LOCK TABLES `play_type` WRITE;
/*!40000 ALTER TABLE `play_type` DISABLE KEYS */;
INSERT INTO `play_type` VALUES (1,'gd11x5','sm|三码/qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 09:16:39'),(2,'gd11x5','sm|三码/qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(3,'gd11x5','sm|三码/qszux|前三组选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(4,'gd11x5','sm|三码/qszux|前三组选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(5,'gd11x5','em|二码/qezx|前二直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(6,'gd11x5','em|二码/qezx|前二直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(7,'gd11x5','em|二码/qezux|前二组选','fs','复式',1,1,0,7,'2020-06-29 10:50:10'),(8,'gd11x5','em|二码/qezux|前二组选','ds','单式',1,0,0,8,'2020-06-29 10:50:10'),(9,'gd11x5','bdw|不定位','fs','复式',1,1,0,9,'2020-06-29 10:50:10'),(10,'gd11x5','dwd|定位胆','fs','复式',1,1,0,10,'2020-06-29 10:50:10'),(11,'gd11x5','qwx|趣味型/qwdds|趣味定单双','fs','复式',1,1,0,11,'2020-06-29 10:50:10'),(12,'gd11x5','qwx|趣味型/qwczw|趣味猜中位','fs','复式',1,1,0,12,'2020-06-29 10:50:10'),(13,'gd11x5','rx|任选/rxyzy|任选一中一','fs','复式',1,1,0,13,'2020-06-29 10:50:10'),(14,'gd11x5','rx|任选/rxeze|任选二中二','fs','复式',1,1,0,14,'2020-06-29 10:50:10'),(15,'gd11x5','rx|任选/rxszs|任选三中三','fs','复式',1,1,0,15,'2020-06-29 10:50:10'),(16,'gd11x5','rx|任选/rxszs|任选四中四','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(17,'gd11x5','rx|任选/rxwzw|任选五中五','fs','复式',1,1,0,17,'2020-06-29 10:50:10'),(18,'gd11x5','rx|任选/rxlzw|任选六中五','fs','复式',1,1,0,18,'2020-06-29 10:50:10'),(19,'gd11x5','rx|任选/rxqzw|任选七中五','fs','复式',1,1,0,19,'2020-06-29 10:50:10'),(20,'gd11x5','rx|任选/rxbzw|任选八中五','fs','复式',1,1,0,20,'2020-06-29 10:50:10'),(21,'gd11x5','rx|任选/rxyzy|任选一中一','ds','单式',1,0,0,21,'2020-06-29 10:50:10'),(22,'gd11x5','rx|任选/rxeze|任选二中二','ds','单式',1,0,0,22,'2020-06-29 10:50:10'),(23,'gd11x5','rx|任选/rxszs|任选三中三','ds','单式',1,0,0,23,'2020-06-29 10:50:10'),(24,'gd11x5','rx|任选/rxszs|任选四中四','ds','单式',1,0,0,24,'2020-06-29 10:50:10'),(25,'gd11x5','rx|任选/rxwzw|任选五中五','ds','单式',1,0,0,25,'2020-06-29 10:50:10'),(26,'gd11x5','rx|任选/rxlzw|任选六中五','ds','单式',1,0,0,26,'2020-06-29 10:50:10'),(27,'gd11x5','rx|任选/rxqzw|任选七中五','ds','单式',1,0,0,27,'2020-06-29 10:50:10'),(28,'gd11x5','rx|任选/rxbzw|任选八中五','ds','单式',1,0,0,28,'2020-06-29 10:50:10'),(29,'sfc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:50:10'),(30,'sfc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(31,'sfc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(32,'sfc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(33,'sfc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(34,'sfc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(35,'sfc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:50:10'),(36,'sfc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:50:10'),(37,'sfc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:50:10'),(38,'sfc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:50:10'),(39,'sfc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:50:10'),(40,'sfc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:50:10'),(41,'sfc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:50:10'),(42,'sfc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:50:10'),(43,'sfc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:50:10'),(44,'sfc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(45,'sfc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:50:10'),(46,'sfc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:50:10'),(47,'sfc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:50:10'),(48,'sfc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:50:10'),(49,'sfc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:50:10'),(50,'sfc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:50:10'),(51,'sfc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:50:10'),(52,'sfc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:50:10'),(53,'sfc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:50:10'),(54,'5fc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:50:10'),(55,'5fc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(56,'5fc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(57,'5fc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(58,'5fc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(59,'5fc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(60,'5fc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:50:10'),(61,'5fc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:50:10'),(62,'5fc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:50:10'),(63,'5fc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:50:10'),(64,'5fc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:50:10'),(65,'5fc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:50:10'),(66,'5fc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:50:10'),(67,'5fc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:50:10'),(68,'5fc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:50:10'),(69,'5fc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(70,'5fc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:50:10'),(71,'5fc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:50:10'),(72,'5fc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:50:10'),(73,'5fc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:50:10'),(74,'5fc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:50:10'),(75,'5fc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:50:10'),(76,'5fc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:50:10'),(77,'5fc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:50:10'),(78,'5fc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:50:10'),(79,'ffc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:50:10'),(80,'ffc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(81,'ffc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(82,'ffc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(83,'ffc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(84,'ffc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(85,'ffc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:50:10'),(86,'ffc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:50:10'),(87,'ffc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:50:10'),(88,'ffc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:50:10'),(89,'ffc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:50:10'),(90,'ffc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:50:10'),(91,'ffc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:50:10'),(92,'ffc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:50:10'),(93,'ffc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:50:10'),(94,'ffc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(95,'ffc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:50:10'),(96,'ffc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:50:10'),(97,'ffc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:50:10'),(98,'ffc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:50:10'),(99,'ffc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:50:10'),(100,'ffc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:50:10'),(101,'ffc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:50:10'),(102,'ffc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:50:10'),(103,'ffc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:50:10'),(104,'mmc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:50:10'),(105,'mmc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(106,'mmc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(107,'mmc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(108,'mmc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(109,'mmc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(110,'mmc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:50:10'),(111,'mmc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:50:10'),(112,'mmc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:50:10'),(113,'mmc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:50:10'),(114,'mmc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:50:10'),(115,'mmc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:50:10'),(116,'mmc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:50:10'),(117,'mmc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:50:10'),(118,'mmc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:50:10'),(119,'mmc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(120,'mmc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:50:11'),(121,'mmc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:50:11'),(122,'mmc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:50:11'),(123,'mmc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:50:11'),(124,'mmc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:50:11'),(125,'mmc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:50:11'),(126,'mmc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:50:11'),(127,'mmc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:50:11'),(128,'mmc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:50:11'),(129,'bjpk10','qyfs|前一复式','fs','复式',1,1,0,1,'2020-06-29 10:50:11'),(130,'bjpk10','qefs|前二复式','fs','复式',1,1,0,2,'2020-06-29 10:50:11'),(131,'bjpk10','qsfs|前三复式','fs','复式',1,1,0,3,'2020-06-29 10:50:11'),(132,'bjpk10','dwd|定位胆/qs|前十','fs','复式',1,1,0,4,'2020-06-29 10:50:11'),(133,'bjpk10','lh|龙虎/1v10|1v10','fs','复式',1,1,0,5,'2020-06-29 10:50:11'),(134,'bjpk10','lh|龙虎/2v9|2v9','fs','复式',1,1,0,6,'2020-06-29 10:50:11'),(135,'bjpk10','lh|龙虎/3v8|3v8','fs','复式',1,1,0,7,'2020-06-29 10:50:11'),(136,'bjpk10','lh|龙虎/4v7|4v7','fs','复式',1,1,0,8,'2020-06-29 10:50:11'),(137,'bjpk10','lh|龙虎/5v6|5v6','fs','复式',1,1,0,9,'2020-06-29 10:50:11'),(138,'bjpk10','dx|大小/dym|第一名','fs','复式',1,1,0,10,'2020-06-29 10:50:11'),(139,'bjpk10','dx|大小/dem|第二名','fs','复式',1,1,0,11,'2020-06-29 10:50:11'),(140,'bjpk10','dx|大小/dsm|第三名','fs','复式',1,1,0,12,'2020-06-29 10:50:11'),(141,'bjpk10','dx|大小/dsim|第四名','fs','复式',1,1,0,13,'2020-06-29 10:50:11'),(142,'bjpk10','dx|大小/dwm|第五名','fs','复式',1,1,0,14,'2020-06-29 10:50:11'),(143,'bjpk10','dx|大小/dlm|第六名','fs','复式',1,1,0,15,'2020-06-29 10:50:11'),(144,'bjpk10','dx|大小/dqm|第七名','fs','复式',1,1,0,16,'2020-06-29 10:50:11'),(145,'bjpk10','dx|大小/dbm|第八名','fs','复式',1,1,0,17,'2020-06-29 10:50:11'),(146,'bjpk10','dx|大小/djm|第九名','fs','复式',1,1,0,18,'2020-06-29 10:50:11'),(147,'bjpk10','dx|大小/dshim|第十名','fs','复式',1,1,0,19,'2020-06-29 10:50:11'),(148,'bjpk10','ds|单双/dym|第一名','fs','复式',1,1,0,20,'2020-06-29 10:50:11'),(149,'bjpk10','ds|单双/dem|第二名','fs','复式',1,1,0,21,'2020-06-29 10:50:11'),(150,'bjpk10','ds|单双/dsm|第三名','fs','复式',1,1,0,22,'2020-06-29 10:50:11'),(151,'bjpk10','ds|单双/dsim|第四名','fs','复式',1,1,0,23,'2020-06-29 10:50:11'),(152,'bjpk10','ds|单双/dwm|第五名','fs','复式',1,1,0,24,'2020-06-29 10:50:11'),(153,'bjpk10','ds|单双/dlm|第六名','fs','复式',1,1,0,25,'2020-06-29 10:50:11'),(154,'bjpk10','ds|单双/dqm|第七名','fs','复式',1,1,0,26,'2020-06-29 10:50:11'),(155,'bjpk10','ds|单双/dbm|第八名','fs','复式',1,1,0,27,'2020-06-29 10:50:11'),(156,'bjpk10','ds|单双/djm|第九名','fs','复式',1,1,0,28,'2020-06-29 10:50:11'),(157,'bjpk10','ds|单双/dshim|第十名','fs','复式',1,1,0,29,'2020-06-29 10:50:11'),(158,'xyft','qyfs|前一复式','fs','复式',1,1,0,1,'2020-06-29 10:50:11'),(159,'xyft','qefs|前二复式','fs','复式',1,1,0,2,'2020-06-29 10:50:11'),(160,'xyft','qsfs|前三复式','fs','复式',1,1,0,3,'2020-06-29 10:50:11'),(161,'xyft','dwd|定位胆/qs|前十','fs','复式',1,1,0,4,'2020-06-29 10:50:11'),(162,'xyft','lh|龙虎/1v10|1v10','fs','复式',1,1,0,5,'2020-06-29 10:50:11'),(163,'xyft','lh|龙虎/2v9|2v9','fs','复式',1,1,0,6,'2020-06-29 10:50:11'),(164,'xyft','lh|龙虎/3v8|3v8','fs','复式',1,1,0,7,'2020-06-29 10:50:11'),(165,'xyft','lh|龙虎/4v7|4v7','fs','复式',1,1,0,8,'2020-06-29 10:50:11'),(166,'xyft','lh|龙虎/5v6|5v6','fs','复式',1,1,0,9,'2020-06-29 10:50:11'),(167,'xyft','dx|大小/dym|第一名','fs','复式',1,1,0,10,'2020-06-29 10:50:11'),(168,'xyft','dx|大小/dem|第二名','fs','复式',1,1,0,11,'2020-06-29 10:50:11'),(169,'xyft','dx|大小/dsm|第三名','fs','复式',1,1,0,12,'2020-06-29 10:50:11'),(170,'xyft','dx|大小/dsim|第四名','fs','复式',1,1,0,13,'2020-06-29 10:50:11'),(171,'xyft','dx|大小/dwm|第五名','fs','复式',1,1,0,14,'2020-06-29 10:50:11'),(172,'xyft','dx|大小/dlm|第六名','fs','复式',1,1,0,15,'2020-06-29 10:50:11'),(173,'xyft','dx|大小/dqm|第七名','fs','复式',1,1,0,16,'2020-06-29 10:50:11'),(174,'xyft','dx|大小/dbm|第八名','fs','复式',1,1,0,17,'2020-06-29 10:50:11'),(175,'xyft','dx|大小/djm|第九名','fs','复式',1,1,0,18,'2020-06-29 10:50:11'),(176,'xyft','dx|大小/dshim|第十名','fs','复式',1,1,0,19,'2020-06-29 10:50:11'),(177,'xyft','ds|单双/dym|第一名','fs','复式',1,1,0,20,'2020-06-29 10:50:11'),(178,'xyft','ds|单双/dem|第二名','fs','复式',1,1,0,21,'2020-06-29 10:50:11'),(179,'xyft','ds|单双/dsm|第三名','fs','复式',1,1,0,22,'2020-06-29 10:50:11'),(180,'xyft','ds|单双/dsim|第四名','fs','复式',1,1,0,23,'2020-06-29 10:50:11'),(181,'xyft','ds|单双/dwm|第五名','fs','复式',1,1,0,24,'2020-06-29 10:50:11'),(182,'xyft','ds|单双/dlm|第六名','fs','复式',1,1,0,25,'2020-06-29 10:50:11'),(183,'xyft','ds|单双/dqm|第七名','fs','复式',1,1,0,26,'2020-06-29 10:50:11'),(184,'xyft','ds|单双/dbm|第八名','fs','复式',1,1,0,27,'2020-06-29 10:50:11'),(185,'xyft','ds|单双/djm|第九名','fs','复式',1,1,0,28,'2020-06-29 10:50:11'),(186,'xyft','ds|单双/dshim|第十名','fs','复式',1,1,0,29,'2020-06-29 10:50:11'),(187,'cqssc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:55:17'),(188,'cqssc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:55:17'),(189,'cqssc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:55:17'),(190,'cqssc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:55:17'),(191,'cqssc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:55:17'),(192,'cqssc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:55:17'),(193,'cqssc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:55:17'),(194,'cqssc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:55:17'),(195,'cqssc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:55:17'),(196,'cqssc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:55:17'),(197,'cqssc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:55:17'),(198,'cqssc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:55:17'),(199,'cqssc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:55:17'),(200,'cqssc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:55:17'),(201,'cqssc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:55:17'),(202,'cqssc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:55:17'),(203,'cqssc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:55:17'),(204,'cqssc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:55:17'),(205,'cqssc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:55:17'),(206,'cqssc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:55:17'),(207,'cqssc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:55:17'),(208,'cqssc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:55:17'),(209,'cqssc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:55:17'),(210,'cqssc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:55:17'),(211,'cqssc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:55:17'),(213,'tc3','ds|单双/bs|百十/ewhs|二位和数','fs','复式',1,1,0,2,'2020-08-27 11:25:02'),(214,'tc3','sz|数值/bs|百十/ewhs|二位和数','fs','复式',1,1,0,4,'2020-08-27 11:25:02'),(215,'tc3','ws|尾数/bs|百十/ewhs|二位和数','fs','复式',1,1,0,1,'2020-08-30 22:39:36'),(216,'tc3','ds|单双/bg|百个/ewhs|二位和数','fs','复式',1,1,0,2,'2020-08-27 11:25:02'),(217,'tc3','sz|数值/bg|百个/ewhs|二位和数','fs','复式',1,1,0,4,'2020-08-27 11:25:02'),(218,'tc3','ws|尾数/bg|百个/ewhs|二位和数','fs','复式',1,1,0,1,'2020-08-30 22:39:36'),(219,'tc3','ds|单双/sg|十个/ewhs|二位和数','fs','复式',1,1,0,2,'2020-08-27 11:25:02'),(220,'tc3','sz|数值/sg|十个/ewhs|二位和数','fs','复式',1,1,0,4,'2020-08-27 11:25:02'),(221,'tc3','ws|尾数/sg|十个/ewhs|二位和数','fs','复式',1,1,0,1,'2020-08-30 22:39:36'),(222,'tc3','dx|大小/swhs|三位和数','fs','复式',1,1,0,10,'2020-08-27 11:25:02'),(223,'tc3','ds|单双/swhs|三位和数','fs','复式',1,1,0,11,'2020-08-27 11:25:02'),(224,'tc3','zh|质和/swhs|三位和数','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(225,'tc3','sz|数值/swhs|三位和数','fs','复式',1,1,0,11,'2020-08-27 11:25:02'),(226,'tc3','ws|尾数/swhs|三位和数','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(227,'tc3','sz|数值/yw|一位/bdw|不定位','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(228,'tc3','sz|数值/ew|二位/bdw|不定位','fs','复式',1,1,0,1,'2020-08-27 12:21:41'),(229,'tc3','sz|数值/sw|三位/bdw|不定位','fs','复式',1,1,0,1,'2020-08-28 02:32:12'),(230,'tc3','dx|大小/bw|百位/ywdw|一位定位','fs','复式',1,1,0,1,'2020-08-27 11:25:02'),(231,'tc3','ds|单双/bw|百位/ywdw|一位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(232,'tc3','zh|质和/bw|百位/ywdw|一位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(233,'tc3','sz|数值/bw|百位/ywdw|一位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(234,'tc3','dx|大小/sw|十位/ywdw|一位定位','fs','复式',1,1,0,1,'2020-08-27 11:25:02'),(235,'tc3','ds|单双/sw|十位/ywdw|一位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(236,'tc3','zh|质和/sw|十位/ywdw|一位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(237,'tc3','sz|数值/sw|十位/ywdw|一位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(238,'tc3','dx|大小/gw|个位/ywdw|一位定位','fs','复式',1,1,0,1,'2020-08-27 11:25:02'),(239,'tc3','ds|单双/gw|个位/ywdw|一位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(240,'tc3','zh|质和/gw|个位/ywdw|一位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(241,'tc3','sz|数值/gw|个位/ywdw|一位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(242,'tc3','sz|数值/bs|百十/ewdw|二位定位','fs','复式',1,1,0,1,'2020-08-27 11:25:02'),(243,'tc3','sz|数值/bg|百个/ewdw|二位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(244,'tc3','sz|数值/sg|十个/ewdw|二位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(245,'tc3','sz|数值/swdw|三位定位','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(246,'tc3','5m|5码/zx3|组选3/zx|组选','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(247,'tc3','6m|6码/zx3|组选3/zx|组选','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(248,'tc3','7m|7码/zx3|组选3/zx|组选','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(249,'tc3','8m|8码/zx3|组选3/zx|组选','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(250,'tc3','9m|9码/zx3|组选3/zx|组选','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(251,'tc3','qb|全包/zx3|组选3/zx|组选','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(252,'tc3','4m|4码/zx6|组选6/zx|组选','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(253,'tc3','5m|5码/zx6|组选6/zx|组选','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(254,'tc3','6m|6码/zx6|组选6/zx|组选','fs','复式',1,1,0,3,'2020-08-27 11:25:02'),(255,'tc3','7m|7码/zx6|组选6/zx|组选','fs','复式',1,1,0,10,'2020-08-27 11:25:02'),(256,'tc3','8m|8码/zx6|组选6/zx|组选','fs','复式',1,1,0,11,'2020-08-27 11:25:02'),(257,'tc3','dx|大小/bw|百位/yzgg|一字过关','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(258,'tc3','ds|单双/bw|百位/yzgg|一字过关','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(259,'tc3','zh|质和/bw|百位/yzgg|一字过关','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(260,'tc3','dx|大小/sw|十位/yzgg|一字过关','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(261,'tc3','ds|单双/sw|十位/yzgg|一字过关','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(262,'tc3','zh|质和/sw|十位/yzgg|一字过关','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(263,'tc3','dx|大小/gw|个位/yzgg|一字过关','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(264,'tc3','ds|单双/gw|个位/yzgg|一字过关','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(265,'tc3','zh|质和/gw|个位/yzgg|一字过关','fs','复式',1,1,0,12,'2020-08-27 11:25:02'),(266,'tc3','sz|数值/bw|百位/fszh|复式组合','fs','复式',1,1,0,20,'2020-08-27 11:25:02'),(267,'tc3','sz|数值/sw|十位/fszh|复式组合','fs','复式',1,1,0,20,'2020-08-27 11:25:02'),(268,'tc3','sz|数值/gw|个位/fszh|复式组合','fs','复式',1,1,0,20,'2020-08-27 11:25:02'),(269,'tc3','0k|0跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(270,'tc3','1k|1跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(271,'tc3','2k|2跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(272,'tc3','3k|3跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(273,'tc3','4k|4跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(274,'tc3','5k|5跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(275,'tc3','6k|6跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(276,'tc3','7k|7跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(277,'tc3','8k|8跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38'),(278,'tc3','9k|9跨/kd|跨度','fs','复式',1,1,0,1,'2020-08-27 12:21:38');
/*!40000 ALTER TABLE `play_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `play_type_num`
--

DROP TABLE IF EXISTS `play_type_num`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `play_type_num` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `play_type_id` int(11) NOT NULL,
  `bet_num` varchar(10) NOT NULL,
  `a_odds` decimal(8,4) DEFAULT NULL,
  `b_odds` decimal(8,4) DEFAULT NULL,
  `c_odds` decimal(8,4) DEFAULT NULL,
  `d_odds` decimal(8,4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `current_odds` decimal(19,2) DEFAULT NULL COMMENT '֧��ƽ̨,ϵͳ����',
  `bet_num_desc` varchar(150) DEFAULT NULL COMMENT '号码描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=433 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `play_type_num`
--

LOCK TABLES `play_type_num` WRITE;
/*!40000 ALTER TABLE `play_type_num` DISABLE KEYS */;
INSERT INTO `play_type_num` VALUES (1,213,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(2,213,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(3,214,'0-4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(4,214,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(5,214,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(6,214,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(7,214,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(8,214,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(9,214,'10',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(10,214,'11',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(11,214,'12',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(12,214,'13',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(13,214,'14-18',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(14,215,'0尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(15,215,'1尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(16,215,'2尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(17,215,'3尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(18,215,'4尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(19,215,'5尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(20,215,'6尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(21,215,'7尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(22,215,'8尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(23,215,'9尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(24,216,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(25,216,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(26,217,'0-4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(27,217,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(28,217,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(29,217,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(30,217,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(31,217,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(32,217,'10',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(33,217,'11',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(34,217,'12',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(35,217,'13',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(36,217,'14-18',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(37,218,'0尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(38,218,'1尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(39,218,'2尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(40,218,'3尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(41,218,'4尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(42,218,'5尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(43,218,'6尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(44,218,'7尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(45,218,'8尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(46,218,'9尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(47,219,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(48,219,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(49,220,'0-4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(50,220,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(51,220,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(52,220,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(53,220,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(54,220,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(55,220,'10',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(56,220,'11',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(57,220,'12',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(58,220,'13',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(59,220,'14-18',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(60,221,'0尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(61,221,'1尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(62,221,'2尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(63,221,'3尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(64,221,'4尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(65,221,'5尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(66,221,'6尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(67,221,'7尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(68,221,'8尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(69,221,'9尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(70,222,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'大'),(71,222,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'小'),(72,224,'质',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(73,224,'和',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(74,223,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(75,223,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(76,225,'0-6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(77,225,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(78,225,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(79,225,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(80,225,'10',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(81,225,'11',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(82,225,'12',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(83,225,'13',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(84,225,'14',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(85,225,'15',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(86,225,'16',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(87,225,'17',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(88,225,'18',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(89,225,'19',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(90,225,'20',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(91,225,'21-27',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(92,226,'0尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(93,226,'1尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(94,226,'2尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(95,226,'3尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(96,226,'4尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(97,226,'5尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(98,226,'6尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(99,226,'7尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(100,226,'8尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(101,226,'9尾',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(102,227,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(103,227,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(104,227,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(105,227,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(106,227,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(107,227,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(108,227,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(109,227,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(110,227,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(111,227,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(112,228,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(113,228,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(114,228,'02',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(115,228,'03',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(116,228,'04',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(117,228,'05',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(118,228,'06',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(119,228,'07',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(120,228,'08',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(121,228,'09',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(122,229,'000',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(123,229,'001',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(124,229,'002',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(125,229,'003',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(126,229,'004',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(127,229,'005',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(128,229,'006',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(129,229,'007',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(130,229,'008',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(131,229,'009',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(132,230,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'大'),(133,230,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'小'),(134,232,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'质'),(135,232,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'和'),(136,231,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(137,231,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(138,233,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'0'),(139,233,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'1'),(140,233,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'2'),(141,233,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'3'),(142,233,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'4'),(143,233,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'5'),(144,233,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'6'),(145,233,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'7'),(146,233,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'8'),(147,233,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'9'),(148,234,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'大'),(149,234,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'小'),(150,236,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'质'),(151,236,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'和'),(152,235,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(153,235,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(154,237,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'0'),(155,237,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'1'),(156,237,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'2'),(157,237,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'3'),(158,237,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'4'),(159,237,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'5'),(160,237,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'6'),(161,237,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'7'),(162,237,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'8'),(163,237,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'9'),(164,238,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'大'),(165,238,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'小'),(166,240,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'质'),(167,240,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'和'),(168,239,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(169,239,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(170,241,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'0'),(171,241,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'1'),(172,241,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'2'),(173,241,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'3'),(174,241,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'4'),(175,241,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'5'),(176,241,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'6'),(177,241,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'7'),(178,241,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'8'),(179,241,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'9'),(180,242,'00x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(181,242,'01x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(182,242,'02x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(183,242,'03x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(184,242,'04x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(185,242,'05x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(186,242,'06x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(187,242,'07x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(188,242,'08x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(189,242,'09x',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(190,243,'0x0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(191,243,'0x1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(192,243,'0x2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(193,243,'0x3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(194,243,'0x4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(195,243,'0x5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(196,243,'0x6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(197,243,'0x7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(198,243,'0x8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(199,243,'0x9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(200,244,'x00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(201,244,'x01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(202,244,'x02',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(203,244,'x03',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(204,244,'x04',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(205,244,'x05',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(206,244,'x06',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(207,244,'x07',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(208,244,'x08',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(209,244,'x09',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(210,245,'000',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(211,245,'001',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(212,245,'002',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(213,245,'003',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(214,245,'004',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(215,245,'005',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(216,245,'006',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(217,245,'007',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(218,245,'008',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(219,245,'009',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(220,246,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(221,246,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(222,246,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(223,246,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(224,246,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(225,246,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(226,246,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(227,246,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(228,246,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(229,246,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(230,247,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(231,247,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(232,247,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(233,247,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(234,247,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(235,247,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(236,247,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(237,247,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(238,247,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(239,247,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(240,248,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(241,248,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(242,248,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(243,248,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(244,248,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(245,248,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(246,248,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(247,248,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(248,248,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(249,248,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(250,249,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(251,249,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(252,249,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(253,249,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(254,249,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(255,249,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(256,249,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(257,249,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(258,249,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(259,249,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(260,250,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(261,250,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(262,250,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(263,250,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(264,250,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(265,250,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(266,250,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(267,250,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(268,250,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(269,250,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(270,251,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(271,251,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(272,251,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(273,251,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(274,251,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(275,251,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(276,251,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(277,251,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(278,251,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(279,251,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(280,252,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(281,252,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(282,252,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(283,252,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(284,252,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(285,252,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(286,252,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(287,252,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(288,252,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(289,252,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(290,253,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(291,253,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(292,253,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(293,253,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(294,253,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(295,253,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(296,253,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(297,253,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(298,253,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(299,253,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(300,254,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(301,254,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(302,254,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(303,254,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(304,254,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(305,254,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(306,254,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(307,254,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(308,254,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(309,254,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(310,255,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(311,255,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(312,255,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(313,255,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(314,255,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(315,255,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(316,255,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(317,255,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(318,255,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(319,255,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(320,256,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(321,256,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(322,256,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(323,256,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(324,256,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(325,256,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(326,256,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(327,256,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(328,256,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(329,256,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(330,257,'大',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(331,257,'小',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(332,259,'质',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(333,259,'和',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(334,258,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(335,258,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(336,260,'大',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(337,260,'小',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(338,262,'质',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(339,262,'和',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(340,261,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(341,261,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(342,263,'大',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(343,263,'小',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(344,265,'质',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(345,265,'和',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(346,264,'00',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'单'),(347,264,'01',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,1.90,'双'),(348,266,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(349,266,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(350,266,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(351,266,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(352,266,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(353,266,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(354,266,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(355,266,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(356,266,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(357,266,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(358,267,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(359,267,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(360,267,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(361,267,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(362,267,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(363,267,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(364,267,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(365,267,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(366,267,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(367,267,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(368,268,'0',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(369,268,'1',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(370,268,'2',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(371,268,'3',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(372,268,'4',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(373,268,'5',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(374,268,'6',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(375,268,'7',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(376,268,'8',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(377,268,'9',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(378,269,'000',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(379,269,'111',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(380,269,'222',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(381,269,'333',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(382,269,'444',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(383,269,'555',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(384,269,'666',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(385,269,'777',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(386,269,'888',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(387,269,'999',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(388,270,'001',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(389,270,'112',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(390,270,'223',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(391,270,'334',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(392,270,'445',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(393,270,'556',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(394,270,'667',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(395,270,'778',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(396,270,'889',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(397,271,'002',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(398,271,'113',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(399,271,'224',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(400,271,'335',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(401,271,'446',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(402,271,'557',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(403,271,'668',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(404,271,'779',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(405,272,'003',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(406,272,'114',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(407,272,'225',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(408,272,'336',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(409,272,'447',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(410,272,'558',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(411,272,'669',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(412,273,'004',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(413,273,'115',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(414,273,'226',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(415,273,'337',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(416,273,'448',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(417,273,'559',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(418,274,'005',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(419,274,'116',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(420,274,'227',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(421,274,'338',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(422,274,'449',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(423,275,'006',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(424,275,'117',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(425,275,'228',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(426,275,'339',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(427,276,'007',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(428,276,'118',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(429,276,'229',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(430,277,'008',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(431,277,'119',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL),(432,278,'009',1.9000,1.9000,1.9000,1.9000,'2020-09-06 12:40:42',NULL,NULL,NULL);
/*!40000 ALTER TABLE `play_type_num` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promo`
--

DROP TABLE IF EXISTS `promo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(50) DEFAULT NULL,
  `promo_type` varchar(50) DEFAULT NULL COMMENT '��ϵͳ�������ж�Ӧ',
  `is_multiple` int(11) DEFAULT NULL COMMENT '�Ƿ�����ȡ',
  `min_deposit_amount` float DEFAULT NULL COMMENT '��С��ֵ�����Ϊ0������',
  `flow_times` int(11) DEFAULT NULL COMMENT '��ˮ���������Ϊ0��û������',
  `value_type` int(11) DEFAULT NULL COMMENT '0,�ֽ�;1,����',
  `value` float DEFAULT NULL COMMENT '��ֵ',
  `wallet_type` int(11) DEFAULT NULL COMMENT '0,�ֽ�;1,����',
  `expired_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `withdraw_flow_times` int(11) DEFAULT NULL COMMENT '�����ˮ����,���Ϊ0������',
  `create_time` datetime DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promo`
--

LOCK TABLES `promo` WRITE;
/*!40000 ALTER TABLE `promo` DISABLE KEYS */;
/*!40000 ALTER TABLE `promo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promo_claim`
--

DROP TABLE IF EXISTS `promo_claim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promo_claim` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `promo_id` int(11) DEFAULT NULL,
  `claim_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promo_claim`
--

LOCK TABLES `promo_claim` WRITE;
/*!40000 ALTER TABLE `promo_claim` DISABLE KEYS */;
/*!40000 ALTER TABLE `promo_claim` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signup_rec`
--

DROP TABLE IF EXISTS `signup_rec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `signup_rec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `reward_points` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signup_rec`
--

LOCK TABLES `signup_rec` WRITE;
/*!40000 ALTER TABLE `signup_rec` DISABLE KEYS */;
/*!40000 ALTER TABLE `signup_rec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_mess_feedback`
--

DROP TABLE IF EXISTS `site_mess_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_mess_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fb_user_id` int(11) DEFAULT NULL,
  `mes_id` int(11) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `fb_time` datetime DEFAULT NULL,
  `is_read` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_mess_feedback`
--

LOCK TABLES `site_mess_feedback` WRITE;
/*!40000 ALTER TABLE `site_mess_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_mess_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_message`
--

DROP TABLE IF EXISTS `site_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `receiver` int(11) DEFAULT NULL,
  `is_read` int(11) DEFAULT NULL COMMENT '0,���Ķ�;1,δ�Ķ�',
  `create_time` datetime DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_message`
--

LOCK TABLES `site_message` WRITE;
/*!40000 ALTER TABLE `site_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_authority`
--

DROP TABLE IF EXISTS `sys_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_authority`
--

LOCK TABLES `sys_authority` WRITE;
/*!40000 ALTER TABLE `sys_authority` DISABLE KEYS */;
INSERT INTO `sys_authority` VALUES (2,2,1),(3,2,3),(4,2,4),(5,2,5),(6,2,6),(7,2,7),(8,2,8),(9,2,9),(10,3,8),(11,4,8),(12,5,8),(13,6,8),(14,7,8),(15,8,2),(16,9,6),(17,10,7),(18,11,8),(19,12,9),(20,13,2),(21,14,2),(22,15,2),(23,16,2),(24,17,2),(25,18,2),(26,19,8),(27,20,2),(28,21,8),(29,22,8),(30,23,8),(31,24,8),(32,25,8),(33,26,2),(34,27,2);
/*!40000 ALTER TABLE `sys_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_code`
--

DROP TABLE IF EXISTS `sys_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code_type` int(11) DEFAULT NULL,
  `is_code_type` int(11) DEFAULT NULL COMMENT '�Ƿ��������:0,�Ǵ�������;1,��������',
  `code_name` varchar(50) DEFAULT NULL COMMENT '�������ͻ��ߴ��������',
  `code_val` varchar(200) DEFAULT NULL COMMENT '����ֵ',
  `seq` int(11) DEFAULT NULL COMMENT '�������',
  `state` int(11) DEFAULT NULL COMMENT '�������ͻ��ߴ����״̬:0,��Ч;1,��Ч',
  `remark` varchar(500) DEFAULT NULL COMMENT '��ע',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_code`
--

LOCK TABLES `sys_code` WRITE;
/*!40000 ALTER TABLE `sys_code` DISABLE KEYS */;
INSERT INTO `sys_code` VALUES (1,NULL,1,'lottery_type','lottery_type',NULL,1,'彩种类型'),(2,1,1,'cqssc','cqssc',1,1,'重庆时时彩'),(3,1,0,'bjpk10','bjpk10',2,1,'北京PK10'),(4,1,0,'gd11x5','gd11x5',3,1,'广东11选5'),(5,1,0,'5fc','5fc',4,1,'5分彩'),(6,1,0,'sfc','sfc',5,1,'双分彩'),(7,1,0,'ffc','ffc',6,1,'分分彩'),(8,1,0,'mmc','mmc',7,1,'秒秒彩'),(9,1,0,'xyft','xyft',8,1,'幸运飞艇'),(10,1,0,'xjssc','xjssc',9,1,'新疆时时彩'),(12,NULL,1,'lottery_config_cqssc','lottery_config_cqssc',NULL,1,'重庆时时彩属性'),(13,NULL,1,'lottery_config_gd11x5','lottery_config_gd11x5',NULL,1,'广东11选5属性'),(14,NULL,1,'lottery_config_txffc','lottery_config_txffc',NULL,1,'腾讯分分彩属性'),(15,NULL,1,'lottery_config_5fc','lottery_config_5fc',NULL,1,'5分彩属性'),(16,NULL,1,'lottery_config_sfc','lottery_config_sfc',NULL,1,'双分彩属性'),(17,NULL,1,'lottery_config_ffc','lottery_config_ffc',NULL,1,'分分彩属性'),(18,NULL,1,'lottery_config_mmc','lottery_config_mmc',NULL,1,'秒秒彩属性'),(19,NULL,1,'lottery_config_bjpk10','lottery_config_bjpk10',NULL,1,'PK10属性'),(20,NULL,1,'lottery_config_xyft','lottery_config_xyft',NULL,1,'幸运飞艇属性'),(21,NULL,1,'lottery_config_xjssc','lottery_config_xjssc',NULL,1,'新疆时时彩'),(22,NULL,1,'lottery_config_yfb','lottery_config_yfb',NULL,1,'1.5分彩'),(23,1,0,'yfb','yfb',10,1,'1.5分彩'),(24,12,0,'dt_setting','0',1,1,'单挑设置'),(25,12,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(26,12,0,'betting_end_time','45',3,1,'截止投注时间'),(27,12,0,'bet_times','85',4,1,'投注倍数'),(28,12,0,'prize_mode','1',5,1,'开奖模式'),(29,12,0,'url_wining_number_extenal','https://shishicai.cjcp.com.cn/chongqing/kaijiang/',6,1,'数据接口'),(30,12,0,'wining_rate','0.0005',7,1,'中奖率'),(31,15,0,'dt_setting','0',1,1,'单挑设置'),(32,15,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(33,15,0,'betting_end_time','45',3,1,'截止投注时间'),(34,15,0,'bet_times','85',4,1,'投注倍数'),(35,15,0,'prize_mode','1',5,1,'开奖模式'),(36,15,0,'url_wining_number_extenal','',7,1,'数据接口'),(37,15,0,'wining_rate','0.0005',8,1,'中奖率'),(38,16,0,'dt_setting','0',1,1,'单挑设置'),(39,16,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(40,16,0,'betting_end_time','45',3,1,'截止投注时间'),(41,16,0,'bet_times','85',4,1,'投注倍数'),(42,16,0,'prize_mode','1',5,1,'开奖模式'),(43,16,0,'url_wining_number_extenal','',6,1,'数据接口'),(44,16,0,'wining_rate','0.0005',7,1,'中奖率'),(45,17,0,'dt_setting','0',1,1,'单挑设置'),(46,17,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(47,17,0,'betting_end_time','45',3,1,'截止投注时间'),(48,17,0,'bet_times','85',4,1,'投注倍数'),(49,17,0,'prize_mode','1',5,1,'开奖模式'),(50,17,0,'url_wining_number_extenal','',6,1,'数据接口'),(51,17,0,'wining_rate','0.0005',7,1,'中奖率'),(52,18,0,'dt_setting','0',1,1,'单挑设置'),(53,18,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(54,18,0,'betting_end_time','45',3,1,'截止投注时间'),(55,18,0,'bet_times','85',4,1,'投注倍数'),(56,18,0,'prize_mode','1',5,1,'开奖模式'),(57,18,0,'url_wining_number_extenal','',6,1,'数据接口'),(58,18,0,'wining_rate','0.0005',7,1,'中奖率'),(59,20,0,'dt_setting','0',1,1,'单挑设置'),(60,20,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(61,20,0,'betting_end_time','45',3,1,'截止投注时间'),(62,20,0,'bet_times','85',4,1,'投注倍数'),(63,20,0,'prize_mode','1',5,1,'开奖模式'),(64,20,0,'url_wining_number_extenal','',6,1,'数据接口'),(65,20,0,'wining_rate','0.0005',7,1,'中奖率'),(66,21,0,'dt_setting','0',1,1,'单挑设置'),(67,21,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(68,21,0,'betting_end_time','45',3,1,'截止投注时间'),(69,21,0,'bet_times','85',4,1,'投注倍数'),(70,21,0,'prize_mode','1',5,1,'开奖模式'),(71,21,0,'url_wining_number_extenal','',6,1,'数据接口'),(72,21,0,'wining_rate','0.0005',7,1,'中奖率'),(73,22,0,'dt_setting','0',1,1,'单挑设置'),(74,22,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(75,22,0,'betting_end_time','45',3,1,'截止投注时间'),(76,22,0,'bet_times','85',4,1,'投注倍数'),(77,22,0,'prize_mode','1',5,1,'开奖模式'),(78,22,0,'url_wining_number_extenal','',6,1,'数据接口'),(79,22,0,'wining_rate','0.0005',7,1,'中奖率'),(80,NULL,1,'sys_runtime_argument','sys_runtime_argument',NULL,1,'系统运行时参数'),(81,80,0,'notify_msg_valid_day','7',1,1,'系统通知有效时间'),(82,80,0,'site_msg_valid_day','14',2,1,'站内信有效时间'),(83,80,0,'number_of_bank_cards','5',3,1,'单个用户银行卡数量'),(84,80,0,'lotto_prize_rate','850,1000',4,1,'彩种赔率范围'),(85,80,0,'point_exchange_scale','1',5,1,'积分兑换比例(1代表1:1,0.1代表10:1,兑换红包金额)'),(86,80,0,'locking_time','5',6,1,'锁定时间(只能是整数分钟)'),(87,80,0,'fail_login_count','5',7,1,'失败登录次数'),(88,NULL,1,'bank_code_list','bank_code_list',NULL,1,'银行代码'),(89,88,0,'CDB','国家开发银行',NULL,1,'国家开发银行'),(90,88,0,'GDB','广东发展银行',NULL,1,'广东发展银行'),(91,88,0,'COMM','交通银行',NULL,1,'交通银行'),(92,88,0,'CMB','招商银行',NULL,1,'招商银行'),(93,88,0,'SPABANK','平安银行',NULL,1,'平安银行'),(94,88,0,'CEB','中国光大银行',NULL,1,'中国光大银行'),(95,88,0,'CMBC','中国民生银行',NULL,1,'中国民生银行'),(96,88,0,'ABC','中国农业银行',NULL,1,'中国农业银行'),(97,88,0,'BOC','中国银行',NULL,1,'中国银行'),(98,88,0,'CIB','兴业银行',NULL,1,'兴业银行'),(99,88,0,'PSBC','中国邮政储蓄银行',NULL,1,'中国邮政储蓄银行'),(100,88,0,'PSBC','中国邮政储蓄银行',NULL,1,'中国邮政储蓄银行'),(101,88,0,'SPDB','上海浦东发展银行',NULL,1,'上海浦东发展银行'),(102,88,0,'CCB','中国建设银行',NULL,1,'中国建设银行'),(103,88,0,'ICBC','中国工商银行',NULL,1,'中国工商银行'),(104,NULL,1,'withdrawal_cfg','withdrawal_cfg',NULL,1,'提款设置'),(105,104,0,'red_packet_wallet_rate','10',1,1,'红包余额转主钱包的流水倍数'),(106,104,0,'max_withdrawal_amt','50000',2,1,'最高提款金额'),(107,104,0,'min_withdrawal_amt','100',3,1,'最低提款金额'),(108,104,0,'day_count','3',4,1,'每日可提款次数'),(109,NULL,1,'demo_user_cfg','demo_user_cfg',NULL,1,'试玩用户属性'),(110,109,0,'demo_user_name_format','shiwan%08d',1,1,'试玩会员用户名格式'),(111,109,0,'demo_user_bal','100000',2,1,'初始余额'),(112,109,0,'ip_max_reg_size','1',3,1,'每个IP每天最大注册的会员数'),(113,109,0,'demo_user_platrebate','13.8',4,1,'试玩会员的平台点数'),(114,NULL,1,'sm_pankou','sm_pankou',NULL,1,'双面盘口设置'),(115,114,0,'sm_pankou_a','14.1',1,1,'A盘口'),(116,114,0,'sm_pankou_c','13',2,1,'C盘口'),(117,114,0,'sm_pankou_b','13.8',3,1,'B盘口'),(118,NULL,1,'flow_type','flow_type',NULL,1,'流水类型'),(119,118,0,'plat_reward','plat_reward',1,1,'平台积分'),(120,118,0,'recovery_payout','recovery_payout',2,1,'派奖回收'),(121,118,0,'acc_freeze','acc_freeze',3,1,'账户冻结'),(122,118,0,'sys_add','sys_add',4,1,'系统加钱'),(123,118,0,'payout','payout',5,1,'系统派奖'),(124,118,0,'acc_unfreeze','acc_unfreeze',6,1,'账户解冻'),(125,118,0,'sys_deduction','sys_deduction',7,1,'系统扣款'),(126,118,0,'cancel_rebate','cancel_rebate',8,1,'取消返点'),(127,118,0,'promo_points','promo_points',9,1,'活动积分'),(128,118,0,'wd_unfreeze','wd_unfreeze',10,1,'提款解冻'),(129,118,0,'bank_fees','bank_fees',11,1,'银行手续费'),(130,118,0,'withdrawal_back','withdrawal_back',12,1,'提款退还'),(131,118,0,'rebate','rebate',13,1,'返点'),(132,118,0,'deposit_cash','deposit_cash',14,1,'充值礼金'),(133,118,0,'betting','betting',15,1,'投注'),(134,118,0,'wd_freeze','wd_freeze',16,1,'提款冻结'),(135,118,0,'transfer','transfer',17,1,'转账'),(136,118,0,'promo_cash','promo_cash',18,1,'活动礼金'),(137,118,0,'deposit','deposit',19,1,'充值'),(138,118,0,'user_red_envelope_withdrawal_deduction','user_red_envelope_withdrawal_deduction',20,1,'用户红包提现扣除'),(139,118,0,'customer_claims','customer_claims',20,1,'平台奖励'),(140,118,0,'withdraw','withdraw',21,1,'提款'),(141,118,0,'user_red_envelope_withdrawal_deduction','user_red_envelope_withdrawal_deduction',22,1,'用户红包提现扣除'),(142,NULL,1,'payment_platform','payment_platform',NULL,1,'支付平台'),(143,142,0,'self_pay','self_pay',1,1,'平台自有支付'),(144,NULL,1,'lucky_draw','lucky_draw',NULL,1,'幸运抽奖设置'),(145,144,0,'winning_probability','0.02',1,1,'中奖概率'),(146,144,0,'minimum_recharge','100',2,1,'最小充值金额'),(147,144,0,'winning_range','3,5,7,9',3,1,'中奖范围'),(148,144,0,'minimum_amount_of_water','100000',4,1,'最小流水金额'),(149,NULL,1,'sign_in_day','sign_in_day',NULL,1,'签到活动'),(150,149,0,'sign_in_point_range','30,80,-100',1,1,'积分获取范围'),(151,NULL,1,'pay_type_class','pay_type_class',NULL,1,'充值方式类型'),(152,151,0,'online_banking','online_banking',1,1,'网银'),(153,151,0,'qr_code','qr_code',2,1,'二维码扫码'),(155,1,0,'demo_lottery','demo_lottery',11,0,'测试彩种'),(157,144,0,'demo_flowtype','demo_flowtype',5,1,'测试流水类型'),(158,13,0,'dt_setting','0',1,1,'单挑设置'),(159,13,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(160,13,0,'betting_end_time','45',3,1,'截止投注时间'),(161,13,0,'bet_times','85',4,1,'投注倍数'),(162,13,0,'prize_mode','1',5,1,'开奖模式'),(163,13,0,'url_wining_number_extenal','http://api.api68.com/lottery/getLotteryListByHot.do',6,1,'数据接口'),(164,13,0,'wining_rate','0.0005',7,1,'中奖率'),(167,NULL,1,'testCodeType3','testCodeType3',NULL,1,'testCodeType3'),(168,NULL,1,'testCodeType2','testCodeType2',NULL,1,'testCodeType2'),(169,118,0,'demo_flow_type','demo_flow_type',23,1,'demo_flow_type'),(170,142,0,'demo_payment','demo_payment1',2,1,'demo_payment1'),(171,80,NULL,'max_plat_rebate','14.6',8,1,'平台最大返点'),(172,1,0,'tc3','tc3',12,1,'体彩3'),(173,1,0,'fc3d','fc3d',13,1,'福彩3D'),(174,181,0,'dt_setting','0',1,1,'单挑设置'),(175,181,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(176,181,0,'betting_end_time','45',3,1,'截止投注时间'),(177,181,0,'bet_times','85',4,1,'投注倍数'),(178,181,0,'prize_mode','1',5,1,'开奖模式'),(179,181,0,'url_wining_number_extenal','https://shishicai.cjcp.com.cn/chongqing/kaijiang/',6,1,'数据接口'),(180,181,0,'wining_rate','0.0005',7,1,'中奖率'),(181,NULL,1,'lottery_config_tc3','lottery_config_cqssc',NULL,1,'重庆时时彩属性');
/*!40000 ALTER TABLE `sys_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_type` varchar(30) DEFAULT NULL,
  `log_ope_type` varchar(30) DEFAULT NULL COMMENT 'Ȩ��',
  `user_id` int(11) DEFAULT NULL COMMENT '0:��Ч;1:��Ч',
  `log_data` varchar(1000) DEFAULT NULL COMMENT 'Ȩ��',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` VALUES (1,'ope_log_mod_login_pwd','ope_log_mod_login_pwd',1,'{\"newPwd\":\"222222\",\"confirmPwd\":\"222222\",\"oldPwd\":\"111111\",\"reqUrl\":\"\\/lottery-api\\/users\\/attrs\\/login-pwd\"}','2020-07-02 23:40:21'),(2,'ope_log_mod_login_pwd','ope_log_mod_login_pwd',1,'{\"newPwd\":\"111111\",\"confirmPwd\":\"111111\",\"oldPwd\":\"222222\",\"reqUrl\":\"\\/lottery-api\\/users\\/attrs\\/login-pwd\"}','2020-07-02 23:44:21'),(3,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"111111\",\"xyAmount\":\"100000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001\",\"userId\":\"\",\"realName\":\"张三\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-03 09:19:03'),(4,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"111111\",\"xyAmount\":\"100000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_002\",\"userId\":\"\",\"realName\":\"李四\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-04 01:26:02'),(5,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_003\",\"userId\":\"\",\"realName\":\"李五\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-04 01:28:57'),(6,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_004\",\"userId\":\"\",\"realName\":\"李六\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-04 01:47:16'),(7,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_01\",\"userId\":\"\",\"realName\":\"李八\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-07-04 01:48:26'),(8,'ope_log_mod_login_pwd','ope_log_mod_login_pwd',2,'{\"newPwd\":\"222222\",\"confirmPwd\":\"222222\",\"id\":\"2\",\"oldPwd\":\"111111\",\"reqUrl\":\"\\/lottery-api\\/users\\/attrs\\/login-pwdAdmin\"}','2020-07-08 11:23:27'),(9,'ope_log_mod_login_pwd','ope_log_mod_login_pwd',2,'{\"newPwd\":\"111111\",\"confirmPwd\":\"111111\",\"id\":\"2\",\"oldPwd\":\"222222\",\"reqUrl\":\"\\/lottery-api\\/users\\/attrs\\/login-pwdAdmin\"}','2020-07-08 11:27:06'),(10,'ope_log_cancel_issue','ope_log_cancel_issue',2,'{\"lottoType\":\"gd11x5\",\"reqUrl\":\"\\/lottery-api\\/sys\\/oper\\/issue\\/200708-12\\/disbale\"}','2020-07-08 11:57:03'),(11,'ope_log_cancel_issue','ope_log_cancel_issue',2,'{\"lottoType\":\"gd11x5\",\"reqUrl\":\"\\/lottery-api\\/sys\\/oper\\/issue\\/200708-42\\/disbale\"}','2020-07-08 12:03:12'),(12,'ope_log_cancel_issue','ope_log_cancel_issue',2,'{\"lottoType\":\"gd11x5\",\"reqUrl\":\"\\/lottery-api\\/sys\\/oper\\/issue\\/200708-42\\/disbale\"}','2020-07-08 12:03:18'),(13,'ope_log_issue_manual_payout','ope_log_issue_manual_payout',2,'{\"lottoType\":\"gd11x5\",\"reqUrl\":\"\\/lottery-api\\/sys\\/oper\\/issue\\/200708-08\\/payout\"}','2020-07-08 12:04:25'),(14,'ope_log_re_payout','ope_log_re_payout',2,'{\"lottoType\":\"gd11x5\",\"reqUrl\":\"\\/lottery-api\\/sys\\/oper\\/issue\\/200708-08\\/re-payout\"}','2020-07-08 12:04:33'),(15,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"1000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_01\",\"userId\":\"\",\"realName\":\"信誉玩家\",\"userType\":\"7\",\"panKou\":\"3\",\"platRebate\":\"13.8\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-07-08 12:53:26'),(16,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"1000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_02\",\"userId\":\"\",\"realName\":\"信誉玩家\",\"userType\":\"7\",\"panKou\":\"3\",\"platRebate\":\"13.8\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-07-08 12:53:41'),(17,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"realName\":\"线上代理\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"wechat\":\"\",\"phoneNum\":\"\",\"userType\":\"1\",\"userName\":\"online_agent_001\",\"userId\":\"\",\"platRebate\":\"39\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-09 11:25:35'),(18,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"realName\":\"在线代理\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"wechat\":\"\",\"phoneNum\":\"\",\"userType\":\"1\",\"userName\":\"online_agent_001\",\"userId\":\"\",\"platRebate\":\"9.733\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-09 12:39:28'),(19,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"realName\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"wechat\":\"\",\"phoneNum\":\"\",\"userType\":\"6\",\"userName\":\"sm_agent_001\",\"userId\":\"\",\"platRebate\":\"4.867\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-09 13:11:00'),(20,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_002\",\"userId\":\"\",\"realName\":\"\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-09 13:11:41'),(21,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"realName\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"wechat\":\"\",\"phoneNum\":\"\",\"userType\":\"10\",\"userName\":\"entrust_agent_001\",\"userId\":\"\",\"platRebate\":\"4.867\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-09 13:12:32'),(22,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_03\",\"userId\":\"\",\"realName\":\"\",\"userType\":\"7\",\"panKou\":\"3\",\"platRebate\":\"13.8\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-07-09 13:16:16'),(23,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"realName\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"wechat\":\"\",\"phoneNum\":\"\",\"userType\":\"0\",\"userName\":\"cash_agent_001_01\",\"userId\":\"\",\"platRebate\":\"4.867\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/9\",\"email\":\"\"}','2020-07-09 13:18:22'),(24,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"realName\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"wechat\":\"\",\"phoneNum\":\"\",\"userType\":\"5\",\"userName\":\"sm_agent_001_01\",\"userId\":\"\",\"platRebate\":\"2.433\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/10\",\"email\":\"\"}','2020-07-09 13:19:47'),(25,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"2000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_04\",\"userId\":\"\",\"realName\":\"\",\"userType\":\"7\",\"panKou\":\"3\",\"platRebate\":\"13.8\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/7\",\"email\":\"\"}','2020-07-10 12:44:22'),(26,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_05\",\"userId\":\"\",\"realName\":\"\",\"userType\":\"7\",\"panKou\":\"2\",\"platRebate\":\"13\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-07-10 13:34:36'),(27,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_06\",\"userId\":\"\",\"realName\":\"\",\"userType\":\"7\",\"panKou\":\"2\",\"platRebate\":\"13\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-07-10 13:36:50'),(28,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_07\",\"userId\":\"\",\"realName\":\"\",\"userType\":\"8\",\"panKou\":\"3\",\"platRebate\":\"13.8\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-07-10 13:37:58'),(29,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"3000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_10\",\"userId\":\"\",\"realName\":\"\",\"userType\":\"7\",\"panKou\":\"3\",\"platRebate\":\"13.8\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-07-10 13:46:56'),(30,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_001_12\",\"userId\":\"\",\"realName\":\"\",\"userType\":\"8\",\"panKou\":\"3\",\"platRebate\":\"13.8\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-07-10 13:48:44'),(31,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"10\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_006\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"20\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-07-31 11:51:01'),(32,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"100\",\"loginPwd\":\"111111\",\"fundPwd\":\"111111\",\"xyAmount\":\"100000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_007\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"200\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-08-01 11:32:47'),(33,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"100\",\"loginPwd\":\"111111\",\"fundPwd\":\"111111\",\"xyAmount\":\"100000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_007\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"200\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-08-01 11:33:19'),(34,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"100\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_008\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"300\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-08-01 12:12:04'),(35,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"10000\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_008\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"1000\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-08-02 14:39:47'),(36,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"10000\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"1000\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-08-02 14:43:07'),(37,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"10\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"10\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-08-02 14:43:38'),(38,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"100000\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009_001\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"10000\",\"userType\":\"7\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/25\",\"email\":\"\"}','2020-08-02 15:28:48'),(39,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"10\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009_001\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"10\",\"userType\":\"7\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/25\",\"email\":\"\"}','2020-08-02 15:29:17'),(40,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"10\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009_001\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"10\",\"userType\":\"7\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/25\",\"email\":\"\"}','2020-08-02 15:32:43'),(41,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"10\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009_001\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"10\",\"userType\":\"7\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/25\",\"email\":\"\"}','2020-08-02 15:36:24'),(42,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"9\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009_001\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"9\",\"userType\":\"7\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/25\",\"email\":\"\"}','2020-08-02 15:36:47'),(43,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"-9\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009_002\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"-9\",\"userType\":\"7\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/25\",\"email\":\"\"}','2020-08-02 15:40:47'),(44,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"-9\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009_002\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"-9\",\"userType\":\"7\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/25\",\"email\":\"\"}','2020-08-02 15:41:44'),(45,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"-9\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_009_002\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"-9\",\"userType\":\"7\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/25\",\"email\":\"\"}','2020-08-02 15:51:26'),(46,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"-9\",\"loginPwd\":\"111111\",\"fundPwd\":\"\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_agent_010\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"-9\",\"userType\":\"8\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/1\",\"email\":\"\"}','2020-08-02 15:54:49'),(47,'ope_log_reg_agent','ope_log_reg_agent',2,'{\"qq\":\"\",\"tsAmount\":\"10\",\"loginPwd\":\"111111\",\"fundPwd\":\"111111\",\"xyAmount\":\"10000\",\"wechat\":\"\",\"phoneNum\":\"\",\"userName\":\"xy_uer_001\",\"userId\":\"\",\"realName\":\"\",\"zcAmount\":\"10\",\"userType\":\"7\",\"panKou\":\"1\",\"platRebate\":\"14.1\",\"reqUrl\":\"\\/lottery-api\\/users\\/agents\\/3\",\"email\":\"\"}','2020-09-19 10:09:07');
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_login`
--

DROP TABLE IF EXISTS `sys_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_type` varchar(30) DEFAULT NULL,
  `log_ope_type` varchar(30) DEFAULT NULL COMMENT 'Ȩ��',
  `user_id` int(11) DEFAULT NULL COMMENT '0:��Ч;1:��Ч',
  `log_data` varchar(1000) DEFAULT NULL COMMENT 'Ȩ��',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_login`
--

LOCK TABLES `sys_login` WRITE;
/*!40000 ALTER TABLE `sys_login` DISABLE KEYS */;
INSERT INTO `sys_login` VALUES (1,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"admint\",\"ip\":\"127.0.0.1\"}','2020-08-01 10:04:19'),(2,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"user_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-19 10:01:40'),(3,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"user_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-19 10:01:49'),(4,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"user_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-19 10:02:05'),(5,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"user_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-19 10:02:27'),(6,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"xy_user_001\",\"ip\":\"127.0.0.1\"}','2020-09-19 10:09:39'),(7,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"xy_user_001\",\"ip\":\"127.0.0.1\"}','2020-09-19 10:09:57'),(8,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"xy_user_001\",\"ip\":\"127.0.0.1\"}','2020-09-19 10:10:32'),(9,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"user_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-20 11:06:15'),(10,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"user_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-20 11:06:25'),(11,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"uer_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-20 11:06:36'),(12,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"uer_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-20 11:06:52'),(13,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"uer_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-20 20:50:19'),(14,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"uer_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-20 20:50:24'),(15,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"uer_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-20 20:50:33'),(16,'ope_log_user_failure','ope_log_user_failure',NULL,'{\"userName\":\"uer_xy_001\",\"ip\":\"127.0.0.1\"}','2020-09-20 20:52:15');
/*!40000 ALTER TABLE `sys_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notification`
--

DROP TABLE IF EXISTS `sys_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `receiver_type` int(11) DEFAULT NULL COMMENT '0:���㼶��ϵ����;1,�����ͷ���',
  `receiver` int(11) DEFAULT NULL COMMENT '���ղ㼶���ͣ��˴����Ǵ����û�id;����������ͷ���:0,ȫ���û�,1,���д����û�,2,������ͨ�û�',
  `create_time` datetime DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notification`
--

LOCK TABLES `sys_notification` WRITE;
/*!40000 ALTER TABLE `sys_notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(60) DEFAULT NULL,
  `role_desc` varchar(100) DEFAULT NULL COMMENT 'Ȩ��',
  `state` int(11) DEFAULT NULL COMMENT '0:��Ч;1:��Ч',
  `creator` int(11) DEFAULT NULL COMMENT '������ID',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'ROLE_ADMIN','系统管理员角色',1,1,'2020-06-28 21:14:20'),(2,'ROLE_USER','玩家角色',1,1,'2020-06-28 21:26:58'),(3,'ROLE_CUSTOMER_SERVICE','客服角色',1,1,'2020-06-28 21:26:58'),(4,'ROLE_FINANCE','财务角色',1,1,'2020-06-28 21:26:59'),(5,'ROLE_MANAGER','经理角色',1,1,'2020-06-28 21:26:59'),(6,'ROLE_AGENT','代理角色',1,1,'2020-06-28 21:26:59'),(7,'ROLE_AGENT_SM','双面代理角色',1,1,'2020-06-28 21:26:59'),(8,'ROLE_AGENT_XY','信誉代理角色',1,1,'2020-06-28 21:26:59'),(9,'ROLE_AGENT_ENTRUST','委托代理角色',1,1,'2020-06-28 21:26:59'),(10,'ROLE_USER_SM','双面玩家角色',1,1,'2020-06-28 21:26:59'),(11,'ROLE_USER_XY','信誉玩家角色',1,1,'2020-06-28 21:27:01');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbUsersRate`
--

DROP TABLE IF EXISTS `tbUsersRate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbUsersRate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `ms17` double DEFAULT NULL COMMENT '�û�����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbUsersRate`
--

LOCK TABLES `tbUsersRate` WRITE;
/*!40000 ALTER TABLE `tbUsersRate` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbUsersRate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbuser`
--

DROP TABLE IF EXISTS `tbuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `usermoney` double DEFAULT NULL COMMENT '֧������',
  `icemoney` double DEFAULT NULL COMMENT '�û�״̬:0,����;1,����;2,����',
  `creditmoney` double DEFAULT NULL COMMENT '��½����',
  `activitymoney` double DEFAULT NULL COMMENT '������ Ҳ���Ǵ���',
  `regfrom` varchar(255) DEFAULT NULL COMMENT '����',
  `isdaili` varchar(255) DEFAULT NULL COMMENT 'ƽ̨��������ߴ�����¼��������ҵĵ�',
  `islock` varchar(19) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL COMMENT '������ Ҳ���Ǵ���',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbuser`
--

LOCK TABLES `tbuser` WRITE;
/*!40000 ALTER TABLE `tbuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_pl_report`
--

DROP TABLE IF EXISTS `team_pl_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_pl_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` date NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '��ֵ��Ա����',
  `user_name` varchar(50) DEFAULT NULL COMMENT '�û���',
  `deposit` decimal(19,2) DEFAULT NULL COMMENT '�û����',
  `withdrawal` decimal(19,2) DEFAULT NULL COMMENT '�û�ȡ��',
  `transfer` decimal(19,2) DEFAULT NULL COMMENT '�û�ȡ��',
  `transfer_out` decimal(19,2) DEFAULT NULL COMMENT '�û�ȡ��',
  `deduction` decimal(19,2) DEFAULT NULL COMMENT '�۳�',
  `consumption` decimal(19,2) DEFAULT NULL COMMENT '����',
  `cancel_amount` decimal(19,2) DEFAULT NULL COMMENT '����',
  `return_prize` decimal(19,2) DEFAULT NULL COMMENT '����',
  `sys_bonus` decimal(19,2) DEFAULT NULL COMMENT '����',
  `rebate` decimal(19,2) DEFAULT NULL COMMENT '����',
  `recharge_member` int(11) DEFAULT NULL COMMENT '��ֵ��Ա����',
  `new_members` int(11) DEFAULT NULL COMMENT '������Ա����',
  `profit` decimal(19,2) DEFAULT NULL COMMENT 'ӯ��',
  `user_type` int(11) DEFAULT NULL COMMENT '�û�����:0,ƽ̨�ͻ�;1,����;3,�ܴ�',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_pl_report`
--

LOCK TABLES `team_pl_report` WRITE;
/*!40000 ALTER TABLE `team_pl_report` DISABLE KEYS */;
INSERT INTO `team_pl_report` VALUES (1,'2020-10-04',27,'xy_uer_001',NULL,NULL,NULL,NULL,NULL,200.00,NULL,NULL,NULL,NULL,NULL,NULL,-200.00,7),(2,'2020-10-06',27,'xy_uer_001',NULL,NULL,NULL,NULL,NULL,500.00,NULL,NULL,NULL,NULL,NULL,NULL,-500.00,7),(3,'2020-10-06',3,'xy_agent_001',NULL,NULL,NULL,NULL,NULL,400.00,NULL,NULL,NULL,NULL,NULL,NULL,0.00,8),(4,'2020-10-06',1,'general_agent',NULL,NULL,NULL,NULL,NULL,100.00,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3),(5,'2020-10-11',27,'xy_uer_001',NULL,NULL,NULL,NULL,NULL,10.00,NULL,NULL,NULL,NULL,NULL,NULL,-10.00,7),(6,'2020-10-11',3,'xy_agent_001',NULL,NULL,NULL,NULL,NULL,10.00,NULL,NULL,NULL,NULL,NULL,NULL,0.00,8),(7,'2020-10-11',1,'general_agent',NULL,NULL,NULL,NULL,NULL,10.00,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3),(8,'2020-10-14',27,'xy_uer_001',NULL,NULL,NULL,NULL,NULL,24.00,NULL,NULL,NULL,NULL,NULL,NULL,-24.00,7),(9,'2020-10-14',3,'xy_agent_001',NULL,NULL,NULL,NULL,NULL,24.00,NULL,NULL,NULL,NULL,NULL,NULL,0.00,8),(10,'2020-10-14',1,'general_agent',NULL,NULL,NULL,NULL,NULL,24.00,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3),(11,'2020-10-18',27,'xy_uer_001',NULL,NULL,NULL,NULL,NULL,8.00,NULL,NULL,NULL,NULL,NULL,NULL,-8.00,7),(12,'2020-10-18',3,'xy_agent_001',NULL,NULL,NULL,NULL,NULL,8.00,NULL,NULL,NULL,NULL,NULL,NULL,0.00,8),(13,'2020-10-18',1,'general_agent',NULL,NULL,NULL,NULL,NULL,8.00,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3);
/*!40000 ALTER TABLE `team_pl_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tmp_user_account_details`
--

DROP TABLE IF EXISTS `tmp_user_account_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_user_account_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `pre_amount` double DEFAULT NULL COMMENT '�䶯ǰ���',
  `post_amount` double DEFAULT NULL COMMENT '�䶯����',
  `wallet_id` int(11) DEFAULT NULL COMMENT '�û��˻�id',
  `order_id` int(11) DEFAULT NULL COMMENT '�����Դ�ڳ�ֵ��������������룬��Ҫ��д',
  `operation_type` varchar(30) DEFAULT NULL COMMENT '�˻��������ͣ���Ӧϵͳ����',
  `create_time` datetime DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  `remark` longtext COMMENT '�˻��������ͣ���Ӧϵͳ����',
  `flag` int(11) DEFAULT NULL COMMENT '�����Դ�ڳ�ֵ��������������룬��Ҫ��д',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tmp_user_account_details`
--

LOCK TABLES `tmp_user_account_details` WRITE;
/*!40000 ALTER TABLE `tmp_user_account_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `tmp_user_account_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tp_notices`
--

DROP TABLE IF EXISTS `tp_notices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tp_notices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `result` varchar(20) DEFAULT NULL,
  `amount` float DEFAULT NULL COMMENT '�ɹ���ֵ���',
  `order_num` varchar(50) DEFAULT NULL COMMENT '��ֵ������',
  `transaction_num` varchar(50) DEFAULT NULL COMMENT '��3��������',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tp_notices`
--

LOCK TABLES `tp_notices` WRITE;
/*!40000 ALTER TABLE `tp_notices` DISABLE KEYS */;
/*!40000 ALTER TABLE `tp_notices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `acc_name` varchar(50) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `freeze` double DEFAULT NULL COMMENT '������',
  `prize` double DEFAULT NULL COMMENT '����',
  `reward_points` bigint(20) DEFAULT NULL COMMENT '����!=Ǯ,��Ҫ���һ����Ķһ�',
  `acc_type` int(11) DEFAULT NULL COMMENT '��ϵͳ���������ж���:1,��Ǯ��;2,���Ǯ��',
  `state` int(11) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (1,3,'主钱包',5002,0,0,0,1,1,'主钱包'),(2,3,'红包钱包',0,0,0,0,2,1,'红包钱包'),(9,7,'主钱包',0,0,0,0,1,1,'主钱包'),(10,7,'红包钱包',0,0,0,0,2,1,'红包钱包'),(11,8,'主钱包',0,0,0,0,1,1,'主钱包'),(12,8,'红包钱包',0,0,0,0,2,1,'红包钱包'),(13,9,'主钱包',0,0,0,0,1,0,'主钱包'),(14,9,'红包钱包',0,0,0,0,2,0,'红包钱包'),(15,10,'主钱包',0,0,0,0,1,0,'主钱包'),(16,10,'红包钱包',0,0,0,0,2,0,'红包钱包'),(17,11,'主钱包',1,0,0,0,1,1,'主钱包'),(18,11,'红包钱包',0,0,0,0,2,1,'红包钱包'),(19,12,'主钱包',0,0,0,0,1,0,'主钱包'),(20,12,'红包钱包',0,0,0,0,2,0,'红包钱包'),(21,13,'主钱包',1,0,0,0,1,1,'主钱包'),(22,13,'红包钱包',0,0,0,0,2,1,'红包钱包'),(23,14,'主钱包',0,0,0,0,1,0,'主钱包'),(24,14,'红包钱包',0,0,0,0,2,0,'红包钱包'),(25,15,'主钱包',0,0,0,0,1,0,'主钱包'),(26,15,'红包钱包',0,0,0,0,2,0,'红包钱包'),(27,1,'主钱包',99989996,0,0,0,1,1,'主钱包'),(28,1,'红包钱包',0,0,0,0,2,1,'红包钱包'),(29,16,'主钱包',0,0,0,0,1,1,'主钱包'),(30,16,'红包钱包',0,0,0,0,2,1,'红包钱包'),(31,17,'主钱包',0,0,0,0,1,1,'主钱包'),(32,17,'红包钱包',0,0,0,0,2,1,'红包钱包'),(33,18,'主钱包',0,0,0,0,1,1,'主钱包'),(34,18,'红包钱包',0,0,0,0,2,1,'红包钱包'),(35,19,'主钱包',0,0,0,0,1,1,'主钱包'),(36,19,'红包钱包',0,0,0,0,2,1,'红包钱包'),(37,20,'主钱包',0,0,0,0,1,1,'主钱包'),(38,20,'红包钱包',0,0,0,0,2,1,'红包钱包'),(39,21,'主钱包',0,0,0,0,1,1,'主钱包'),(40,21,'红包钱包',0,0,0,0,2,1,'红包钱包'),(41,22,'主钱包',0,0,0,0,1,1,'主钱包'),(42,22,'红包钱包',0,0,0,0,2,1,'红包钱包'),(43,23,'主钱包',0,0,0,0,1,1,'主钱包'),(44,23,'红包钱包',0,0,0,0,2,1,'红包钱包'),(45,24,'主钱包',0,0,0,0,1,1,'主钱包'),(46,24,'红包钱包',0,0,0,0,2,1,'红包钱包'),(47,25,'主钱包',0,0,0,0,1,1,'主钱包'),(48,25,'红包钱包',0,0,0,0,2,1,'红包钱包'),(49,26,'主钱包',0,0,0,0,1,1,'主钱包'),(50,26,'红包钱包',0,0,0,0,2,1,'红包钱包'),(51,27,'主钱包',3858,0,0,0,1,0,'主钱包'),(52,27,'红包钱包',0,0,0,0,2,1,'红包钱包');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_details`
--

DROP TABLE IF EXISTS `user_account_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `pre_amount` double DEFAULT NULL COMMENT '�䶯ǰ���',
  `post_amount` double DEFAULT NULL COMMENT '�䶯����',
  `wallet_id` int(11) DEFAULT NULL COMMENT '�û��˻�id',
  `order_id` int(11) DEFAULT NULL COMMENT '�����Դ�ڳ�ֵ��������������룬��Ҫ��д',
  `operation_type` varchar(30) DEFAULT NULL COMMENT '�˻��������ͣ���Ӧϵͳ����',
  `data_item_type` int(11) DEFAULT NULL COMMENT '账户变动数据类型,0:余额;1:奖金;2,积分;3,冻结资金',
  `create_time` datetime DEFAULT NULL,
  `remark` longtext COMMENT '�˻��������ͣ���Ӧϵͳ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_details`
--

LOCK TABLES `user_account_details` WRITE;
/*!40000 ALTER TABLE `user_account_details` DISABLE KEYS */;
INSERT INTO `user_account_details` VALUES (1,1,1,100000000,99999999,27,1,'transfer',0,'2020-07-10 11:45:16','out'),(2,11,1,0,1,17,1,'transfer',0,'2020-07-10 11:45:16','in'),(3,1,1,99999999,99999998,27,2,'transfer',0,'2020-07-10 11:50:52','out'),(4,3,1,0,1,1,2,'transfer',0,'2020-07-10 11:50:52','in'),(5,1,1,99999998,99999997,27,3,'transfer',0,'2020-07-10 11:53:52','out'),(6,3,1,1,2,1,3,'transfer',0,'2020-07-10 11:53:52','in'),(7,3,1,2,1,1,4,'transfer',0,'2020-07-10 12:05:26','out'),(8,13,1,0,1,21,4,'transfer',0,'2020-07-10 12:05:26','in'),(9,1,1,99999997,99999996,27,5,'transfer',0,'2020-07-10 13:46:22','out'),(10,3,1,1,2,1,5,'transfer',0,'2020-07-10 13:46:22','in'),(11,1,10000,99999996,99989996,27,6,'transfer',0,'2020-09-19 10:12:42','out'),(12,3,10000,2,10002,1,6,'transfer',0,'2020-09-19 10:12:42','in'),(13,3,5000,10002,5002,1,7,'transfer',0,'2020-09-19 10:12:56','out'),(14,27,5000,0,5000,51,7,'transfer',0,'2020-09-19 10:12:56','in'),(15,27,100,5000,4900,51,1,'betting',0,'2020-09-27 17:57:24',NULL),(16,27,100,4900,4800,51,2,'betting',0,'2020-09-27 18:04:50',NULL),(17,27,100,4800,4700,51,3,'betting',0,'2020-09-27 18:13:43',NULL),(18,27,100,4700,4600,51,4,'betting',0,'2020-09-28 10:34:19',NULL),(19,27,100,4600,4500,51,5,'betting',0,'2020-10-03 01:18:23',NULL),(20,27,100,4500,4400,51,6,'betting',0,'2020-10-03 01:23:41',NULL),(21,27,100,4400,4300,51,7,'betting',0,'2020-10-04 11:05:06',NULL),(22,27,100,4300,4200,51,8,'betting',0,'2020-10-04 11:52:16',NULL),(23,27,100,4200,4100,51,9,'betting',0,'2020-10-06 00:58:38',NULL),(24,27,100,4100,4000,51,10,'betting',0,'2020-10-06 01:08:24',NULL),(25,27,100,4000,3900,51,11,'betting',0,'2020-10-06 10:34:17',NULL),(26,27,1,3900,3899,51,12,'betting',0,'2020-10-11 14:37:23',NULL),(27,27,1,3899,3898,51,13,'betting',0,'2020-10-11 15:32:12',NULL),(28,27,1,3898,3897,51,14,'betting',0,'2020-10-11 16:28:03',NULL),(29,27,1,3897,3896,51,15,'betting',0,'2020-10-11 16:29:06',NULL),(30,27,1,3896,3895,51,16,'betting',0,'2020-10-11 16:29:56',NULL),(31,27,1,3895,3894,51,17,'betting',0,'2020-10-11 16:29:56',NULL),(32,27,1,3894,3893,51,18,'betting',0,'2020-10-11 17:59:32',NULL),(33,27,1,3893,3892,51,19,'betting',0,'2020-10-11 17:59:32',NULL),(34,27,1,3892,3891,51,20,'betting',0,'2020-10-11 17:59:32',NULL),(35,27,1,3891,3890,51,21,'betting',0,'2020-10-11 17:59:32',NULL),(36,27,1,3890,3889,51,22,'betting',0,'2020-10-14 09:39:37',NULL),(37,27,1,3889,3888,51,23,'betting',0,'2020-10-14 09:39:37',NULL),(38,27,1,3888,3887,51,24,'betting',0,'2020-10-14 09:39:37',NULL),(39,27,1,3887,3886,51,25,'betting',0,'2020-10-14 09:39:37',NULL),(40,27,1,3886,3885,51,26,'betting',0,'2020-10-14 09:39:37',NULL),(41,27,1,3885,3884,51,27,'betting',0,'2020-10-14 09:39:37',NULL),(42,27,1,3884,3883,51,28,'betting',0,'2020-10-14 10:15:04',NULL),(43,27,1,3883,3882,51,29,'betting',0,'2020-10-14 10:15:04',NULL),(44,27,1,3882,3881,51,30,'betting',0,'2020-10-14 10:15:04',NULL),(45,27,1,3881,3880,51,31,'betting',0,'2020-10-14 10:15:04',NULL),(46,27,1,3880,3879,51,32,'betting',0,'2020-10-14 10:15:04',NULL),(47,27,1,3879,3878,51,33,'betting',0,'2020-10-14 10:15:04',NULL),(48,27,1,3878,3877,51,34,'betting',0,'2020-10-14 10:17:00',NULL),(49,27,1,3877,3876,51,35,'betting',0,'2020-10-14 10:17:00',NULL),(50,27,1,3876,3875,51,36,'betting',0,'2020-10-14 10:17:00',NULL),(51,27,1,3875,3874,51,37,'betting',0,'2020-10-14 10:17:00',NULL),(52,27,1,3874,3873,51,38,'betting',0,'2020-10-14 10:17:00',NULL),(53,27,1,3873,3872,51,39,'betting',0,'2020-10-14 10:17:00',NULL),(54,27,1,3872,3871,51,40,'betting',0,'2020-10-14 10:40:27',NULL),(55,27,1,3871,3870,51,41,'betting',0,'2020-10-14 10:40:27',NULL),(56,27,1,3870,3869,51,42,'betting',0,'2020-10-14 10:40:27',NULL),(57,27,1,3869,3868,51,43,'betting',0,'2020-10-14 10:40:27',NULL),(58,27,1,3868,3867,51,44,'betting',0,'2020-10-14 10:40:27',NULL),(59,27,1,3867,3866,51,45,'betting',0,'2020-10-14 10:40:27',NULL),(60,27,1,3866,3865,51,46,'betting',0,'2020-10-18 18:44:59',NULL),(61,27,1,3865,3864,51,47,'betting',0,'2020-10-18 18:44:59',NULL),(62,27,1,3864,3863,51,48,'betting',0,'2020-10-18 18:44:59',NULL),(63,27,1,3863,3862,51,49,'betting',0,'2020-10-18 18:44:59',NULL),(64,27,1,3862,3861,51,50,'betting',0,'2020-10-18 18:49:56',NULL),(65,27,1,3861,3860,51,51,'betting',0,'2020-10-18 18:49:56',NULL),(66,27,1,3860,3859,51,52,'betting',0,'2020-10-18 18:49:56',NULL),(67,27,1,3859,3858,51,53,'betting',0,'2020-10-18 18:49:56',NULL);
/*!40000 ALTER TABLE `user_account_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_bank_card`
--

DROP TABLE IF EXISTS `user_bank_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_bank_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_num` varchar(30) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '�û�ID',
  `bank_branch` varchar(100) DEFAULT NULL COMMENT '֧��',
  `bank_code` varchar(10) DEFAULT NULL COMMENT '���д���',
  `state` int(11) DEFAULT NULL COMMENT '��״̬:0,��Ч;1,��Ч',
  `creator` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_bank_card`
--

LOCK TABLES `user_bank_card` WRITE;
/*!40000 ALTER TABLE `user_bank_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_bank_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(30) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL COMMENT '�û�����',
  `user_id` varchar(50) DEFAULT NULL,
  `login_pwd` varchar(256) DEFAULT NULL COMMENT '��¼����',
  `fund_pwd` varchar(256) DEFAULT NULL COMMENT '֧������',
  `state` int(11) DEFAULT NULL COMMENT '�û�״̬:0,����;1,����;2,����',
  `level` int(11) DEFAULT NULL COMMENT '�û�����,��Ӧ�û��������',
  `fail_login_count` int(11) DEFAULT NULL COMMENT '��½����',
  `login_count` int(11) DEFAULT NULL COMMENT '��½����',
  `unlock_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `user_type` int(11) DEFAULT NULL COMMENT '�û�����:0,ƽ̨�ͻ�;1,����;2,ϵͳ�û�',
  `superior` varchar(200) DEFAULT NULL,
  `rebate` decimal(19,2) DEFAULT NULL COMMENT '����',
  `plat_rebate` decimal(8,2) DEFAULT NULL COMMENT 'ƽ̨��������ߴ�����¼��������ҵĵ�',
  `phone_num` varchar(30) DEFAULT NULL COMMENT '�ֻ���',
  `qq` varchar(30) DEFAULT NULL,
  `wechat` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `is_valid_phone` int(11) DEFAULT NULL COMMENT '�Ƿ���֤',
  `is_valid_email` int(11) DEFAULT NULL COMMENT '�Ƿ���֤',
  `reg_ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  `create_time` datetime DEFAULT NULL,
  `creator` int(11) DEFAULT NULL COMMENT '������ Ҳ���Ǵ���',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,'总代','general_agent',NULL,NULL,NULL,0,NULL,0,NULL,'2020-07-03 00:27:16',3,'0',0.00,14.60,NULL,'','',NULL,NULL,NULL,'127.0.0.1','2020-07-04 01:24:24',NULL),(2,NULL,'admin',NULL,'$2a$10$VRk0yxKUJ1Eiq7R5X3aoNect19TBwG2bK5gC53X6wU.gyh0TOUUl6','111111',0,1,0,NULL,'2020-07-08 11:27:44',2,'1',0.00,14.60,NULL,NULL,NULL,NULL,NULL,NULL,'127.0.0.1','2020-06-25 08:46:33',NULL),(3,'张三','xy_agent_001','','$2a$10$YjwKzIE9Z4HVn9dy9ZX/z.dSclmeI6Az66TiiHw7zAzln0feK3cdS','$2a$10$BUfisfSy0Hm0MhGt/IV.eehGBb8uJGLU8kqOMrXtrnQBw6YsuWUAe',0,0,NULL,0,NULL,8,'1',0.50,14.10,'','','','',0,0,'127.0.0.1','2020-07-03 09:19:03',1),(7,'李八','xy_agent_001_01','','$2a$10$iRJfs/EijIXSakit2QMPm.BdRRafT4ZTMfkx5293AhWP7PBLzTYuG','$2a$10$9bKEV6A6xxwVFL2e8MJooeQ2lNehQN8KuzWE91X9d7SNYmgZAL7Zy',0,0,NULL,0,NULL,8,'3,1',0.00,14.10,'','','','',0,0,'127.0.0.1','2020-07-04 01:48:26',1),(8,'信誉玩家','xy_agent_001_02','','$2a$10$PCUzaAzwjiVtt.xsLYOZ9.fPChU0Ylq7dn5PPIxu1gGkx410ejmay','$2a$10$9bE3kq2PJVoYYm3QVfJmS.8uGeZbzDNAEpGna.X4h./1xht3oSUiK',0,0,NULL,0,NULL,7,'3,1',0.30,13.80,'','','','',0,0,'127.0.0.1','2020-07-08 12:53:41',1),(9,'在线代理','online_agent_001','','$2a$10$i81B9myXv.ERj5aDZzXZoO/OUBSdPUnrWyzGdP1ifBCEQTEf1fmCW','$2a$10$VqVbY36Cuk5M5KFxzF7hpOwt4uBe8RZUIrLI/5cGjLd6HuZz56VO.',0,0,NULL,0,NULL,1,'1',4.87,9.73,'','','','',0,0,'127.0.0.1','2020-07-09 12:39:27',1),(10,'','sm_agent_001','','$2a$10$FNWpF86CbxjfVaFvyNFcv.uIv1CdGXEzyJFihxSEiXRNKyk/2XqZe','$2a$10$4fo5A82qyG6XsgL6yU74ceVzCfWqX72JSqC/fdZHWX70/qt.W/QoG',0,0,NULL,0,NULL,6,'1',9.73,4.87,'','','','',0,0,'127.0.0.1','2020-07-09 13:11:00',1),(11,'','xy_agent_002','','$2a$10$6IX4FhHFHp9l/YNk5IugV.aIsjBIsFmqVuNpoeJZOR45Q9jFJU1Ui','$2a$10$Tl3F3h4cSZlINEW3anCUjuU/NNJbb7NkDAk2sgbowe.P7cmcWV8w2',0,0,0,0,'2020-07-10 14:00:04',8,'1',0.80,13.80,'','','','',0,0,'127.0.0.1','2020-07-09 13:11:41',1),(12,'','entrust_agent_001','','$2a$10$n088td0iJe9hP/Pye4CCheylADl8.DStzRsQsuxE7EcGx8NIrrkaa','$2a$10$6/8QiqTrKwN082qjedD7FunupxN4RDmxXxod.nXkxaPcnFeMeS.PC',0,0,NULL,0,NULL,10,'1',9.73,4.87,'','','','',0,0,'127.0.0.1','2020-07-09 13:12:32',1),(13,'','xy_agent_001_03','','$2a$10$/MayuunepamSLo1gTTbW1eqdwnuva4/IMISGM5cXlJw6cH0WzJ6KG','$2a$10$uTXdN1hAiB3KOb0esxpepeOEF1/1nDAqO0xI/nWfJjLFkMc9mR7NK',0,0,NULL,0,NULL,7,'3,1',0.30,13.80,'','','','',0,0,'127.0.0.1','2020-07-09 13:16:16',1),(14,'','cash_agent_001_01','','$2a$10$VL8Ih3/cYwVjYjNDpj/ONedI28X6qA6IVRRv2N.z53piYJUPpmQJe','$2a$10$9zwFHhvqtkA3qYQwzfdkjO/u9j7RgxbRF6lUnRY5Exy7Fnccs8hPK',0,0,NULL,0,NULL,0,'9,1',4.86,4.87,'','','','',0,0,'127.0.0.1','2020-07-09 13:18:22',1),(15,'','sm_agent_001_01','','$2a$10$WIqAu7Ayj3DrXJbSl6By8Oe8mQTIgFwzoOwRlV53VXuRZ99OhhX7u','$2a$10$7cxivzre9r1pQcJVVl53heSl7BbZqCZXPWwfPTre2iwb.8lpLTKI2',0,0,NULL,0,NULL,5,'10,1',2.44,2.43,'','','','',0,0,'127.0.0.1','2020-07-09 13:19:47',1),(16,'','xy_agent_001_04','','$2a$10$x564ydggJ9m.Pb9H8/gbtuCJtz2ZgkL.tvjjesjlFUpmuIpDebR86','$2a$10$MZf9ZvlW08qjNST0tXOQfOQBd89YESD0y.l0XO.XmvcuRJdowZeWK',0,0,NULL,0,NULL,7,'7,3,1',0.30,13.80,'','','','',0,0,'127.0.0.1','2020-07-10 12:44:22',1),(17,'','xy_agent_001_05','','$2a$10$TshVi1xBl9LiGFibAW19jO/koGDFpnw4uvDmJAos3DnvRt5.PvZlG','$2a$10$TgYhCQ35M3jMeuZG1REPw.YbrhF2jWvawOUmQPMx3gRbHU1BO9Yty',0,0,NULL,0,NULL,7,'3,1',1.10,13.00,'','','','',0,0,'127.0.0.1','2020-07-10 13:34:36',1),(18,'','xy_agent_001_06','','$2a$10$i5hXVrEvVImuzyY5UQU1P.HI3sLkjTe902o8bVUqaIroIit069tnq','$2a$10$QGC5NXAD8QrX.nxzWMfNlecLwDGzJX84aAWYo3pCB.qGY26XXwbeq',0,0,NULL,0,NULL,7,'3,1',1.10,13.00,'','','','',0,0,'127.0.0.1','2020-07-10 13:36:50',1),(19,'','xy_agent_001_07','','$2a$10$uk53VwgruJe56VJqMcjQ3uuJu2z8bK0aeAwSmCpn/7qrLTeiEngpC','$2a$10$0VhMxnqo1EIrfeU.fCC4OeIBYbVnVCoJh1z3zFwMK1zpafcdGSE7e',0,0,NULL,0,NULL,8,'3,1',0.30,13.80,'','','','',0,0,'127.0.0.1','2020-07-10 13:37:58',1),(20,'','xy_agent_001_10','','$2a$10$0EGWE4jL66hRN8gdW4gAVuGq.yIB90fdR3Kc.gp0QO0sb8E.JD2b2','$2a$10$0AMnQ7xKRQqoy1mlCWT2OeU1c7rWoDy89Ex7XRidVA1sgPWvm2kDu',0,0,NULL,0,NULL,7,'3,1',0.30,13.80,'','','','',0,0,'127.0.0.1','2020-07-10 13:46:56',1),(21,'','xy_agent_001_12','','$2a$10$SRaWOr8vivDxrsjL7C/X3OT79tMGRxWZ4Io06sUfWYM1St2bgICFC','$2a$10$yH3PIrJOfrpXI5h/FMYYRu1xYVEArKMJ3Ckw4RQoKfmIbOcjGPz9y',0,0,NULL,0,NULL,8,'3,1',0.30,13.80,'','','','',0,0,'127.0.0.1','2020-07-10 13:48:44',1),(22,'','xy_agent_006','','$2a$10$MjwBd/yKD8HG/5tZCWous.alvpl1crK/m94nbOZOlkR1UwhaFhAKa','$2a$10$Fu9uKEJuf.Cl46SFW.xgtuR22Tq0iPhbyi2Wztl/gtaQq7GhJ1.sK',0,0,0,0,'2020-08-01 11:22:51',8,'1',0.50,14.10,'','','','',0,0,'127.0.0.1','2020-07-31 11:51:01',1),(23,'','xy_agent_007','','$2a$10$/59yk2Nj0gNLQ3tIXW3XGO7A7zNN2BQw3fNHMUhnmszI4PzJv/ygC','$2a$10$hKNXf3Rkb3HgyAPw9BBeYO41E58kVSNyrF3CmvJONHPClccpaU5DW',0,0,NULL,0,NULL,8,'1',0.50,14.10,'','','','',0,0,'127.0.0.1','2020-08-01 11:32:46',1),(24,'','xy_agent_008','','$2a$10$DMF/MhpNHpWKM2B1ws.jnek6yGGErS0qhfe4PJx6.lSXoAhr4xL7S','$2a$10$5BnMa9x0QRCG4LapvZYwMeBPMhNOwCINBTISP6q28rfXBXwa43ISy',0,0,NULL,0,NULL,8,'1',0.50,14.10,'','','','',0,0,'127.0.0.1','2020-08-01 12:12:04',1),(25,'','xy_agent_009','','$2a$10$VAEPqPq8oV6pxGiJ/qqyMevxwHCYOu1AmgEpAok3kaxLmulAif0gK','$2a$10$gyVsxogJmQZnO6bY69Mv8.6rCaMTFEhLz.gzK4QOAjbk5MqnqZFvG',0,0,0,0,'2020-08-02 16:20:24',8,'1',0.50,14.10,'','','','',0,0,'127.0.0.1','2020-08-02 14:43:38',1),(26,'','xy_agent_009_001','','$2a$10$ZoVIYXOaB/HX9xfGxKZZw.V5193UDfzHLRFuLNonPKl.xg0A5afXe','$2a$10$40FBI/bbp6Q9EsFwwgXV.eswHQeP4abcjrNiO8fmb64EGj6C2uh.q',0,0,0,0,'2020-08-02 16:23:18',7,'25,1',0.00,14.10,'','','','',0,0,'127.0.0.1','2020-08-02 15:36:47',1),(27,'','xy_uer_001','','$2a$10$TWZzFR7rpTyM9zZUHjzmPOI5ZmVNuTRiSWpt6ZX5UhdBZtaZK8AAa','$2a$10$aUNw0d9EaAU9ZJLql/2wAumj7wiPGKqo44B5/BE/WYck0UwEpcArS',0,0,NULL,0,NULL,7,'3,1',0.00,14.10,'','','','',0,0,'127.0.0.1','2020-09-19 10:09:07',1);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info_ext`
--

DROP TABLE IF EXISTS `user_info_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `ext_field_name` varchar(255) DEFAULT NULL,
  `ext_field_val` varchar(255) DEFAULT NULL COMMENT '�Ƿ��������:0,�Ǵ�������;1,��������',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info_ext`
--

LOCK TABLES `user_info_ext` WRITE;
/*!40000 ALTER TABLE `user_info_ext` DISABLE KEYS */;
INSERT INTO `user_info_ext` VALUES (1,3,'panKou','1','2020-07-03 09:19:03'),(2,3,'xyAmount','100000.0','2020-07-03 09:19:03'),(3,4,'panKou','1','2020-07-04 01:26:02'),(4,4,'xyAmount','100000.0','2020-07-04 01:26:02'),(5,5,'panKou','1','2020-07-04 01:28:57'),(6,5,'xyAmount','10000.0','2020-07-04 01:28:57'),(7,6,'panKou','1','2020-07-04 01:47:16'),(8,6,'xyAmount','10000.0','2020-07-04 01:47:16'),(9,7,'panKou','1','2020-07-04 01:48:26'),(10,7,'xyAmount','10000.0','2020-07-04 01:48:26'),(11,8,'panKou','3','2020-07-08 12:53:41'),(12,8,'xyAmount','1000.0','2020-07-08 12:53:41'),(13,11,'panKou','3','2020-07-09 13:11:41'),(14,11,'xyAmount','10000.0','2020-07-09 13:11:41'),(15,13,'panKou','3','2020-07-09 13:16:16'),(16,13,'xyAmount','10000.0','2020-07-09 13:16:16'),(17,16,'panKou','3','2020-07-10 12:44:22'),(18,16,'xyAmount','2000.0','2020-07-10 12:44:22'),(19,17,'panKou','2','2020-07-10 13:34:36'),(20,17,'xyAmount','10000.0','2020-07-10 13:34:36'),(21,18,'panKou','2','2020-07-10 13:36:50'),(22,18,'xyAmount','10000.0','2020-07-10 13:36:50'),(23,19,'panKou','3','2020-07-10 13:37:58'),(24,19,'xyAmount','10000.0','2020-07-10 13:37:58'),(25,20,'panKou','3','2020-07-10 13:46:56'),(26,20,'xyAmount','3000.0','2020-07-10 13:46:56'),(27,21,'panKou','3','2020-07-10 13:48:44'),(28,21,'xyAmount','10000.0','2020-07-10 13:48:44'),(29,22,'panKou','1','2020-07-31 11:51:01'),(30,22,'xyAmount','10000.0','2020-07-31 11:51:01'),(31,22,'zcAmount','0.40000000000000000832667268468867405317723751068115234375000','2020-07-31 11:51:01'),(32,22,'tsAmount','0.10000000000000000208166817117216851329430937767028808593750','2020-07-31 11:51:01'),(33,1,'zcAmount','1','2020-08-01 10:24:11'),(34,1,'tsAmount','1','2020-08-01 10:24:22'),(35,23,'panKou','1','2020-08-01 11:32:47'),(36,23,'xyAmount','100000.0','2020-08-01 11:32:47'),(37,23,'zcAmount','2','2020-08-01 11:32:47'),(38,23,'tsAmount','1','2020-08-01 11:32:47'),(39,24,'panKou','1','2020-08-01 12:12:04'),(40,24,'xyAmount','10000.0','2020-08-01 12:12:04'),(41,24,'zcAmount','3','2020-08-01 12:12:04'),(42,24,'tsAmount','1','2020-08-01 12:12:04'),(43,25,'panKou','1','2020-08-02 14:43:38'),(44,25,'xyAmount','10000.0','2020-08-02 14:43:38'),(45,25,'zcAmount','0.30000000000000000624500451351650553988292813301086425781250','2020-08-02 14:43:38'),(46,25,'tsAmount','0.10000000000000000208166817117216851329430937767028808593750','2020-08-02 14:43:38'),(47,26,'panKou','1','2020-08-02 15:36:47'),(48,26,'xyAmount','10000.0','2020-08-02 15:36:47'),(49,26,'zcAmount','0.15000000000000000312250225675825276994146406650543212890625','2020-08-02 15:36:47'),(50,26,'tsAmount','0.09000000000000000187350135405495166196487843990325927734375','2020-08-02 15:36:47'),(51,27,'panKou','1','2020-09-19 10:09:07'),(52,27,'xyAmount','10000.0','2020-09-19 10:09:07'),(53,27,'zcAmount','0.1','2020-09-19 10:09:07'),(54,27,'tsAmount','0.1','2020-09-19 10:09:07'),(55,27,'usedCreditAmount','0.00',NULL),(56,1,'xyAmount','10000000000','2020-10-06 10:47:08');
/*!40000 ALTER TABLE `user_info_ext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_push_config`
--

DROP TABLE IF EXISTS `user_push_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_push_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lottery_type` varchar(30) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '�û�����',
  `play_type` int(11) DEFAULT NULL,
  `play_type_name` varchar(256) DEFAULT NULL COMMENT '��¼����',
  `push_times` int(11) DEFAULT NULL COMMENT '֧������',
  `push_amount` double DEFAULT NULL COMMENT '�û�״̬:0,����;1,����;2,����',
  `push_numbers` int(11) DEFAULT NULL COMMENT '�û�����,��Ӧ�û��������',
  `state` int(11) DEFAULT NULL COMMENT '��½����',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_push_config`
--

LOCK TABLES `user_push_config` WRITE;
/*!40000 ALTER TABLE `user_push_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_push_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_settlement`
--

DROP TABLE IF EXISTS `user_settlement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_settlement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL COMMENT 'Ȩ��',
  `credit_limit` double DEFAULT NULL COMMENT '0:��Ч;1:��Ч',
  `available_credit` double DEFAULT NULL COMMENT '0:��Ч;1:��Ч',
  `state` int(11) DEFAULT NULL COMMENT '0:��Ч;1:��Ч',
  `set_date` date DEFAULT NULL COMMENT '������ID',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_settlement`
--

LOCK TABLES `user_settlement` WRITE;
/*!40000 ALTER TABLE `user_settlement` DISABLE KEYS */;
INSERT INTO `user_settlement` VALUES (1,3,'xy_agent_001',100000,5002,0,'2020-07-02','2020-07-03 09:20:00'),(2,4,'xy_agent_002',100000,0,0,'2020-07-03','2020-07-04 01:30:00'),(3,5,'xy_agent_003',10000,0,0,'2020-07-03','2020-07-04 01:30:00'),(4,6,'xy_agent_004',10000,0,0,'2020-07-03','2020-07-04 08:48:20'),(5,7,'xy_agent_001_01',10000,0,0,'2020-07-03','2020-07-04 08:48:21'),(6,11,'xy_agent_002',10000,1,0,'2020-07-08','2020-07-09 13:15:00'),(7,19,'xy_agent_001_07',10000,0,0,'2020-07-09','2020-07-10 13:40:00'),(8,21,'xy_agent_001_12',10000,0,0,'2020-07-09','2020-07-10 13:50:00'),(9,22,'xy_agent_006',10000,0,0,'2020-07-30','2020-07-31 11:55:00'),(10,23,'xy_agent_007',100000,0,0,'2020-07-31','2020-08-01 11:35:00'),(11,24,'xy_agent_008',10000,0,0,'2020-07-31','2020-08-01 12:15:00'),(12,25,'xy_agent_009',10000,0,0,'2020-08-01','2020-08-02 14:45:00');
/*!40000 ALTER TABLE `user_settlement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `v_transfer_application`
--

DROP TABLE IF EXISTS `v_transfer_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `v_transfer_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_num` varchar(50) DEFAULT NULL,
  `source_user` int(11) DEFAULT NULL COMMENT '�û�id',
  `source_user_name` varchar(255) DEFAULT NULL COMMENT '�û�id',
  `source_wallet_id` int(11) DEFAULT NULL COMMENT '�û�id',
  `source_wallet_name` varchar(255) DEFAULT NULL COMMENT '�û�id',
  `dst_user` int(11) DEFAULT NULL COMMENT '�û�id',
  `dst_user_name` varchar(255) DEFAULT NULL COMMENT '�û�id',
  `dst_wallet_id` int(11) DEFAULT NULL COMMENT '�û�id',
  `dst_wallet_name` varchar(255) DEFAULT NULL COMMENT '�û�id',
  `amount` double DEFAULT NULL COMMENT '���ֽ��',
  `state` int(11) DEFAULT NULL COMMENT '����״̬:0,�ȴ�����;1,����ɹ�,2,��˲�ͨ��;3,���й���;4,�˻���Ϣ����;5.others',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `creator` int(11) DEFAULT NULL COMMENT 'ƽ̨����Ա',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `v_transfer_application`
--

LOCK TABLES `v_transfer_application` WRITE;
/*!40000 ALTER TABLE `v_transfer_application` DISABLE KEYS */;
INSERT INTO `v_transfer_application` VALUES (1,'202007100100010414',1,NULL,27,NULL,11,NULL,17,NULL,1,1,'2020-07-10 11:45:17',NULL,2),(2,'202007100100025031',1,NULL,27,NULL,3,NULL,1,NULL,1,1,'2020-07-10 11:50:52',NULL,2),(3,'202007100100038288',1,NULL,27,NULL,3,NULL,1,NULL,1,1,'2020-07-10 11:53:52',NULL,2),(4,'202007100100040262',3,NULL,1,NULL,13,NULL,21,NULL,1,1,'2020-07-10 12:05:26',NULL,2),(5,'202007100100054546',1,NULL,27,NULL,3,NULL,1,NULL,1,1,'2020-07-10 13:46:22',NULL,2),(6,'202009190100073062',1,NULL,27,NULL,3,NULL,1,NULL,10000,1,'2020-09-19 10:12:42',NULL,2),(7,'202009190100086711',3,NULL,1,NULL,27,NULL,51,NULL,5000,1,'2020-09-19 10:12:56',NULL,2);
/*!40000 ALTER TABLE `v_transfer_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `withdraw_application`
--

DROP TABLE IF EXISTS `withdraw_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `withdraw_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_num` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '�û�id',
  `amount` float DEFAULT NULL COMMENT '���ֽ��',
  `wallet_id` int(11) DEFAULT NULL COMMENT '����Ǯ��',
  `bank_card_id` int(11) DEFAULT NULL COMMENT '�û����п�',
  `state` int(11) DEFAULT NULL COMMENT '����״̬:0,�ȴ�����;1,����ɹ�,2,��˲�ͨ��;3,���й���;4,�˻���Ϣ����;5.others',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `operator` int(11) DEFAULT NULL COMMENT 'ƽ̨����Ա',
  `remark` varchar(200) DEFAULT NULL COMMENT '��ע',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `withdraw_application`
--

LOCK TABLES `withdraw_application` WRITE;
/*!40000 ALTER TABLE `withdraw_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `withdraw_application` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-21 11:57:04
