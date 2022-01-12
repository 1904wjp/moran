/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2022-01-12 11:23:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chat_record
-- ----------------------------
DROP TABLE IF EXISTS `chat_record`;
CREATE TABLE `chat_record` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` varchar(64) DEFAULT NULL COMMENT '删除标志',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `user_a_id` bigint(32) DEFAULT NULL COMMENT '用户a的id',
  `user_b_id` bigint(32) DEFAULT NULL COMMENT '用户b的id',
  `content` text COMMENT '聊天内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='聊天记录类';

-- ----------------------------
-- Records of chat_record
-- ----------------------------
INSERT INTO `chat_record` VALUES ('1', null, '2022-01-10 16:02:12', null, null, null, '5', '1', '你好');
INSERT INTO `chat_record` VALUES ('2', null, '2022-01-10 16:03:09', null, null, null, '1', '5', '你好');
INSERT INTO `chat_record` VALUES ('3', null, '2022-01-10 16:03:23', null, null, null, '5', '1', 'hellO');
INSERT INTO `chat_record` VALUES ('4', null, '2022-01-10 16:03:27', null, null, null, '1', '5', '啊？');
INSERT INTO `chat_record` VALUES ('5', null, '2022-01-10 16:03:37', null, null, null, '1', '5', '我老婆是二傻子');
INSERT INTO `chat_record` VALUES ('6', null, '2022-01-10 16:03:53', null, null, null, '5', '1', '巧了，我老婆也是');
INSERT INTO `chat_record` VALUES ('7', null, '2022-01-10 16:09:23', null, null, null, '5', '1', '0.0');
INSERT INTO `chat_record` VALUES ('8', null, '2022-01-10 16:09:39', null, null, null, '5', '1', '0.0');
INSERT INTO `chat_record` VALUES ('9', null, '2022-01-10 16:10:21', null, null, null, '5', '1', '0.0');
INSERT INTO `chat_record` VALUES ('10', null, '2022-01-10 16:11:21', null, null, null, '1', '1', '0.0.');
INSERT INTO `chat_record` VALUES ('11', null, '2022-01-10 16:11:42', null, null, null, '1', '5', '0.');
INSERT INTO `chat_record` VALUES ('12', null, '2022-01-10 16:11:44', null, null, null, '1', '5', '00.0');
INSERT INTO `chat_record` VALUES ('13', null, '2022-01-10 16:11:45', null, null, null, '1', '5', '0.0.');
INSERT INTO `chat_record` VALUES ('14', null, '2022-01-10 16:12:01', null, null, null, '1', '5', '0.0');
INSERT INTO `chat_record` VALUES ('15', null, '2022-01-10 16:12:05', null, null, null, '1', '5', '');
INSERT INTO `chat_record` VALUES ('16', null, '2022-01-10 16:12:05', null, null, null, '1', '5', '');
INSERT INTO `chat_record` VALUES ('17', null, '2022-01-10 16:12:06', null, null, null, '1', '5', '');
INSERT INTO `chat_record` VALUES ('18', null, '2022-01-10 16:12:06', null, null, null, '1', '5', '');
INSERT INTO `chat_record` VALUES ('19', null, '2022-01-10 16:12:07', null, null, null, '1', '5', '...');
INSERT INTO `chat_record` VALUES ('20', null, '2022-01-10 16:12:07', null, null, null, '1', '5', '.');
INSERT INTO `chat_record` VALUES ('21', null, '2022-01-10 16:12:07', null, null, null, '1', '5', '.');
INSERT INTO `chat_record` VALUES ('22', null, '2022-01-10 16:12:08', null, null, null, '1', '5', '.');
INSERT INTO `chat_record` VALUES ('23', null, '2022-01-10 16:12:08', null, null, null, '1', '5', '.');
INSERT INTO `chat_record` VALUES ('24', null, '2022-01-10 16:12:08', null, null, null, '1', '5', '.');
INSERT INTO `chat_record` VALUES ('25', null, '2022-01-10 16:12:08', null, null, null, '1', '5', '.');
INSERT INTO `chat_record` VALUES ('26', null, '2022-01-10 16:12:34', null, null, null, '5', '1', '.。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。');
INSERT INTO `chat_record` VALUES ('27', null, '2022-01-10 16:12:37', null, null, null, '5', '1', '。。。。。。。。。。。。。。。。。。。。。。。。。。。。');
INSERT INTO `chat_record` VALUES ('28', null, '2022-01-10 16:12:39', null, null, null, '5', '1', '。。。。。。。。。。。。。。。。。');
INSERT INTO `chat_record` VALUES ('29', null, '2022-01-10 16:12:40', null, null, null, '5', '1', '。。。。。。。。。。。。。。');
INSERT INTO `chat_record` VALUES ('30', null, '2022-01-10 16:12:57', null, null, null, '5', '1', '。。。。。。。。。。。。。。。。');
INSERT INTO `chat_record` VALUES ('31', null, '2022-01-10 16:12:59', null, null, null, '5', '1', '。。。。。。。。。。。。。。。。。。。。。。。。。');
INSERT INTO `chat_record` VALUES ('32', null, '2022-01-10 16:13:01', null, null, null, '5', '1', '。。。。。。。。。。。。。。。。');
INSERT INTO `chat_record` VALUES ('33', null, '2022-01-10 16:13:03', null, null, null, '5', '1', '。。。。。。。。。。。。。。。。');
INSERT INTO `chat_record` VALUES ('34', null, '2022-01-10 16:13:04', null, null, null, '5', '1', '。。。。。。。。。。。。。。。');
INSERT INTO `chat_record` VALUES ('35', null, '2022-01-10 16:17:21', null, null, null, '5', '1', '你好');
INSERT INTO `chat_record` VALUES ('36', null, '2022-01-10 16:18:57', null, null, null, '5', '1', '111111111111111111111111111111111111111111111111111111111111111111111');
INSERT INTO `chat_record` VALUES ('37', null, '2022-01-10 16:19:12', null, null, null, '5', '1', '爱手工覅的结构i暗色UFO给你苏武功我弄死我');
INSERT INTO `chat_record` VALUES ('38', null, '2022-01-10 16:19:19', null, null, null, '5', '1', '的撒大大撒旦撒旦撒');
INSERT INTO `chat_record` VALUES ('39', null, '2022-01-10 16:28:05', null, null, null, '5', '3', '的撒旦飞洒发顺丰打赏大大撒sdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss');
INSERT INTO `chat_record` VALUES ('40', null, '2022-01-10 16:28:15', null, null, null, '5', '3', 'dadasdsadsa');
INSERT INTO `chat_record` VALUES ('41', null, '2022-01-10 16:28:18', null, null, null, '5', '3', 'dasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
INSERT INTO `chat_record` VALUES ('42', null, '2022-01-10 16:28:31', null, null, null, '5', '3', '你好风格上放射科法门难得跑i买个机票是免费公司的母公司摩纳哥佛山的你莫非你是打磨烦恼的你们佛山的年末');
INSERT INTO `chat_record` VALUES ('43', null, '2022-01-10 16:28:50', null, null, null, '5', '3', '倒萨倒萨倒萨倒萨撒旦·');
INSERT INTO `chat_record` VALUES ('44', null, '2022-01-10 16:28:57', null, null, null, '5', '3', '大撒大撒大撒大撒');
INSERT INTO `chat_record` VALUES ('45', null, '2022-01-10 16:30:03', null, null, null, '5', '6', '4165146515');
INSERT INTO `chat_record` VALUES ('46', null, '2022-01-10 16:34:28', null, null, null, '5', '1', '超');
INSERT INTO `chat_record` VALUES ('47', null, '2022-01-10 16:34:35', null, null, null, '5', '1', '老搞事');
INSERT INTO `chat_record` VALUES ('48', null, '2022-01-10 16:34:39', null, null, null, '5', '1', '来搞事');
INSERT INTO `chat_record` VALUES ('49', null, '2022-01-10 16:34:53', null, null, null, '1', '5', '？？？');
INSERT INTO `chat_record` VALUES ('50', null, '2022-01-10 16:34:56', null, null, null, '5', '1', '？？？');
INSERT INTO `chat_record` VALUES ('51', null, '2022-01-10 16:35:05', null, null, null, '1', '5', '6666');
INSERT INTO `chat_record` VALUES ('52', null, '2022-01-10 16:35:12', null, null, null, '5', '1', '劳资一个能打十个');
