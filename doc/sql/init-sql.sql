DROP TABLE IF EXISTS sys_wallet;
CREATE TABLE `sys_wallet` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `uid` bigint NOT NULL COMMENT 'GUID',
  `total` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '总金额',
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `frozen` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `withdraw` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '提现金额',
  `flow` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '流水',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint DEFAULT NULL COMMENT '最后修改人',
  `last_modified_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uid` (`uid`)
) COMMENT='钱包';

DROP TABLE IF EXISTS sys_wallet_record;
CREATE TABLE `sys_wallet_record` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `business_id` bigint DEFAULT NULL COMMENT '业务id',
  `uid` bigint NOT NULL COMMENT 'GUID',
  `wallet_id` bigint NOT NULL COMMENT '钱包ID',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `amount` decimal(12,2) NOT NULL COMMENT '金额',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint DEFAULT NULL COMMENT '最后修改人',
  `last_modified_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
)  COMMENT='钱包-记录';

DROP TABLE IF EXISTS sys_wallet_transfer;
CREATE TABLE `sys_wallet_transfer` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `from` bigint NOT NULL COMMENT 'FROM',
  `to` bigint NOT NULL COMMENT 'TO',
  `amount` decimal(12,2) NOT NULL COMMENT '金额',
  `business_type` varchar(100) DEFAULT NULL COMMENT '业务类型',
  `business_id` bigint DEFAULT NULL COMMENT '业务ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint DEFAULT NULL COMMENT '最后修改人',
  `last_modified_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
)  COMMENT='钱包-转账';

DROP TABLE IF EXISTS sys_wallet_withdraw;
CREATE TABLE `sys_wallet_withdraw` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `uid` bigint NOT NULL COMMENT 'GUID',
  `wallet_id` bigint NOT NULL COMMENT '钱包ID',
  `channel` varchar(20) NOT NULL COMMENT '通道',
  `amount` decimal(12,2) NOT NULL COMMENT '金额',
  `payee` varchar(20) DEFAULT NULL COMMENT '收款人',
  `account` varchar(20) DEFAULT NULL COMMENT '账号',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  `qrcode` varchar(255) DEFAULT NULL COMMENT '二维码',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_by` bigint DEFAULT NULL COMMENT '最后修改人',
  `last_modified_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) COMMENT='钱包-提现';