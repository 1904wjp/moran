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

 Date: 27/08/2023 20:50:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单链接',
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `delete_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志',
  `create_ids` bigint(0) NULL DEFAULT NULL COMMENT '创建用户id',
  `user_ids` bigint(0) NULL DEFAULT NULL COMMENT '修改用户id',
  `menu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_order` bigint(0) NULL DEFAULT NULL,
  `parent_id` bigint(0) NULL DEFAULT NULL,
  `menu_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_ids` bigint(0) NULL DEFAULT NULL COMMENT '修改用户id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单链接',
  `val` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单标识',
  `add_params_id` bigint(0) NULL DEFAULT NULL COMMENT '参数表id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `menu_url`(`menu_url`) USING BTREE,
  UNIQUE INDEX `menu_name`(`menu_name`) USING BTREE,
  UNIQUE INDEX `menu_url_2`(`menu_url`) USING BTREE,
  UNIQUE INDEX `menu_url_3`(`menu_url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (NULL, 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, '首页', '/main', 'index', NULL);
INSERT INTO `sys_menu` VALUES (NULL, 2, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, '一起聊天', '/example/user/websocket', 'websocket', NULL);
INSERT INTO `sys_menu` VALUES (NULL, 3, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, '生成代码', '/example/columns/getColumnsPage', 'columns', NULL);
INSERT INTO `sys_menu` VALUES (NULL, 4, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 3, NULL, NULL, NULL, '配置包', '/example/db/packagePage', 'pg', NULL);
INSERT INTO `sys_menu` VALUES (NULL, 5, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 3, NULL, NULL, NULL, '配置数据源', '/example/db/dbPage', 'db', NULL);
INSERT INTO `sys_menu` VALUES (NULL, 6, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, '写笔记', '/example/uedit/uEditorListPage', 'uedit', NULL);
INSERT INTO `sys_menu` VALUES (NULL, 7, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, '疫情数据', '/example/covid/map', 'covid', NULL);
INSERT INTO `sys_menu` VALUES (NULL, 8, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, '关于我们', '/aboutUs', 'au', NULL);
INSERT INTO `sys_menu` VALUES (NULL, 9, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, '相册', '/example/source/albumListPage', 'al', NULL);
INSERT INTO `sys_menu` VALUES (NULL, 10, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 6, NULL, NULL, NULL, '测试', '/test/test', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
