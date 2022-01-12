/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2022-01-12 11:24:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for day_task
-- ----------------------------
DROP TABLE IF EXISTS `day_task`;
CREATE TABLE `day_task` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `end_times` datetime DEFAULT NULL COMMENT '结束时间',
  `today_task` varchar(255) DEFAULT NULL COMMENT '今日任务',
  `finaly_task` varchar(255) DEFAULT NULL COMMENT '最终任务',
  `project_id` bigint(32) DEFAULT NULL COMMENT '项目id',
  `project_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='每日看板类';

-- ----------------------------
-- Records of day_task
-- ----------------------------
INSERT INTO `day_task` VALUES ('1', 'xdr', '2021-12-28 10:08:16', '0', 'xdr', '2021-12-28 11:37:00', '2021-12-29 00:00:00', '52', '3645', '1', '智慧社区', '1');
INSERT INTO `day_task` VALUES ('2', 'xdr', '2021-12-28 10:11:51', '0', 'xdr', '2021-12-28 11:36:51', '2021-12-29 00:00:00', '52', '3645', '1', '智慧社区', '1');
INSERT INTO `day_task` VALUES ('3', 'xdr', '2021-12-28 10:13:56', '0', 'xdr', '2021-12-28 11:39:16', '2021-12-30 00:00:00', '52', '3645', '1', '智慧社区', '1');
INSERT INTO `day_task` VALUES ('4', 'xdr', '2021-12-28 10:15:15', '0', 'xdr', '2021-12-28 11:21:44', '2021-12-24 00:00:00', '52', '3645', '1', '智慧社区', '1');
INSERT INTO `day_task` VALUES ('5', 'xdr', '2021-12-28 10:16:54', '0', 'xdr', '2021-12-28 11:18:30', '2021-12-29 00:00:00', '52', '3645', '1', '智慧社区', '1');
INSERT INTO `day_task` VALUES ('6', 'xdr', '2021-12-28 10:20:36', '0', 'xdr', '2021-12-28 11:21:36', '2022-01-21 00:00:00', '52', '3645', '1', '智慧社区', '1');
INSERT INTO `day_task` VALUES ('7', 'xdr', '2021-12-28 10:22:10', '0', 'xdr', '2021-12-28 11:21:19', '2021-12-31 00:00:00', '52', '3645', '1', '智慧社区', '1');
INSERT INTO `day_task` VALUES ('8', 'xdr', '2021-12-28 11:20:43', '0', null, null, '2021-12-31 00:00:00', '52', '3645', '1', '智慧社区', '1');
INSERT INTO `day_task` VALUES ('9', 'dm', '2021-12-29 10:31:57', '0', 'dm', '2021-12-29 13:26:58', '2021-12-24 00:00:00', '521', '3645', '1', '智慧社区', '5');
INSERT INTO `day_task` VALUES ('10', 'dm', '2021-12-29 13:23:06', '0', 'dm', '2021-12-29 13:26:48', null, '52', '3645', '1', '智慧社区', '5');
INSERT INTO `day_task` VALUES ('11', 'dm', '2021-12-29 13:23:17', '0', null, null, '2021-12-23 00:00:00', '52', '3645', '1', '智慧社区', '5');
INSERT INTO `day_task` VALUES ('12', null, null, null, null, null, '2021-08-17 00:00:00', '环境配置以及依若cloud框架的运行和前后端数据交互的学习', '环境配置以及依若cloud框架的运行和前后端数据交互的学习', '1', '', '1');
INSERT INTO `day_task` VALUES ('13', null, null, null, null, null, '2021-08-18 00:00:00', '环境配置以及依若cloud框架的运行和前后端数据交互的学习', '环境配置以及依若cloud框架的运行和前后端数据交互的学习', '1', '', '1');
INSERT INTO `day_task` VALUES ('14', null, null, null, null, null, '2021-08-19 00:00:00', '物品出门模块', '物品出门模块', '1', '', '1');
INSERT INTO `day_task` VALUES ('15', null, null, null, null, null, '2021-08-20 00:00:00', '物品出门模块', '物品出门模块', '1', '', '1');
INSERT INTO `day_task` VALUES ('16', null, null, null, null, null, '2021-08-23 00:00:00', '轮播图管理模块', '轮播图管理模块', '1', '', '1');
INSERT INTO `day_task` VALUES ('17', null, null, null, null, null, '2021-08-24 00:00:00', '轮播图管理模块', '轮播图管理模块', '1', '', '1');
INSERT INTO `day_task` VALUES ('18', null, null, null, null, null, '2021-08-28 00:00:00', '人员通行记录和访客通行模块', '人员通行记录和访客通行模块', '1', '', '1');
INSERT INTO `day_task` VALUES ('19', null, null, null, null, null, '2021-08-28 00:00:00', '人员通行记录和访客通行模块', '人员通行记录和访客通行模块', '1', '', '1');
INSERT INTO `day_task` VALUES ('20', null, null, null, null, null, '2021-09-12 00:00:00', '运营端(api：智慧小区：1.人员信息；2.道闸信息；3.楼栋信息)', '运营端(api：智慧小区：1.人员信息；2.道闸信息；3.楼栋信息)', '1', '', '1');
INSERT INTO `day_task` VALUES ('21', null, null, null, null, null, '2021-09-11 00:00:00', '1.公司管理；2.公司管理', '1.公司管理；2.公司管理', '1', '', '1');
INSERT INTO `day_task` VALUES ('22', null, null, null, null, null, '2021-09-11 00:00:00', '1.公司管理；2.公司管理', '1.公司管理；2.公司管理', '1', '', '1');
INSERT INTO `day_task` VALUES ('23', null, null, null, null, null, '2021-09-13 00:00:00', '修改bug', '修改bug', '1', '', '1');
INSERT INTO `day_task` VALUES ('24', null, null, null, null, null, '2021-09-14 00:00:00', '修改bug,页面修改', '修改bug,页面修改', '1', '', '1');
INSERT INTO `day_task` VALUES ('25', null, null, null, null, null, '2021-09-15 00:00:00', '中台任务', '中台任务', null, null, '1');
INSERT INTO `day_task` VALUES ('26', null, null, null, null, null, '2021-09-16 00:00:00', '修改bug', '修改bug', null, null, '1');
INSERT INTO `day_task` VALUES ('27', null, null, null, null, null, '2021-09-17 00:00:00', '修改中台bug', '修改中台bug', null, null, '1');
INSERT INTO `day_task` VALUES ('28', null, null, null, null, null, '2021-09-18 00:00:00', '修改中台bug', '修改中台bug', null, null, '1');
INSERT INTO `day_task` VALUES ('29', null, null, null, null, null, '2021-09-25 00:00:00', '中台模块和智慧社区的优化', '中台模块和智慧社区的优化', null, null, '1');
INSERT INTO `day_task` VALUES ('30', null, null, null, null, null, '2021-09-25 00:00:00', '中台模块', '中台模块', null, null, '1');
INSERT INTO `day_task` VALUES ('31', null, null, null, null, null, '2021-09-25 00:00:00', '中台模块', '中台模块', null, null, '1');
INSERT INTO `day_task` VALUES ('32', null, null, null, null, null, '2021-09-25 00:00:00', '中台模块', '中台模块', null, null, '1');
INSERT INTO `day_task` VALUES ('33', null, null, null, null, null, '2021-09-28 00:00:00', '中台模块运营端', '中台模块运营端', null, null, '1');
INSERT INTO `day_task` VALUES ('34', null, null, null, null, null, '2021-09-30 00:00:00', '中台模块运营端', '中台模块运营端', null, null, '1');
INSERT INTO `day_task` VALUES ('35', null, null, null, null, null, '2021-09-30 00:00:00', '中台模块bug修改', '中台模块bug修改', null, null, '1');
INSERT INTO `day_task` VALUES ('36', null, null, null, null, null, '2021-09-30 00:00:00', '中台模块添加', '中台模块添加', null, null, '1');
INSERT INTO `day_task` VALUES ('37', null, null, null, null, null, '2021-09-30 00:00:00', '中台模块添加', '中台模块bug修改', null, null, '1');
INSERT INTO `day_task` VALUES ('38', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('39', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('40', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('41', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('42', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('43', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('44', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('45', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('46', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('47', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('48', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('49', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('50', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('51', null, null, null, null, null, '2021-10-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('52', null, null, null, null, null, '2021-11-08 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('53', null, null, null, null, null, '2021-11-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('54', null, null, null, null, null, '2021-11-02 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('55', null, null, null, null, null, '2021-11-05 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('56', null, null, null, null, null, '2021-11-11 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('57', null, null, null, null, null, '2021-11-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('58', null, null, null, null, null, '2021-11-23 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('59', null, null, null, null, null, '2021-11-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('60', null, null, null, null, null, '2021-11-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('61', null, null, null, null, null, '2021-11-30 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('62', null, null, null, null, null, '2021-11-27 00:00:00', '中台模块优化', '中台模块优化', null, null, '1');
INSERT INTO `day_task` VALUES ('63', null, null, null, null, null, '2021-12-02 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('64', null, null, null, null, null, '2021-12-02 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('65', null, null, null, null, null, '2021-12-02 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('66', null, null, null, null, null, '2021-12-03 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('67', null, null, null, null, null, '2021-12-06 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('68', null, null, null, null, null, '2021-12-11 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('69', null, null, null, null, null, '2021-12-11 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('70', null, null, null, null, null, '2021-12-10 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('71', null, null, null, null, null, '2021-12-11 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('72', null, null, null, null, null, '2021-12-11 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('73', null, null, null, null, null, '2021-12-13 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('74', null, null, null, null, null, '2021-12-14 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('75', null, null, null, null, null, '2021-12-15 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('76', null, null, null, null, null, '2021-12-16 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('77', null, null, null, null, null, '2021-12-17 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('78', null, null, null, null, null, '2021-12-20 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('79', null, null, null, null, null, '2021-12-21 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('80', null, null, null, null, null, '2021-12-22 00:00:00', '智慧社区bug修复', '智慧社区bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('81', null, null, null, null, null, '2021-12-24 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('82', null, null, null, null, null, '2021-12-30 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('83', null, null, null, null, null, '2021-12-30 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('84', null, null, null, null, null, '2021-12-30 00:00:00', '数据中台bug修复', '数据中台bug修复', null, null, '1');
INSERT INTO `day_task` VALUES ('85', null, null, null, null, null, null, '', '', '1', '', '1');
INSERT INTO `day_task` VALUES ('86', null, null, null, null, null, null, '', '', '1', '', '1');
INSERT INTO `day_task` VALUES ('87', null, null, null, null, null, null, '', '', '1', '', '1');
INSERT INTO `day_task` VALUES ('88', null, null, null, null, null, null, '', '', '1', '', '1');
INSERT INTO `day_task` VALUES ('89', null, null, null, null, null, null, '', '', '1', '', '1');
