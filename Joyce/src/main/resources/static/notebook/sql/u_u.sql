/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2021-12-22 14:43:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for u_u
-- ----------------------------
DROP TABLE IF EXISTS `u_u`;
CREATE TABLE `u_u` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `usera_id` bigint(32) DEFAULT NULL COMMENT '用户id',
  `userb_id` int(32) DEFAULT NULL COMMENT '用户id',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体基础类';

-- ----------------------------
-- Records of u_u
-- ----------------------------
