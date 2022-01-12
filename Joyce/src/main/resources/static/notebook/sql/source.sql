/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2022-01-12 11:24:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for source
-- ----------------------------
DROP TABLE IF EXISTS `source`;
CREATE TABLE `source` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `source_name` varchar(64) DEFAULT NULL COMMENT '资源名称',
  `type` varchar(3) DEFAULT NULL COMMENT '类型[0.图片；1.音频；2.视频]',
  `url` varchar(255) DEFAULT NULL COMMENT '资源连接',
  `sort` int(16) DEFAULT NULL COMMENT '排序',
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='资源类';

-- ----------------------------
-- Records of source
-- ----------------------------
INSERT INTO `source` VALUES ('1', 'xdr', '2021-12-19 19:00:06', '0', null, null, '大大撒', null, '/static/1/img/photo/20211219/d155154abea795d2b05b792b75_f.jpeg', '1', '1');
INSERT INTO `source` VALUES ('2', 'xdr', '2021-12-19 19:07:23', '0', null, null, '16516', null, '/static/1/img/photo/20211219/e001d34b2e95ba4e0db8d4ed66_du.jpg', '2', '1');
INSERT INTO `source` VALUES ('3', 'xdr', '2021-12-19 19:10:33', '0', null, null, 'xiaomimi', null, '/static/1/img/photo/20211219/61af1347b883cb05fdb7d8c1df_du.jpg', '5', '1');
INSERT INTO `source` VALUES ('4', 'xdr', '2021-12-19 19:11:29', '0', null, null, 'dasd', null, '/static/1/img/photo/20211219/509f3b45a88f972cf58fca19a9_du.jpg', '1', '1');
INSERT INTO `source` VALUES ('5', 'xdr', '2021-12-19 19:11:46', '0', null, null, 'sfea', null, '/static/1/img/photo/20211219/b1eb4c4044a543c8c33d78181b_un.jpg', '7', '1');
INSERT INTO `source` VALUES ('6', 'xdr', '2021-12-19 19:12:06', '0', null, null, 'sfea', null, '/static/1/img/photo/20211219/c83ad94b2ea346ec47dfd60614_lb.jpg', '7', '1');
INSERT INTO `source` VALUES ('7', 'xdr', '2021-12-19 19:18:09', '0', null, null, '是德国VS地方·1·', null, '/static/1/img/photo/20211219/2e2d6f4a6687f8e198e1c36108_lb.jpg', '3', '1');
INSERT INTO `source` VALUES ('8', 'xdr', '2021-12-19 19:18:22', '0', null, null, '是德国VS地方·1·y', null, '/static/1/img/photo/20211219/90b63f40bdb92234d65f949ff0_9g.jpg', '30', '1');
INSERT INTO `source` VALUES ('9', 'xdr', '2021-12-19 19:18:37', '0', null, null, '是德国VS地方·1·ywqeq', null, '/static/1/img/photo/20211219/31182245eaaca5b31210197a0c_ky.jpg', '30', '1');
INSERT INTO `source` VALUES ('10', 'xdr', '2021-12-19 19:18:46', '0', null, null, '是德国dsad', null, '/static/1/img/photo/20211219/e98fa544e7b9a906eb352db40a_tu.jpg', '30', '1');
INSERT INTO `source` VALUES ('11', 'xdr', '2021-12-19 19:18:56', '0', null, null, '是德国dsadfgff', null, '/static/1/img/photo/20211219/c1cb1d4aa2965c07660db94e90_tu.jpg', '30', '1');
INSERT INTO `source` VALUES ('12', 'xdr', '2021-12-19 19:19:20', '0', null, null, '是德国dsadfgfffaf', null, '/static/1/img/photo/20211219/283c604e69b5623c35474fe7d7_ji.jpg', '30', '1');
INSERT INTO `source` VALUES ('13', 'xdr', '2021-12-19 19:19:45', '0', null, null, '是德国dsadfgfffafddd', null, '/static/1/img/photo/20211219/716e6f4284ab9b559b95edc9bc_mg.jpg', '30', '1');
INSERT INTO `source` VALUES ('14', 'xdr', '2021-12-19 19:19:55', '0', null, null, 'ssd', null, '/static/1/img/photo/20211219/d94f9a4917a0504bf0a7da8d61_mg.jpg', '30', '1');
INSERT INTO `source` VALUES ('15', 'xdr', '2021-12-19 19:20:04', '0', null, null, 'ssdafad', null, '/static/1/img/photo/20211219/3e8d124e138e9a1e0e7a9107ae_me.jpg', '30', '1');
INSERT INTO `source` VALUES ('16', 'xdr', '2021-12-19 19:20:13', '0', null, null, 'ssdafadfdsafda', null, '/static/1/img/photo/20211219/2f92804472af3ab7f1b8af7c3f_qq.jpg', '30', '1');
INSERT INTO `source` VALUES ('17', 'xdr', '2021-12-19 19:20:22', '0', null, null, 'ssdad', null, '/static/1/img/photo/20211219/9b7ff6485aa78735db3f761e26_mg.jpg', '30', '1');
INSERT INTO `source` VALUES ('18', 'xdr', '2021-12-19 19:20:33', '0', null, null, 'ssdadagdsdfa', null, '/static/1/img/photo/20211219/e0e8bc4b2aae4a84ecfaf829cf_73.jpg', '30', '1');
INSERT INTO `source` VALUES ('19', 'xdr', '2021-12-19 19:20:44', '0', null, null, 'dsadfada', null, '/static/1/img/photo/20211219/3b35aa4564a76da671c5aff2ec_00.jpg', '30', '1');
INSERT INTO `source` VALUES ('20', 'xdr', '2021-12-19 19:20:54', '0', null, null, 'dasf', null, '/static/1/img/photo/20211219/32472a443aa36531a73a86ab81_00.jpg', '30', '1');
INSERT INTO `source` VALUES ('21', 'xdr', '2021-12-19 19:21:01', '0', null, null, 'dasffasdas', null, '/static/1/img/photo/20211219/bf68c047a29fc9e836d0d08af1_ao.jpg', '30', '1');
INSERT INTO `source` VALUES ('22', 'xdr', '2021-12-21 16:46:54', '0', null, null, '你好', null, '/static/1/img/photo/20211221/03fe724f80a11ccf8453c5b426_1).png', '21', '1');
INSERT INTO `source` VALUES ('23', 'xdr', '2021-12-22 14:53:02', '0', null, null, '就让他r\'f\'h', null, '/static/1/img/photo/20211222/c69829432b9bdb02df1ff7f368_ao.jpg', '55', '1');
