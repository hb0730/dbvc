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
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统公告' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `j_sys_notice`(`id`, `create_id`, `create_time`, `title`, `is_new`, `tag`, `memo`, `content`, `notice_time`) VALUES (10, 'B14B0CD7710F44B1A1BB5F2235464245', '2019-12-17 15:56:30', '测试公告', 0, '公告', '预计晚上八点部署更新', NULL, '2019-12-24 16:15:00');
INSERT INTO `j_sys_notice`(`id`, `create_id`, `create_time`, `title`, `is_new`, `tag`, `memo`, `content`, `notice_time`) VALUES (11, 'B14B0CD7710F44B1A1BB5F2235464245', '2019-12-17 16:48:12', '测试公告', 0, NULL, NULL, NULL, '2019-12-24 14:00:00');
