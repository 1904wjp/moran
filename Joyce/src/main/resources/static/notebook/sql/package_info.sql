/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2022-01-12 11:24:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for package_info
-- ----------------------------
DROP TABLE IF EXISTS `package_info`;
CREATE TABLE `package_info` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `package_name` varchar(255) DEFAULT NULL COMMENT '包名',
  `package_value` varchar(255) DEFAULT NULL COMMENT '包值',
  `user_id` bigint(32) NOT NULL COMMENT '用户id，表user',
  `apply_status` int(3) DEFAULT NULL COMMENT '应用标志',
  `web_entity_id` bigint(32) DEFAULT NULL COMMENT 'web实体类id，表web_entity',
  `project_address` varchar(255) DEFAULT NULL COMMENT '项目地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='类包类';

-- ----------------------------
-- Records of package_info
-- ----------------------------
INSERT INTO `package_info` VALUES ('1', 'sj', '2021-12-14 14:18:44', '0', 'xdr', '2021-12-22 14:23:15', '测试', 'com.wr.aiot', '1', '1', null, null);
INSERT INTO `package_info` VALUES ('2', 'sj', '2021-12-14 14:42:38', '0', null, null, '现在v在·1', '的撒大', '2', '0', null, null);
INSERT INTO `package_info` VALUES ('3', 'sunwukong', '2021-12-15 17:08:25', '0', 'sunwukong', '2021-12-17 15:19:53', 'wan', 'com.wr.system', '3', '1', null, null);
INSERT INTO `package_info` VALUES ('4', 'sunwukong', '2021-12-16 18:07:36', '0', 'sunwukong', null, '小区', 'com.wr.aiot', '3', '0', null, null);
INSERT INTO `package_info` VALUES ('5', 'sj', '2021-12-24 15:25:12', '0', 'sj', '2021-12-24 15:25:20', 'liubei', 'com.wr.property', '2', '1', null, null);
