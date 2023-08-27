/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : springboot

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 27/08/2023 20:50:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_type_menu
-- ----------------------------
DROP TABLE IF EXISTS `user_type_menu`;
CREATE TABLE `user_type_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_type_id` bigint(0) NULL DEFAULT NULL COMMENT '身份id',
  `sys_menu_id` bigint(0) NULL DEFAULT NULL COMMENT '菜单id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `delete_flag` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_type_menu
-- ----------------------------
INSERT INTO `user_type_menu` VALUES (1, 1, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_type_menu` VALUES (2, 1, 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_type_menu` VALUES (3, 1, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_type_menu` VALUES (4, 1, 4, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_type_menu` VALUES (5, 1, 5, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_type_menu` VALUES (6, 1, 6, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_type_menu` VALUES (7, 1, 7, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_type_menu` VALUES (8, 1, 8, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_type_menu` VALUES (9, 1, 9, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_type_menu` VALUES (10, 1, 10, NULL, NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
