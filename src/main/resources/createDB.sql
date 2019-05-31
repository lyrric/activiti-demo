/*
Navicat MySQL Data Transfer

Source Server         : localhost_1
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-05-31 15:16:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL COMMENT '学生ID',
  `student_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学生姓名',
  `start_time` datetime DEFAULT NULL COMMENT '请假开始时间',
  `day` int(6) DEFAULT NULL COMMENT '请假天数',
  `created_time` datetime DEFAULT NULL COMMENT '提交时间',
  `pro_inst_id` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '进程实例ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
