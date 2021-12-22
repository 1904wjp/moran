/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2021-12-22 14:43:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `email` varchar(25) DEFAULT NULL COMMENT '邮件地址',
  `id_code` varchar(25) DEFAULT NULL COMMENT '身份号码',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `person_desc` varchar(255) DEFAULT NULL COMMENT '个人简历',
  `person_name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `sex` int(1) DEFAULT NULL COMMENT '性别',
  `user_type_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `file_url` varchar(100) DEFAULT NULL COMMENT '头像地址',
  `password` varchar(12) DEFAULT NULL COMMENT '登录密码',
  `secondar_password` varchar(12) DEFAULT NULL COMMENT '二级密码',
  `status` varchar(5) DEFAULT NULL COMMENT '账号状态【0.未激活；1.激活；10000：冻结】',
  `status_code` varchar(100) DEFAULT NULL COMMENT '激活码',
  `username` varchar(10) DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, null, '0', null, '2021-12-22 14:18:43', null, null, '2239122645@qq.com', null, null, null, null, '18375391922', '1', '1', '/static/1/img/photo/20211219/72974f4076aa9d756ed095f0ab_ali.gif', '123456', null, '1', null, 'xdr');
INSERT INTO `user` VALUES ('2', null, '2021-11-22 11:54:36', '0', null, '2021-12-22 13:25:07', null, null, '1122121', null, null, null, null, '13905673635', null, null, '/static/img/userImg/file0default0name.png', 'sj123', null, '1', null, 'sj');
INSERT INTO `user` VALUES ('3', null, '2021-12-02 14:14:09', '0', null, null, null, null, '770098752@qq.com', null, null, null, null, '15209839616', null, null, '/static/img/userImg/file0default0name.png', 'admin123', null, '1', null, 'sunwukong');
INSERT INTO `user` VALUES ('5', null, '2021-12-17 14:01:34', '0', null, '2021-12-17 14:03:02', null, null, '2239122644@qq.com', null, null, null, null, '18375391921', null, null, '/static/img/photo/20211217/970d42fd1f3544858646efe855ab1450_20211217020259_ali.gif', 'dm123', null, '1', null, 'dm');
