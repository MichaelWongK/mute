/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : ry_act6

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2020-04-08 11:36:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8 COMMENT='角色信息表';


-- ----------------------------
-- Table structure for ums_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_role`;
CREATE TABLE `ums_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';

 ----------------------------
-- Table structure for biz_todo_item
-- ----------------------------
DROP TABLE IF EXISTS `biz_todo_item`;
CREATE TABLE `biz_todo_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `item_name` varchar(100) DEFAULT NULL COMMENT '事项标题',
  `item_content` varchar(500) DEFAULT NULL COMMENT '事项内容',
  `module` varchar(50) DEFAULT NULL COMMENT '模块名称 (必须以 uri 一致)',
  `task_id` varchar(64) DEFAULT NULL COMMENT '任务 ID',
  `instance_id` varchar(32) DEFAULT NULL COMMENT '流程实例 ID',
  `task_name` varchar(50) DEFAULT NULL COMMENT '任务名称 (必须以表单页面名称一致)',
  `node_name` varchar(50) DEFAULT NULL COMMENT '节点名称',
  `is_view` char(1) DEFAULT '0' COMMENT '是否查看 default 0 (0 否 1 是)',
  `is_handle` char(1) DEFAULT '0' COMMENT '是否处理 default 0 (0 否 1 是)',
  `todo_user_id` varchar(20) DEFAULT NULL COMMENT '待办人 ID',
  `todo_user_name` varchar(30) DEFAULT NULL COMMENT '待办人名称',
  `handle_user_id` varchar(20) DEFAULT NULL COMMENT '处理人 ID',
  `handle_user_name` varchar(30) DEFAULT NULL COMMENT '处理人名称',
  `todo_time` datetime DEFAULT NULL COMMENT '通知时间',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='待办事项表';
