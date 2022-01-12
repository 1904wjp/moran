/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2022-01-12 11:24:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for web_entity
-- ----------------------------
DROP TABLE IF EXISTS `web_entity`;
CREATE TABLE `web_entity` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` varchar(64) DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` varchar(64) DEFAULT NULL COMMENT '更新时间',
  `web_name` varchar(255) DEFAULT NULL COMMENT 'web名称',
  `is_extends` int(1) DEFAULT '0' COMMENT '是否有继承类(0：没有；1：继承)',
  `package_id` int(32) DEFAULT NULL COMMENT 'web包id',
  `is_implements` int(11) DEFAULT NULL COMMENT '是否实现类(0：没有；1：有)',
  `implement_package_ids` varchar(255) DEFAULT NULL COMMENT '实现的父类id的包集合',
  `add_code` varchar(255) DEFAULT NULL COMMENT '添加代码',
  `add_package_id` varchar(255) DEFAULT NULL COMMENT '添加代码的包',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='web实体类';

-- ----------------------------
-- Records of web_entity
-- ----------------------------
