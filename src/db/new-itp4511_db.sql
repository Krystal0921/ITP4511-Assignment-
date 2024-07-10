/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : localhost:3306
 Source Schema         : itp4511_db

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 28/04/2024 18:18:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for borrow_list
-- ----------------------------
DROP TABLE IF EXISTS `borrow_list`;
CREATE TABLE `borrow_list`  (
  `borrow_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pick_up_date` datetime NULL DEFAULT NULL,
  `return_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`borrow_id`) USING BTREE,
  INDEX `fk-user_id-borrowlist`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk-user_id-borrowlist` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of borrow_list
-- ----------------------------


-- ----------------------------
-- Table structure for borrow_list_detail
-- ----------------------------
DROP TABLE IF EXISTS `borrow_list_detail`;
CREATE TABLE `borrow_list_detail`  (
  `borrow_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `campus_item_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`borrow_id`, `campus_item_id`) USING BTREE,
  INDEX `fk-item_id-borrow_list_detail`(`campus_item_id` ASC) USING BTREE,
  CONSTRAINT `fk-borrow_id-borrow_list_detail` FOREIGN KEY (`borrow_id`) REFERENCES `borrow_list` (`borrow_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-campus_item_id-borrow_list_detail` FOREIGN KEY (`campus_item_id`) REFERENCES `campus_item` (`campus_item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of borrow_list_detail
-- ----------------------------


-- ----------------------------
-- Table structure for campus
-- ----------------------------
DROP TABLE IF EXISTS `campus`;
CREATE TABLE `campus`  (
  `campus_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `campus_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `campus_locate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`campus_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of campus
-- ----------------------------
INSERT INTO `campus` VALUES ('c0000001', 'Chai Wan', 'Chai Wan');
INSERT INTO `campus` VALUES ('c0000002', 'Lee Wai Lee', 'Lee Wai Lee');
INSERT INTO `campus` VALUES ('c0000003', 'Sha Tin', 'Sha Tin');
INSERT INTO `campus` VALUES ('c0000004', 'Tuen Mun', 'Tuen Mun');
INSERT INTO `campus` VALUES ('c0000005', 'Tsing Yi', 'Tsing Yi');

-- ----------------------------
-- Table structure for campus_item
-- ----------------------------
DROP TABLE IF EXISTS `campus_item`;
CREATE TABLE `campus_item`  (
  `campus_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `campus_item_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `item_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`campus_id`, `campus_item_id`) USING BTREE,
  INDEX `fk-item_id-campus_item`(`item_id` ASC) USING BTREE,
  INDEX `campus_item_id`(`campus_item_id` ASC) USING BTREE,
  CONSTRAINT `fk-campus_id-campus_item` FOREIGN KEY (`campus_id`) REFERENCES `campus` (`campus_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-item_id-campus_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of campus_item
-- ----------------------------


-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `item_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`item_id`, `user_id`) USING BTREE,
  INDEX `fk-user_id-cart`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk-item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-user_id-cart` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cart
-- ----------------------------

-- ----------------------------
-- Table structure for damage_report
-- ----------------------------
DROP TABLE IF EXISTS `damage_report`;
CREATE TABLE `damage_report`  (
  `report_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `campus_item_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `datetime` date NOT NULL,
  `status` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`report_id`) USING BTREE,
  INDEX `fk_item_id`(`campus_item_id` ASC) USING BTREE,
  INDEX `fk-user_id-damage_report`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk-campus_item_id-damage_report` FOREIGN KEY (`campus_item_id`) REFERENCES `campus_item` (`campus_item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-user_id-damage_report` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of damage_report
-- ----------------------------

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `item_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `item_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `for_staff` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `active` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of item
-- ----------------------------


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `campus_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `active` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `fk-campus_id-user`(`campus_id` ASC) USING BTREE,
  CONSTRAINT `fk-campus_id-user` FOREIGN KEY (`campus_id`) REFERENCES `campus` (`campus_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('u0000001', 'admin', 'admin', 'admin', 'c0000001', 'active');


-- ----------------------------
-- Table structure for wish_list
-- ----------------------------
DROP TABLE IF EXISTS `wish_list`;
CREATE TABLE `wish_list`  (
  `item_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`item_id`, `user_id`) USING BTREE,
  INDEX `fk-user_id-wishlist`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk-user_id-wishlist` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_item_id-wishlist` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wish_list
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
