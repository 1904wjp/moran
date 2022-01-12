/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2022-01-12 11:24:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for db_base_setting
-- ----------------------------
DROP TABLE IF EXISTS `db_base_setting`;
CREATE TABLE `db_base_setting` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `url` varchar(255) DEFAULT NULL COMMENT '数据库url',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '数据库密码',
  `project_address` varchar(255) DEFAULT NULL COMMENT '项目地址',
  `package_path_id` bigint(32) DEFAULT NULL COMMENT '类包，对应表package_path',
  `temp_code` varchar(255) DEFAULT NULL COMMENT '临时包',
  `database_type` varchar(64) DEFAULT NULL COMMENT '数据源类型',
  `user_id` bigint(32) NOT NULL COMMENT '当前登录人id',
  `data_source_name` varchar(255) DEFAULT NULL COMMENT '数据源名称',
  `apply_status` int(3) DEFAULT NULL COMMENT '应用状态：1代表应用',
  `driver_name` varchar(255) DEFAULT NULL COMMENT '数据库驱动名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='数据库配置';

-- ----------------------------
-- Records of db_base_setting
-- ----------------------------
INSERT INTO `db_base_setting` VALUES ('4', 'xdr', '2021-12-17 11:41:02', '0', 'xdr', '2021-12-22 14:23:29', 'jdbc:mysql://112.123.228.65:3306', 'wrkj', 'Wrkj1234?!', null, null, '智慧社区项目数据源', 'mysql', '1', '112.123.228.65', '1', 'com.mysql.cj.jdbc.Driver');
INSERT INTO `db_base_setting` VALUES ('5', 'sunwukong', '2021-12-17 15:20:57', '0', 'sunwukong', '2021-12-17 15:25:08', 'jdbc:mysql://112.123.228.65:3306', 'wrkj', 'Wrkj1234?!', null, null, '小区', 'mysql', '3', '112.123.228.65', '1', 'com.mysql.cj.jdbc.Driver');
INSERT INTO `db_base_setting` VALUES ('6', 'sj', '2021-12-24 15:23:28', '0', 'sj', '2021-12-24 15:23:43', 'jdbc:mysql://112.123.228.65:3306', 'wrkj', 'Wrkj1234?!', null, null, 'xiaoxiaioixao', 'mysql', '2', 'user', '1', 'com.mysql.cj.jdbc.Driver');
