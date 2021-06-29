-- MySQL dump 10.13  Distrib 5.6.28, for Win64 (x86_64)
--
-- Host: localhost    Database: lottery
-- ------------------------------------------------------
-- Server version	5.6.28

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_indx_issue` (`lottery_type`,`start_time`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `ts` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `brief_cla` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=345 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `bet_num_desc` varchar(150) DEFAULT NULL COMMENT '号码描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3787 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `stat_betting_number`
--

DROP TABLE IF EXISTS `stat_betting_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stat_betting_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lottery_type` varchar(50) DEFAULT NULL,
  `issue_id` varchar(5) DEFAULT NULL,
  `issue_num` varchar(5) DEFAULT NULL,
  `play_type_id` varchar(100) DEFAULT NULL,
  `play_type_name` varchar(100) NOT NULL,
  `bet_num` varchar(100) NOT NULL,
  `bet_amount` decimal(11,4) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=612 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `tbusersrate`
--

DROP TABLE IF EXISTS `tbusersrate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbusersrate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `ms17` double DEFAULT NULL COMMENT '�û�����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `ts_amount` decimal(19,4) DEFAULT NULL,
  `zc_amount` decimal(19,4) DEFAULT NULL,
  `used_credit_limit` decimal(19,4) DEFAULT NULL,
  `remain_credit_limit` decimal(19,4) DEFAULT NULL,
  `settlement_flag` tinyint(4) DEFAULT NULL,
  `settlement_amount` decimal(19,4) DEFAULT NULL,
  `lottery_type` varchar(255) DEFAULT NULL COMMENT '�û�����:0,ƽ̨�ͻ�;1,����;3,�ܴ�',
  `play_type` varchar(255) DEFAULT NULL COMMENT '�û�����:0,ƽ̨�ͻ�;1,����;3,�ܴ�',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_ts`
--

DROP TABLE IF EXISTS `user_ts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_ts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `lottery_type` varchar(30) DEFAULT NULL,
  `play_type_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `a_ts` decimal(19,2) DEFAULT NULL COMMENT '����ʱ��',
  `b_ts` decimal(19,2) DEFAULT NULL COMMENT '����ʱ��',
  `c_ts` decimal(19,2) DEFAULT NULL COMMENT '����ʱ��',
  `d_ts` decimal(19,2) DEFAULT NULL COMMENT '����ʱ��',
  `play_type_brief` varchar(19) DEFAULT NULL COMMENT '����ʱ��',
  `single_bet_limit_amount` decimal(19,2) DEFAULT NULL COMMENT '����ʱ��',
  `total_bet_limit_amount` decimal(19,2) DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-29  9:11:40
