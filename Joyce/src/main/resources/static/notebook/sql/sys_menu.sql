/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2022-01-12 11:24:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '菜单地址',
  `menu_order` varchar(255) DEFAULT NULL COMMENT '菜单排序',
  `parent_id` bigint(32) DEFAULT NULL COMMENT '菜单父类id',
  `menu_type` varchar(2) DEFAULT NULL COMMENT '菜单类型',
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
