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


DROP FUNCTION IF EXISTS isUserEnabled;

DELIMITER //

CREATE FUNCTION isUserEnabled(state int, unlock_time datetime)
  RETURNS INTEGER

  BEGIN
    DECLARE isEnabled int default 0;
    IF state = 0 and unlock_time < now() THEN  
      set @isEnabled = 1;
    ELSEIF state = 0 and unlock_time is null THEN
      set @isEnabled = 1;
    ELSE
      set @isEnabled = 0;
    END IF;
    RETURN @isEnabled;
  END //

DELIMITER ; 


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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_sequence`
--

LOCK TABLES `gen_sequence` WRITE;
/*!40000 ALTER TABLE `gen_sequence` DISABLE KEYS */;
INSERT INTO `gen_sequence` VALUES (1,'seq_num_bjpk10',10000,'2020-06-25');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_black_list`
--

LOCK TABLES `ip_black_list` WRITE;
/*!40000 ALTER TABLE `ip_black_list` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=52674 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `play_type`
--

LOCK TABLES `play_type` WRITE;
/*!40000 ALTER TABLE `play_type` DISABLE KEYS */;
INSERT INTO `play_type` VALUES (1,'gd11x5','sm|三码/qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 09:16:39'),(2,'gd11x5','sm|三码/qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(3,'gd11x5','sm|三码/qszux|前三组选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(4,'gd11x5','sm|三码/qszux|前三组选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(5,'gd11x5','em|二码/qezx|前二直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(6,'gd11x5','em|二码/qezx|前二直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(7,'gd11x5','em|二码/qezux|前二组选','fs','复式',1,1,0,7,'2020-06-29 10:50:10'),(8,'gd11x5','em|二码/qezux|前二组选','ds','单式',1,0,0,8,'2020-06-29 10:50:10'),(9,'gd11x5','bdw|不定位','fs','复式',1,1,0,9,'2020-06-29 10:50:10'),(10,'gd11x5','dwd|定位胆','fs','复式',1,1,0,10,'2020-06-29 10:50:10'),(11,'gd11x5','qwx|趣味型/qwdds|趣味定单双','fs','复式',1,1,0,11,'2020-06-29 10:50:10'),(12,'gd11x5','qwx|趣味型/qwczw|趣味猜中位','fs','复式',1,1,0,12,'2020-06-29 10:50:10'),(13,'gd11x5','rx|任选/rxyzy|任选一中一','fs','复式',1,1,0,13,'2020-06-29 10:50:10'),(14,'gd11x5','rx|任选/rxeze|任选二中二','fs','复式',1,1,0,14,'2020-06-29 10:50:10'),(15,'gd11x5','rx|任选/rxszs|任选三中三','fs','复式',1,1,0,15,'2020-06-29 10:50:10'),(16,'gd11x5','rx|任选/rxszs|任选四中四','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(17,'gd11x5','rx|任选/rxwzw|任选五中五','fs','复式',1,1,0,17,'2020-06-29 10:50:10'),(18,'gd11x5','rx|任选/rxlzw|任选六中五','fs','复式',1,1,0,18,'2020-06-29 10:50:10'),(19,'gd11x5','rx|任选/rxqzw|任选七中五','fs','复式',1,1,0,19,'2020-06-29 10:50:10'),(20,'gd11x5','rx|任选/rxbzw|任选八中五','fs','复式',1,1,0,20,'2020-06-29 10:50:10'),(21,'gd11x5','rx|任选/rxyzy|任选一中一','ds','单式',1,0,0,21,'2020-06-29 10:50:10'),(22,'gd11x5','rx|任选/rxeze|任选二中二','ds','单式',1,0,0,22,'2020-06-29 10:50:10'),(23,'gd11x5','rx|任选/rxszs|任选三中三','ds','单式',1,0,0,23,'2020-06-29 10:50:10'),(24,'gd11x5','rx|任选/rxszs|任选四中四','ds','单式',1,0,0,24,'2020-06-29 10:50:10'),(25,'gd11x5','rx|任选/rxwzw|任选五中五','ds','单式',1,0,0,25,'2020-06-29 10:50:10'),(26,'gd11x5','rx|任选/rxlzw|任选六中五','ds','单式',1,0,0,26,'2020-06-29 10:50:10'),(27,'gd11x5','rx|任选/rxqzw|任选七中五','ds','单式',1,0,0,27,'2020-06-29 10:50:10'),(28,'gd11x5','rx|任选/rxbzw|任选八中五','ds','单式',1,0,0,28,'2020-06-29 10:50:10'),(29,'sfc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:50:10'),(30,'sfc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(31,'sfc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(32,'sfc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(33,'sfc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(34,'sfc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(35,'sfc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:50:10'),(36,'sfc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:50:10'),(37,'sfc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:50:10'),(38,'sfc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:50:10'),(39,'sfc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:50:10'),(40,'sfc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:50:10'),(41,'sfc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:50:10'),(42,'sfc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:50:10'),(43,'sfc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:50:10'),(44,'sfc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(45,'sfc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:50:10'),(46,'sfc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:50:10'),(47,'sfc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:50:10'),(48,'sfc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:50:10'),(49,'sfc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:50:10'),(50,'sfc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:50:10'),(51,'sfc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:50:10'),(52,'sfc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:50:10'),(53,'sfc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:50:10'),(54,'5fc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:50:10'),(55,'5fc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(56,'5fc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(57,'5fc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(58,'5fc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(59,'5fc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(60,'5fc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:50:10'),(61,'5fc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:50:10'),(62,'5fc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:50:10'),(63,'5fc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:50:10'),(64,'5fc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:50:10'),(65,'5fc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:50:10'),(66,'5fc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:50:10'),(67,'5fc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:50:10'),(68,'5fc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:50:10'),(69,'5fc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(70,'5fc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:50:10'),(71,'5fc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:50:10'),(72,'5fc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:50:10'),(73,'5fc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:50:10'),(74,'5fc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:50:10'),(75,'5fc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:50:10'),(76,'5fc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:50:10'),(77,'5fc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:50:10'),(78,'5fc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:50:10'),(79,'ffc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:50:10'),(80,'ffc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(81,'ffc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(82,'ffc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(83,'ffc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(84,'ffc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(85,'ffc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:50:10'),(86,'ffc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:50:10'),(87,'ffc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:50:10'),(88,'ffc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:50:10'),(89,'ffc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:50:10'),(90,'ffc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:50:10'),(91,'ffc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:50:10'),(92,'ffc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:50:10'),(93,'ffc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:50:10'),(94,'ffc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(95,'ffc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:50:10'),(96,'ffc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:50:10'),(97,'ffc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:50:10'),(98,'ffc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:50:10'),(99,'ffc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:50:10'),(100,'ffc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:50:10'),(101,'ffc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:50:10'),(102,'ffc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:50:10'),(103,'ffc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:50:10'),(104,'mmc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:50:10'),(105,'mmc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:50:10'),(106,'mmc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:50:10'),(107,'mmc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:50:10'),(108,'mmc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:50:10'),(109,'mmc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:50:10'),(110,'mmc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:50:10'),(111,'mmc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:50:10'),(112,'mmc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:50:10'),(113,'mmc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:50:10'),(114,'mmc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:50:10'),(115,'mmc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:50:10'),(116,'mmc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:50:10'),(117,'mmc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:50:10'),(118,'mmc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:50:10'),(119,'mmc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:50:10'),(120,'mmc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:50:11'),(121,'mmc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:50:11'),(122,'mmc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:50:11'),(123,'mmc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:50:11'),(124,'mmc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:50:11'),(125,'mmc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:50:11'),(126,'mmc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:50:11'),(127,'mmc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:50:11'),(128,'mmc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:50:11'),(129,'bjpk10','qyfs|前一复式','fs','复式',1,1,0,1,'2020-06-29 10:50:11'),(130,'bjpk10','qefs|前二复式','fs','复式',1,1,0,2,'2020-06-29 10:50:11'),(131,'bjpk10','qsfs|前三复式','fs','复式',1,1,0,3,'2020-06-29 10:50:11'),(132,'bjpk10','dwd|定位胆/qs|前十','fs','复式',1,1,0,4,'2020-06-29 10:50:11'),(133,'bjpk10','lh|龙虎/1v10|1v10','fs','复式',1,1,0,5,'2020-06-29 10:50:11'),(134,'bjpk10','lh|龙虎/2v9|2v9','fs','复式',1,1,0,6,'2020-06-29 10:50:11'),(135,'bjpk10','lh|龙虎/3v8|3v8','fs','复式',1,1,0,7,'2020-06-29 10:50:11'),(136,'bjpk10','lh|龙虎/4v7|4v7','fs','复式',1,1,0,8,'2020-06-29 10:50:11'),(137,'bjpk10','lh|龙虎/5v6|5v6','fs','复式',1,1,0,9,'2020-06-29 10:50:11'),(138,'bjpk10','dx|大小/dym|第一名','fs','复式',1,1,0,10,'2020-06-29 10:50:11'),(139,'bjpk10','dx|大小/dem|第二名','fs','复式',1,1,0,11,'2020-06-29 10:50:11'),(140,'bjpk10','dx|大小/dsm|第三名','fs','复式',1,1,0,12,'2020-06-29 10:50:11'),(141,'bjpk10','dx|大小/dsim|第四名','fs','复式',1,1,0,13,'2020-06-29 10:50:11'),(142,'bjpk10','dx|大小/dwm|第五名','fs','复式',1,1,0,14,'2020-06-29 10:50:11'),(143,'bjpk10','dx|大小/dlm|第六名','fs','复式',1,1,0,15,'2020-06-29 10:50:11'),(144,'bjpk10','dx|大小/dqm|第七名','fs','复式',1,1,0,16,'2020-06-29 10:50:11'),(145,'bjpk10','dx|大小/dbm|第八名','fs','复式',1,1,0,17,'2020-06-29 10:50:11'),(146,'bjpk10','dx|大小/djm|第九名','fs','复式',1,1,0,18,'2020-06-29 10:50:11'),(147,'bjpk10','dx|大小/dshim|第十名','fs','复式',1,1,0,19,'2020-06-29 10:50:11'),(148,'bjpk10','ds|单双/dym|第一名','fs','复式',1,1,0,20,'2020-06-29 10:50:11'),(149,'bjpk10','ds|单双/dem|第二名','fs','复式',1,1,0,21,'2020-06-29 10:50:11'),(150,'bjpk10','ds|单双/dsm|第三名','fs','复式',1,1,0,22,'2020-06-29 10:50:11'),(151,'bjpk10','ds|单双/dsim|第四名','fs','复式',1,1,0,23,'2020-06-29 10:50:11'),(152,'bjpk10','ds|单双/dwm|第五名','fs','复式',1,1,0,24,'2020-06-29 10:50:11'),(153,'bjpk10','ds|单双/dlm|第六名','fs','复式',1,1,0,25,'2020-06-29 10:50:11'),(154,'bjpk10','ds|单双/dqm|第七名','fs','复式',1,1,0,26,'2020-06-29 10:50:11'),(155,'bjpk10','ds|单双/dbm|第八名','fs','复式',1,1,0,27,'2020-06-29 10:50:11'),(156,'bjpk10','ds|单双/djm|第九名','fs','复式',1,1,0,28,'2020-06-29 10:50:11'),(157,'bjpk10','ds|单双/dshim|第十名','fs','复式',1,1,0,29,'2020-06-29 10:50:11'),(158,'xyft','qyfs|前一复式','fs','复式',1,1,0,1,'2020-06-29 10:50:11'),(159,'xyft','qefs|前二复式','fs','复式',1,1,0,2,'2020-06-29 10:50:11'),(160,'xyft','qsfs|前三复式','fs','复式',1,1,0,3,'2020-06-29 10:50:11'),(161,'xyft','dwd|定位胆/qs|前十','fs','复式',1,1,0,4,'2020-06-29 10:50:11'),(162,'xyft','lh|龙虎/1v10|1v10','fs','复式',1,1,0,5,'2020-06-29 10:50:11'),(163,'xyft','lh|龙虎/2v9|2v9','fs','复式',1,1,0,6,'2020-06-29 10:50:11'),(164,'xyft','lh|龙虎/3v8|3v8','fs','复式',1,1,0,7,'2020-06-29 10:50:11'),(165,'xyft','lh|龙虎/4v7|4v7','fs','复式',1,1,0,8,'2020-06-29 10:50:11'),(166,'xyft','lh|龙虎/5v6|5v6','fs','复式',1,1,0,9,'2020-06-29 10:50:11'),(167,'xyft','dx|大小/dym|第一名','fs','复式',1,1,0,10,'2020-06-29 10:50:11'),(168,'xyft','dx|大小/dem|第二名','fs','复式',1,1,0,11,'2020-06-29 10:50:11'),(169,'xyft','dx|大小/dsm|第三名','fs','复式',1,1,0,12,'2020-06-29 10:50:11'),(170,'xyft','dx|大小/dsim|第四名','fs','复式',1,1,0,13,'2020-06-29 10:50:11'),(171,'xyft','dx|大小/dwm|第五名','fs','复式',1,1,0,14,'2020-06-29 10:50:11'),(172,'xyft','dx|大小/dlm|第六名','fs','复式',1,1,0,15,'2020-06-29 10:50:11'),(173,'xyft','dx|大小/dqm|第七名','fs','复式',1,1,0,16,'2020-06-29 10:50:11'),(174,'xyft','dx|大小/dbm|第八名','fs','复式',1,1,0,17,'2020-06-29 10:50:11'),(175,'xyft','dx|大小/djm|第九名','fs','复式',1,1,0,18,'2020-06-29 10:50:11'),(176,'xyft','dx|大小/dshim|第十名','fs','复式',1,1,0,19,'2020-06-29 10:50:11'),(177,'xyft','ds|单双/dym|第一名','fs','复式',1,1,0,20,'2020-06-29 10:50:11'),(178,'xyft','ds|单双/dem|第二名','fs','复式',1,1,0,21,'2020-06-29 10:50:11'),(179,'xyft','ds|单双/dsm|第三名','fs','复式',1,1,0,22,'2020-06-29 10:50:11'),(180,'xyft','ds|单双/dsim|第四名','fs','复式',1,1,0,23,'2020-06-29 10:50:11'),(181,'xyft','ds|单双/dwm|第五名','fs','复式',1,1,0,24,'2020-06-29 10:50:11'),(182,'xyft','ds|单双/dlm|第六名','fs','复式',1,1,0,25,'2020-06-29 10:50:11'),(183,'xyft','ds|单双/dqm|第七名','fs','复式',1,1,0,26,'2020-06-29 10:50:11'),(184,'xyft','ds|单双/dbm|第八名','fs','复式',1,1,0,27,'2020-06-29 10:50:11'),(185,'xyft','ds|单双/djm|第九名','fs','复式',1,1,0,28,'2020-06-29 10:50:11'),(186,'xyft','ds|单双/dshim|第十名','fs','复式',1,1,0,29,'2020-06-29 10:50:11'),(187,'cqssc','qszx|前三直选','fs','复式',1,1,0,1,'2020-06-29 10:55:17'),(188,'cqssc','qszx|前三直选','ds','单式',1,0,0,2,'2020-06-29 10:55:17'),(189,'cqssc','zszx|中三直选','fs','复式',1,1,0,3,'2020-06-29 10:55:17'),(190,'cqssc','zszx|中三直选','ds','单式',1,0,0,4,'2020-06-29 10:55:17'),(191,'cqssc','hszx|后三直选','fs','复式',1,1,0,5,'2020-06-29 10:55:17'),(192,'cqssc','hszx|后三直选','ds','单式',1,0,0,6,'2020-06-29 10:55:17'),(193,'cqssc','qszux|前三组选','zsfs','组三复式',1,1,0,7,'2020-06-29 10:55:17'),(194,'cqssc','qszux|前三组选','zlfs','组六复式',1,1,0,8,'2020-06-29 10:55:17'),(195,'cqssc','qszux|前三组选','hhzxds','混合组选(单式)',1,0,0,9,'2020-06-29 10:55:17'),(196,'cqssc','zszux|中三组选','zsfs','组三复式',1,1,0,10,'2020-06-29 10:55:17'),(197,'cqssc','zszux|中三组选','zlfs','组六复式',1,1,0,11,'2020-06-29 10:55:17'),(198,'cqssc','zszux|中三组选','hhzxds','混合组选(单式)',1,0,0,12,'2020-06-29 10:55:17'),(199,'cqssc','hszux|后三组选','zsfs','组三复式',1,1,0,13,'2020-06-29 10:55:17'),(200,'cqssc','hszux|后三组选','zlfs','组六复式',1,1,0,14,'2020-06-29 10:55:17'),(201,'cqssc','hszux|后三组选','hhzxds','混合组选(单式)',1,0,0,15,'2020-06-29 10:55:17'),(202,'cqssc','wxq2|五星前二','fs','复式',1,1,0,16,'2020-06-29 10:55:17'),(203,'cqssc','wxq2|五星前二','ds','单式',1,0,0,17,'2020-06-29 10:55:17'),(204,'cqssc','wxq2|五星前二','zxfs','组选(复式)',1,1,0,18,'2020-06-29 10:55:17'),(205,'cqssc','bdw|不定位','qsbdwfs','前三不定位(复式)',1,1,0,19,'2020-06-29 10:55:17'),(206,'cqssc','bdw|不定位','zsbdwfs','中三不定位(复式)',1,1,0,20,'2020-06-29 10:55:17'),(207,'cqssc','bdw|不定位','hsbdwfs','后三不定位(复式)',1,1,0,21,'2020-06-29 10:55:17'),(208,'cqssc','dwd|定位胆','dwdfs','定位胆(复式)',1,1,0,22,'2020-06-29 10:55:17'),(209,'cqssc','wxh2|五星后二','fs','复式',1,1,0,23,'2020-06-29 10:55:17'),(210,'cqssc','wxh2|五星后二','ds','单式',1,0,0,24,'2020-06-29 10:55:17'),(211,'cqssc','wxh2|五星后二','zxfs','组选(复式)',1,0,0,25,'2020-06-29 10:55:17');
/*!40000 ALTER TABLE `play_type` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_authority`
--

LOCK TABLES `sys_authority` WRITE;
/*!40000 ALTER TABLE `sys_authority` DISABLE KEYS */;
INSERT INTO `sys_authority` VALUES (2,2,1),(3,2,3),(4,2,4),(5,2,5),(6,2,6),(7,2,7),(8,2,8),(9,2,9),(10,3,8),(11,4,8),(12,5,8),(13,6,8),(14,7,8);
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
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_code`
--

LOCK TABLES `sys_code` WRITE;
/*!40000 ALTER TABLE `sys_code` DISABLE KEYS */;
INSERT INTO `sys_code` VALUES (1,NULL,1,'lottery_type','lottery_type',NULL,1,'彩种类型'),(2,1,1,'cqssc','cqssc',1,1,'重庆时时彩'),(3,1,0,'bjpk10','bjpk10',2,1,'北京PK10'),(4,1,0,'gd11x5','gd11x5',3,1,'广东11选5'),(5,1,0,'5fc','5fc',4,1,'5分彩'),(6,1,0,'sfc','sfc',5,1,'双分彩'),(7,1,0,'ffc','ffc',6,1,'分分彩'),(8,1,0,'mmc','mmc',7,1,'秒秒彩'),(9,1,0,'xyft','xyft',8,1,'幸运飞艇'),(10,1,0,'xjssc','xjssc',9,1,'新疆时时彩'),(12,NULL,1,'lottery_config_cqssc','lottery_config_cqssc',NULL,1,'重庆时时彩属性'),(13,NULL,1,'lottery_config_gd11x5','lottery_config_gd11x5',NULL,1,'广东11选5属性'),(14,NULL,1,'lottery_config_txffc','lottery_config_txffc',NULL,1,'腾讯分分彩属性'),(15,NULL,1,'lottery_config_5fc','lottery_config_5fc',NULL,1,'5分彩属性'),(16,NULL,1,'lottery_config_sfc','lottery_config_sfc',NULL,1,'双分彩属性'),(17,NULL,1,'lottery_config_ffc','lottery_config_ffc',NULL,1,'分分彩属性'),(18,NULL,1,'lottery_config_mmc','lottery_config_mmc',NULL,1,'秒秒彩属性'),(19,NULL,1,'lottery_config_bjpk10','lottery_config_bjpk10',NULL,1,'PK10属性'),(20,NULL,1,'lottery_config_xyft','lottery_config_xyft',NULL,1,'幸运飞艇属性'),(21,NULL,1,'lottery_config_xjssc','lottery_config_xjssc',NULL,1,'新疆时时彩'),(22,NULL,1,'lottery_config_yfb','lottery_config_yfb',NULL,1,'1.5分彩'),(23,1,0,'yfb','yfb',10,1,'1.5分彩'),(24,12,0,'dt_setting','0',1,1,'单挑设置'),(25,12,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(26,12,0,'betting_end_time','45',3,1,'截止投注时间'),(27,12,0,'bet_times','85',4,1,'投注倍数'),(28,12,0,'prize_mode','1',5,1,'开奖模式'),(29,12,0,'url_wining_number_extenal','https://shishicai.cjcp.com.cn/chongqing/kaijiang/',6,1,'数据接口'),(30,12,0,'wining_rate','0.0005',7,1,'中奖率'),(31,15,0,'dt_setting','0',1,1,'单挑设置'),(32,15,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(33,15,0,'betting_end_time','45',3,1,'截止投注时间'),(34,15,0,'bet_times','85',4,1,'投注倍数'),(35,15,0,'prize_mode','1',5,1,'开奖模式'),(36,15,0,'url_wining_number_extenal','',7,1,'数据接口'),(37,15,0,'wining_rate','0.0005',8,1,'中奖率'),(38,16,0,'dt_setting','0',1,1,'单挑设置'),(39,16,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(40,16,0,'betting_end_time','45',3,1,'截止投注时间'),(41,16,0,'bet_times','85',4,1,'投注倍数'),(42,16,0,'prize_mode','1',5,1,'开奖模式'),(43,16,0,'url_wining_number_extenal','',6,1,'数据接口'),(44,16,0,'wining_rate','0.0005',7,1,'中奖率'),(45,17,0,'dt_setting','0',1,1,'单挑设置'),(46,17,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(47,17,0,'betting_end_time','45',3,1,'截止投注时间'),(48,17,0,'bet_times','85',4,1,'投注倍数'),(49,17,0,'prize_mode','1',5,1,'开奖模式'),(50,17,0,'url_wining_number_extenal','',6,1,'数据接口'),(51,17,0,'wining_rate','0.0005',7,1,'中奖率'),(52,18,0,'dt_setting','0',1,1,'单挑设置'),(53,18,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(54,18,0,'betting_end_time','45',3,1,'截止投注时间'),(55,18,0,'bet_times','85',4,1,'投注倍数'),(56,18,0,'prize_mode','1',5,1,'开奖模式'),(57,18,0,'url_wining_number_extenal','',6,1,'数据接口'),(58,18,0,'wining_rate','0.0005',7,1,'中奖率'),(59,20,0,'dt_setting','0',1,1,'单挑设置'),(60,20,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(61,20,0,'betting_end_time','45',3,1,'截止投注时间'),(62,20,0,'bet_times','85',4,1,'投注倍数'),(63,20,0,'prize_mode','1',5,1,'开奖模式'),(64,20,0,'url_wining_number_extenal','',6,1,'数据接口'),(65,20,0,'wining_rate','0.0005',7,1,'中奖率'),(66,21,0,'dt_setting','0',1,1,'单挑设置'),(67,21,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(68,21,0,'betting_end_time','45',3,1,'截止投注时间'),(69,21,0,'bet_times','85',4,1,'投注倍数'),(70,21,0,'prize_mode','1',5,1,'开奖模式'),(71,21,0,'url_wining_number_extenal','',6,1,'数据接口'),(72,21,0,'wining_rate','0.0005',7,1,'中奖率'),(73,22,0,'dt_setting','0',1,1,'单挑设置'),(74,22,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(75,22,0,'betting_end_time','45',3,1,'截止投注时间'),(76,22,0,'bet_times','85',4,1,'投注倍数'),(77,22,0,'prize_mode','1',5,1,'开奖模式'),(78,22,0,'url_wining_number_extenal','',6,1,'数据接口'),(79,22,0,'wining_rate','0.0005',7,1,'中奖率'),(80,NULL,1,'sys_runtime_argument','sys_runtime_argument',NULL,1,'系统运行时参数'),(81,80,0,'notify_msg_valid_day','7',1,1,'系统通知有效时间'),(82,80,0,'site_msg_valid_day','14',2,1,'站内信有效时间'),(83,80,0,'number_of_bank_cards','5',3,1,'单个用户银行卡数量'),(84,80,0,'lotto_prize_rate','850,1000',4,1,'彩种赔率范围'),(85,80,0,'point_exchange_scale','1',5,1,'积分兑换比例(1代表1:1,0.1代表10:1,兑换红包金额)'),(86,80,0,'locking_time','5',6,1,'锁定时间(只能是整数分钟)'),(87,80,0,'fail_login_count','5',7,1,'失败登录次数'),(88,NULL,1,'bank_code_list','bank_code_list',NULL,1,'银行代码'),(89,88,0,'CDB','国家开发银行',NULL,1,'国家开发银行'),(90,88,0,'GDB','广东发展银行',NULL,1,'广东发展银行'),(91,88,0,'COMM','交通银行',NULL,1,'交通银行'),(92,88,0,'CMB','招商银行',NULL,1,'招商银行'),(93,88,0,'SPABANK','平安银行',NULL,1,'平安银行'),(94,88,0,'CEB','中国光大银行',NULL,1,'中国光大银行'),(95,88,0,'CMBC','中国民生银行',NULL,1,'中国民生银行'),(96,88,0,'ABC','中国农业银行',NULL,1,'中国农业银行'),(97,88,0,'BOC','中国银行',NULL,1,'中国银行'),(98,88,0,'CIB','兴业银行',NULL,1,'兴业银行'),(99,88,0,'PSBC','中国邮政储蓄银行',NULL,1,'中国邮政储蓄银行'),(100,88,0,'PSBC','中国邮政储蓄银行',NULL,1,'中国邮政储蓄银行'),(101,88,0,'SPDB','上海浦东发展银行',NULL,1,'上海浦东发展银行'),(102,88,0,'CCB','中国建设银行',NULL,1,'中国建设银行'),(103,88,0,'ICBC','中国工商银行',NULL,1,'中国工商银行'),(104,NULL,1,'withdrawal_cfg','withdrawal_cfg',NULL,1,'提款设置'),(105,104,0,'red_packet_wallet_rate','10',1,1,'红包余额转主钱包的流水倍数'),(106,104,0,'max_withdrawal_amt','50000',2,1,'最高提款金额'),(107,104,0,'min_withdrawal_amt','100',3,1,'最低提款金额'),(108,104,0,'day_count','3',4,1,'每日可提款次数'),(109,NULL,1,'demo_user_cfg','demo_user_cfg',NULL,1,'试玩用户属性'),(110,109,0,'demo_user_name_format','shiwan%08d',1,1,'试玩会员用户名格式'),(111,109,0,'demo_user_bal','100000',2,1,'初始余额'),(112,109,0,'ip_max_reg_size','1',3,1,'每个IP每天最大注册的会员数'),(113,109,0,'demo_user_platrebate','13.8',4,1,'试玩会员的平台点数'),(114,NULL,1,'sm_pankou','sm_pankou',NULL,1,'双面盘口设置'),(115,114,0,'sm_pankou_a','14.1',1,1,'A盘口'),(116,114,0,'sm_pankou_c','13',2,1,'C盘口'),(117,114,0,'sm_pankou_b','13.8',3,1,'B盘口'),(118,NULL,1,'flow_type','flow_type',NULL,1,'流水类型'),(119,118,0,'plat_reward','plat_reward',1,1,'平台积分'),(120,118,0,'recovery_payout','recovery_payout',2,1,'派奖回收'),(121,118,0,'acc_freeze','acc_freeze',3,1,'账户冻结'),(122,118,0,'sys_add','sys_add',4,1,'系统加钱'),(123,118,0,'payout','payout',5,1,'系统派奖'),(124,118,0,'acc_unfreeze','acc_unfreeze',6,1,'账户解冻'),(125,118,0,'sys_deduction','sys_deduction',7,1,'系统扣款'),(126,118,0,'cancel_rebate','cancel_rebate',8,1,'取消返点'),(127,118,0,'promo_points','promo_points',9,1,'活动积分'),(128,118,0,'wd_unfreeze','wd_unfreeze',10,1,'提款解冻'),(129,118,0,'bank_fees','bank_fees',11,1,'银行手续费'),(130,118,0,'withdrawal_back','withdrawal_back',12,1,'提款退还'),(131,118,0,'rebate','rebate',13,1,'返点'),(132,118,0,'deposit_cash','deposit_cash',14,1,'充值礼金'),(133,118,0,'betting','betting',15,1,'投注'),(134,118,0,'wd_freeze','wd_freeze',16,1,'提款冻结'),(135,118,0,'transfer','transfer',17,1,'转账'),(136,118,0,'promo_cash','promo_cash',18,1,'活动礼金'),(137,118,0,'deposit','deposit',19,1,'充值'),(138,118,0,'user_red_envelope_withdrawal_deduction','user_red_envelope_withdrawal_deduction',20,1,'用户红包提现扣除'),(139,118,0,'customer_claims','customer_claims',20,1,'平台奖励'),(140,118,0,'withdraw','withdraw',21,1,'提款'),(141,118,0,'user_red_envelope_withdrawal_deduction','user_red_envelope_withdrawal_deduction',22,1,'用户红包提现扣除'),(142,NULL,1,'payment_platform','payment_platform',NULL,1,'支付平台'),(143,142,0,'self_pay','self_pay',1,1,'平台自有支付'),(144,NULL,1,'lucky_draw','lucky_draw',NULL,1,'幸运抽奖设置'),(145,144,0,'winning_probability','0.02',1,1,'中奖概率'),(146,144,0,'minimum_recharge','100',2,1,'最小充值金额'),(147,144,0,'winning_range','3,5,7,9',3,1,'中奖范围'),(148,144,0,'minimum_amount_of_water','100000',4,1,'最小流水金额'),(149,NULL,1,'sign_in_day','sign_in_day',NULL,1,'签到活动'),(150,149,0,'sign_in_point_range','30,80,-100',1,1,'积分获取范围'),(151,NULL,1,'pay_type_class','pay_type_class',NULL,1,'充值方式类型'),(152,151,0,'online_banking','online_banking',1,1,'网银'),(153,151,0,'qr_code','qr_code',2,1,'二维码扫码'),(154,NULL,1,'testType','testType',NULL,1,'测试代码类型'),(155,1,0,'demo_lottery','demo_lottery',11,1,'测试彩种'),(156,118,0,'demo_flowtype','demo_flowtype',23,1,'测试流水类型'),(157,144,0,'demo_flowtype','demo_flowtype',5,1,'测试流水类型'),(158,13,0,'dt_setting','0',1,1,'单挑设置'),(159,13,0,'max_prize_amount','300000',2,1,'单个订单最大中奖金额'),(160,13,0,'betting_end_time','45',3,1,'截止投注时间'),(161,13,0,'bet_times','85',4,1,'投注倍数'),(162,13,0,'prize_mode','1',5,1,'开奖模式'),(163,13,0,'url_wining_number_extenal','http://api.api68.com/lottery/getLotteryListByHot.do',6,1,'数据接口'),(164,13,0,'wining_rate','0.0005',7,1,'中奖率');
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_login`
--

LOCK TABLES `sys_login` WRITE;
/*!40000 ALTER TABLE `sys_login` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_pl_report`
--

LOCK TABLES `team_pl_report` WRITE;
/*!40000 ALTER TABLE `team_pl_report` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (1,3,'主钱包',0,0,0,0,1,1,'主钱包'),(2,3,'红包钱包',0,0,0,0,2,1,'红包钱包'),(3,4,'主钱包',0,0,0,0,1,1,'主钱包'),(4,4,'红包钱包',0,0,0,0,2,1,'红包钱包'),(5,5,'主钱包',0,0,0,0,1,1,'主钱包'),(6,5,'红包钱包',0,0,0,0,2,1,'红包钱包'),(7,6,'主钱包',0,0,0,0,1,1,'主钱包'),(8,6,'红包钱包',0,0,0,0,2,1,'红包钱包'),(9,7,'主钱包',0,0,0,0,1,1,'主钱包'),(10,7,'红包钱包',0,0,0,0,2,1,'红包钱包');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_details`
--

LOCK TABLES `user_account_details` WRITE;
/*!40000 ALTER TABLE `user_account_details` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,'总代','general_agent',NULL,NULL,NULL,0,NULL,0,NULL,'2020-07-03 00:27:16',3,'0',0.00,14.60,NULL,'','',NULL,NULL,NULL,NULL,'2020-07-04 01:24:24',NULL),(2,NULL,'admin',NULL,'$2a$10$1vgqUMbDnlP74MZaKhMRTOjJ4VKTgwUscIbkTvgTRDD9N0c35aY/y','111111',0,1,NULL,NULL,NULL,2,'1',0.00,14.60,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2020-06-25 08:46:33',NULL),(3,'张三','xy_agent_001','','$2a$10$YjwKzIE9Z4HVn9dy9ZX/z.dSclmeI6Az66TiiHw7zAzln0feK3cdS','$2a$10$BUfisfSy0Hm0MhGt/IV.eehGBb8uJGLU8kqOMrXtrnQBw6YsuWUAe',0,0,NULL,0,NULL,8,'1',0.50,14.10,'','','','',0,0,'127.0.0.1','2020-07-03 09:19:03',1),(4,'李四','xy_agent_002','','$2a$10$7oOMmU4kmXszjX3b1TkCXeDDqwu4hR/iswHlIS4Z78V4o4soLfNqu','$2a$10$SjnOePDmFMTqwDu7KSJpweSDJMEtBdmyxucemInTbCZ71sN1WSLRe',0,0,NULL,0,NULL,8,'1',0.50,14.10,'','','','',0,0,'127.0.0.1','2020-07-04 01:26:01',1),(5,'李五','xy_agent_003','','$2a$10$qejTqox5.9idR8I77bLxF.Ux6N.9aJTOn8D7Ojsy3CZlnrsChoUua','$2a$10$26ir8RX9ArImafOqv2amPuAc6.HYNK85NMCpcNnwLcw27o2oqVr22',0,0,NULL,0,NULL,8,'1',0.50,14.10,'','','','',0,0,'127.0.0.1','2020-07-04 01:28:57',1),(6,'李六','xy_agent_004','','$2a$10$M3RlP4mmAZndmoygVssX2O3Nd4RLzWgVU5iGO8gIa6qwCx4dmgKyq','$2a$10$OaRe6jKrP7Ce3ie4ocS4R.8b/fFc.LInAGkCWWHtp/Qz39N/azMxe',0,0,NULL,0,NULL,8,'1',0.50,14.10,'','','','',0,0,'127.0.0.1','2020-07-04 01:47:16',1),(7,'李八','xy_agent_001_01','','$2a$10$iRJfs/EijIXSakit2QMPm.BdRRafT4ZTMfkx5293AhWP7PBLzTYuG','$2a$10$9bKEV6A6xxwVFL2e8MJooeQ2lNehQN8KuzWE91X9d7SNYmgZAL7Zy',0,0,NULL,0,NULL,8,'3,1',0.00,14.10,'','','','',0,0,'127.0.0.1','2020-07-04 01:48:26',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info_ext`
--

LOCK TABLES `user_info_ext` WRITE;
/*!40000 ALTER TABLE `user_info_ext` DISABLE KEYS */;
INSERT INTO `user_info_ext` VALUES (1,3,'panKou','1','2020-07-03 09:19:03'),(2,3,'xyAmount','100000.0','2020-07-03 09:19:03'),(3,4,'panKou','1','2020-07-04 01:26:02'),(4,4,'xyAmount','100000.0','2020-07-04 01:26:02'),(5,5,'panKou','1','2020-07-04 01:28:57'),(6,5,'xyAmount','10000.0','2020-07-04 01:28:57'),(7,6,'panKou','1','2020-07-04 01:47:16'),(8,6,'xyAmount','10000.0','2020-07-04 01:47:16'),(9,7,'panKou','1','2020-07-04 01:48:26'),(10,7,'xyAmount','10000.0','2020-07-04 01:48:26');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_settlement`
--

LOCK TABLES `user_settlement` WRITE;
/*!40000 ALTER TABLE `user_settlement` DISABLE KEYS */;
INSERT INTO `user_settlement` VALUES (1,3,'xy_agent_001',100000,0,0,'2020-07-02','2020-07-03 09:20:00'),(2,4,'xy_agent_002',100000,0,0,'2020-07-03','2020-07-04 01:30:00'),(3,5,'xy_agent_003',10000,0,0,'2020-07-03','2020-07-04 01:30:00'),(4,6,'xy_agent_004',10000,0,0,'2020-07-03','2020-07-04 08:48:20'),(5,7,'xy_agent_001_01',10000,0,0,'2020-07-03','2020-07-04 08:48:21');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `v_transfer_application`
--

LOCK TABLES `v_transfer_application` WRITE;
/*!40000 ALTER TABLE `v_transfer_application` DISABLE KEYS */;
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

-- Dump completed on 2020-07-04 10:02:31
