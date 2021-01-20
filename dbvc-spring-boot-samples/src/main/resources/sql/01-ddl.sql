-- 公告
DROP TABLE IF EXISTS `j_sys_notice`;
CREATE TABLE `j_sys_notice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `is_new` int(2) NULL DEFAULT NULL COMMENT '是否为最新',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `notice_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统公告' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
