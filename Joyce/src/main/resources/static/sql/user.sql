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

 Date: 27/08/2023 20:49:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `delete_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件地址',
  `id_code` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份号码',
  `person_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人简历',
  `person_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `sex` int(0) NULL DEFAULT NULL COMMENT '性别',
  `user_type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `file_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `secondar_password` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二级密码',
  `status` int(0) NULL DEFAULT NULL,
  `status_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '激活码',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_ids` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `create_ids` bigint(0) NULL DEFAULT NULL COMMENT '创建用户id',
  `update_ids` bigint(0) NULL DEFAULT NULL COMMENT '修改用户id',
  `add_params_id` bigint(0) NULL DEFAULT NULL COMMENT '参数表id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, NULL, NULL, 0, 'admin', '2023-08-16 21:19:19', NULL, NULL, '2239122645@qq.com', NULL, NULL, NULL, '18375391922', 1, '1', '/static/1/static/source/20230816/a854a_a1.jpg', 'admin', NULL, 3, NULL, 'admin', '邢道荣', NULL, NULL, 1, NULL);
INSERT INTO `user` VALUES (2, NULL, '2021-11-22 11:54:36', 0, NULL, '2021-12-22 13:25:07', NULL, NULL, '1122121', NULL, NULL, NULL, '13905673635', NULL, NULL, '/static/img/userImg/file0default0name.png', 'sj123', NULL, 3, NULL, 'sj', '宋江', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (3, NULL, '2021-12-02 14:14:09', 0, NULL, NULL, NULL, NULL, '770098752@qq.com', NULL, NULL, NULL, '15209839616', NULL, NULL, '/static/img/userImg/file0default0name.png', 'admin123', NULL, 1, NULL, 'sunwukong', '孙悟空', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (5, NULL, '2021-12-17 14:01:34', 0, NULL, '2021-12-17 14:03:02', NULL, NULL, '2239122644@qq.com', NULL, NULL, NULL, '18375391921', NULL, NULL, '/static/img/photo/20211217/970d42fd1f3544858646efe855ab1450_20211217020259_ali.gif', 'dm123', NULL, 1, NULL, 'dm', '德玛', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (6, NULL, '2021-12-24 15:10:51', 0, NULL, NULL, NULL, NULL, '1154517070@qq.com', NULL, NULL, NULL, '18856628782', NULL, NULL, '/static/img/userImg/file0default0name.png', 'hj95112066', NULL, 1, NULL, 'lb', '刘备', NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
