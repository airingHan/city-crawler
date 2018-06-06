/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : sys

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-06 14:58:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crawler_job
-- ----------------------------
DROP TABLE IF EXISTS `crawler_job`;
CREATE TABLE `crawler_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `state` bit(1) DEFAULT b'0' COMMENT '0为已经为未完的  1为已经完的',
  `type` int(11) DEFAULT NULL COMMENT '1为省级 2为地级 3为县级 4为乡级 5为村级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46181 DEFAULT CHARSET=utf8mb4 COMMENT='任务';

-- ----------------------------
-- Table structure for sys_city
-- ----------------------------
DROP TABLE IF EXISTS `sys_city`;
CREATE TABLE `sys_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL COMMENT '行政代码',
  `name` varchar(15) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `province_code` int(11) NOT NULL COMMENT '省级行政代码',
  `state` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态1 启用  0 未启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `city_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=345 DEFAULT CHARSET=utf8mb4 COMMENT='地级';

-- ----------------------------
-- Table structure for sys_county
-- ----------------------------
DROP TABLE IF EXISTS `sys_county`;
CREATE TABLE `sys_county` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL COMMENT '行政代码',
  `name` varchar(15) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `city_code` int(11) NOT NULL COMMENT '地级行政代码',
  `state` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态1 启用  0 未启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `county_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3137 DEFAULT CHARSET=utf8mb4 COMMENT='县级';

-- ----------------------------
-- Table structure for sys_province
-- ----------------------------
DROP TABLE IF EXISTS `sys_province`;
CREATE TABLE `sys_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL COMMENT '省行政代码',
  `name` varchar(15) NOT NULL COMMENT '名称',
  `state` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态1 启用  0 未启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `province` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='省级';

-- ----------------------------
-- Table structure for sys_town
-- ----------------------------
DROP TABLE IF EXISTS `sys_town`;
CREATE TABLE `sys_town` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL COMMENT '行政代码',
  `name` varchar(30) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `county_code` int(11) NOT NULL COMMENT '县级行政代码',
  `state` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态1 启用  0 未启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `town_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=42951 DEFAULT CHARSET=utf8mb4 COMMENT='乡级';

-- ----------------------------
-- Table structure for sys_village
-- ----------------------------
DROP TABLE IF EXISTS `sys_village`;
CREATE TABLE `sys_village` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` bigint(20) NOT NULL COMMENT '行政代码',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `town_code` int(11) NOT NULL COMMENT '乡级行政代码',
  `state` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态1 启用  0 未启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `village_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=667910 DEFAULT CHARSET=utf8mb4 COMMENT='村级';
