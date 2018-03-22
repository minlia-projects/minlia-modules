/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost
 Source Database       : minlia_v1

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 01/23/2018 12:22:12 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE
IF NOT EXISTS minlia_captcha(
	id BIGINT UNSIGNED AUTO_INCREMENT COMMENT '自增主键' ,
	cellphone VARCHAR(11) NOT NULL COMMENT '手机号码' ,
	code VARCHAR(11) NOT NULL COMMENT '验证码' ,
	used TINYINT NOT NULL DEFAULT 0 COMMENT '是否使用' ,
	locked TINYINT NOT NULL DEFAULT 0 COMMENT '是否锁定' ,
	failure_count TINYINT NOT NULL DEFAULT 0 COMMENT '验证失败次数' ,
	effective_dtime DATETIME COMMENT '有效时间' ,
	create_date DATETIME DEFAULT NOW() COMMENT '创建时间' ,
	create_by VARCHAR(15) COMMENT 'GUID' ,
	last_modified_date DATETIME COMMENT '最后修改时间' ,
	last_modified_by VARCHAR(15) COMMENT 'GUID',
	enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用',
	PRIMARY KEY(id) ,
	UNIQUE KEY uk_captcha_cellphone(cellphone)
) COMMENT '验证码' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE
IF NOT EXISTS minlia_user(
	id BIGINT UNSIGNED AUTO_INCREMENT COMMENT '自增主键' ,
	guid VARCHAR(15) NOT NULL COMMENT '唯一ID' ,
	username VARCHAR(15) COMMENT '用户名' ,
	password VARCHAR(200) NOT NULL COMMENT '密码' ,
	cellphone VARCHAR(11) COMMENT '手机号码' ,
	email VARCHAR(20) COMMENT '邮箱' ,
	credentials_expired TINYINT NOT NULL DEFAULT 0 COMMENT '凭证是否过期' ,
	expired TINYINT NOT NULL DEFAULT 0 COMMENT '是否过期' ,
	locked TINYINT NOT NULL DEFAULT 0 COMMENT '是否锁定' ,
	lock_limit TINYINT NOT NULL DEFAULT 0 COMMENT '锁定次数' ,
	lock_time DATETIME COMMENT '锁定时间' ,
	last_login_time DATETIME COMMENT '最后登录时间' ,
	last_login_ip VARCHAR(16) COMMENT '最后登录IP' ,
	referral VARCHAR(20) COMMENT '推荐人' ,
	create_date DATETIME DEFAULT NOW() COMMENT '创建时间' ,
	create_by VARCHAR(15) COMMENT 'GUID' ,
	last_modified_date DATETIME COMMENT '最后修改时间' ,
	last_modified_by VARCHAR(15) COMMENT 'GUID' ,
	enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用' ,
	PRIMARY KEY(id) ,
	UNIQUE KEY uk_user_username(username)
) COMMENT '用户' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE
IF NOT EXISTS minlia_role (
	id SMALLINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
	CODE VARCHAR(30) NOT NULL COMMENT '编码' ,
	label VARCHAR(50) COMMENT '标签' ,
	notes VARCHAR(255) COMMENT '备注' ,
	create_date DATETIME DEFAULT NOW() COMMENT '创建时间' ,
	create_by VARCHAR(15) COMMENT 'GUID' ,
	last_modified_date DATETIME COMMENT '最后修改时间' ,
	last_modified_by VARCHAR(15) COMMENT 'GUID' ,
	enabled TINYINT(1) DEFAULT '1' COMMENT '是否启用' ,
	PRIMARY KEY(id) ,
	UNIQUE KEY uk_role_code(CODE)
) COMMENT '角色' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE
IF NOT EXISTS minlia_permission(
	id SMALLINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
	code VARCHAR(30) NOT NULL COMMENT '编码' ,
	label VARCHAR(50) DEFAULT NULL COMMENT '标签' ,
	create_date DATETIME DEFAULT NOW() COMMENT '创建时间' ,
	create_by VARCHAR(15) COMMENT 'GUID' ,
	last_modified_date DATETIME COMMENT '最后修改时间' ,
	last_modified_by VARCHAR(15) COMMENT 'GUID' ,
	enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用' ,
	PRIMARY KEY(id) ,
	UNIQUE KEY uk_permission_code(code)
) COMMENT '权限点' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8 ;


CREATE TABLE
IF NOT EXISTS minlia_navigation(
	id SMALLINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
	type VARCHAR(10) NOT NULL DEFAULT 'LEAF' COMMENT '类型：FOLDER-根、LEAF-叶' ,
	name VARCHAR(50) NOT NULL COMMENT '名称' ,
	icon VARCHAR(255) COMMENT '图标' ,
	state VARCHAR(255) COMMENT '路由' ,
	orders TINYINT COMMENT '顺序' ,
	parent_id SMALLINT COMMENT '父ID' ,
	create_date DATETIME DEFAULT NOW() COMMENT '创建时间' ,
	create_by VARCHAR(15) COMMENT 'GUID' ,
	last_modified_date DATETIME COMMENT '最后修改时间' ,
	last_modified_by VARCHAR(15) COMMENT 'GUID' ,
	enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用' ,
	PRIMARY KEY(id) ,
	UNIQUE KEY uk_navigation_parent_name(parent_id , name)
) COMMENT '导航' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE
IF NOT EXISTS map_user_role(
	user_id BIGINT NOT NULL ,
	role_id SMALLINT NOT NULL
) COMMENT '用户与角色关系映射表' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE
IF NOT EXISTS map_role_permission(
	role_id SMALLINT NOT NULL,
	permission_id SMALLINT NOT NULL
) COMMENT '角色与权限点关系映射表' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE
IF NOT EXISTS map_role_navigation(
	role_id SMALLINT NOT NULL,
	navigation_id SMALLINT NOT NULL
) COMMENT '角色与导航关系映射表' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8;




CREATE TABLE
IF NOT EXISTS minlia_bible(
	id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
	code VARCHAR(20) NOT NULL COMMENT '编码' ,
	value VARCHAR(255) NOT NULL COMMENT '值' ,
	notes VARCHAR(255) COMMENT '图标' ,
	create_date DATETIME DEFAULT NOW() COMMENT '创建时间' ,
	create_by VARCHAR(15) COMMENT 'GUID' ,
	last_modified_date DATETIME COMMENT '最后修改时间' ,
	last_modified_by VARCHAR(15) COMMENT 'GUID' ,
	PRIMARY KEY(id) ,
	UNIQUE KEY uk_navigation_parent_name(code)
) COMMENT '数据字典' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8;


CREATE TABLE
IF NOT EXISTS minlia_bible_item(
	id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键' ,
	parent_code VARCHAR(20) NOT NULL COMMENT '父编码' ,
	code VARCHAR(20) NOT NULL COMMENT '编码' ,
	value VARCHAR(255) NOT NULL COMMENT '值' ,
	notes VARCHAR(255) COMMENT '说明' ,
	sorts TINYINT(4) DEFAULT 0 COMMENT '排序' ,
	attribute1 VARCHAR(255) COMMENT '扩展字段1' ,
	attribute2 VARCHAR(255) COMMENT '扩展字段2' ,
	attribute3 VARCHAR(255) COMMENT '扩展字段3' ,
	create_date DATETIME DEFAULT NOW() COMMENT '创建时间' ,
	create_by VARCHAR(15) COMMENT 'GUID' ,
	last_modified_date DATETIME COMMENT '最后修改时间' ,
	last_modified_by VARCHAR(15) COMMENT 'GUID' ,
	PRIMARY KEY(id) ,
	UNIQUE KEY uk_bible_item_parent_code(parent_code,code)
) COMMENT '数据字典' ,
 ENGINE = INNODB DEFAULT CHARSET = utf8;


SET FOREIGN_KEY_CHECKS = 1;
