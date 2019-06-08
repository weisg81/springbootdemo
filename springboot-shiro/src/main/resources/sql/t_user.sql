/*
Navicat MySQL Data Transfer

Source Server         : mysql_127.0.0.1
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2019-06-02 22:43:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` bigint(16) NOT NULL,
  `account` varchar(50) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'weisg', '111111', '2019-06-02 20:03:05');
INSERT INTO `t_user` VALUES ('2', 'admin', '111111', null);
