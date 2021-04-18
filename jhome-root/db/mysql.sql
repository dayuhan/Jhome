/*
 Navicat Premium Data Transfer

 Source server         : 10.1.241.152
 Source server Type    : MySQL
 Source server Version : 50717
 Source Host           : 10.1.241.152:3306
 Source Schema         : mysqlDb

 Target server Type    : MySQL
 Target server Version : 50717
 File Encoding         : 65001

 Date: 22/08/2020 11:31:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cs_registered_config
-- ----------------------------
DROP TABLE IF EXISTS `cs_registered_config`;
CREATE TABLE `cs_registered_config`  (
                                         `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                         `paramName` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '参数名',
                                         `key` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '键',
                                         `value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '值',
                                         `application` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '应用名称',
                                         `isEnable` int(11) DEFAULT 0 COMMENT '是否启用 0:启用 1:禁用',
                                         `profile` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '环境',
                                         `label` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '分支',
                                         `createTime` datetime(0) DEFAULT NULL COMMENT '创建时间',
                                         `createBy` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
                                         `updateTime` datetime(0) DEFAULT NULL COMMENT '修改时间',
                                         `updateBy` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '修复人',
                                         `isPublicComponent` int(11) DEFAULT 0 COMMENT '是否是公共组件 0不是 1是',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- 	***************************************************************系统标准产品，简化部署，分部署始化数据*********************************************************
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('060af790-056f-11eb-a27d-0242ac110004', '数据库配置-密码', 'spring.datasource.password', '123456', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91810', 'redis配置-地址', 'spring.redis.host', '192.168.52.130', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91811', '注册中心配置-当前服务部署地址', 'eureka.instance.ipAddress', '127.0.0.1', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91812', '大屏tomcat-端口号', 'server.port', '8300', 'account-fileStore', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91813', '申请码', 'declareCode', '9549a4bf4a3aea064be955f499c91cc6', 'account-configService', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91814', '注册码', 'registeredCode', 'XhbvlphAeVRAv99K', 'account-configService', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91815', '授权码', 'authorizationCode', '06deaeoXfTH0TKznLXMPjHt1oqx7Ty4w1FSNyqGg9t1cPdsSV63BL67JWoR5AlevEWFr6JKfKAB9qVoFChkZCpMwuv11lPMunX4vmFrTL/cWHETE8QZR08D5gzv1m9fUb1m/SorZZWaVob59H03ygrGuc58ZyXN+xPRc98JtENU=', 'account-configService', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91816', 'redis配置-默认数据库', 'spring.redis.database', '0', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91817', 'redis配置-端口', 'spring.redis.port', '6379', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91818', 'redis配置-密码', 'spring.redis.password', '123456', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91819', '注册中心配置-地址', 'eureka.client.serviceUrl.defaultZone', 'http://daxunihao:123456@127.0.0.1:9100/account/eureka/', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc91820', '注册中心配置-当前服务部署地址', 'eureka.instance.ipAddress', '127.0.0.1', 'account-fileStore', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918b5', 'redis配置-地址', 'spring.redis.host', '192.168.52.130', 'account-fileStore', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918b6', 'redis配置-默认数据库', 'spring.redis.database', '0', 'account-fileStore', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918b7', 'redis配置-端口', 'spring.redis.port', '6379', 'account-fileStore', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918b8', 'redis配置-密码', 'spring.redis.password', '123456', 'account-fileStore', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918ba', '数据库配置-地址', 'spring.datasource.url', 'jdbc:mysql://192.168.52.130:3306/mysqlDb?serverTimezone=UTC&useSSL=false', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bb', '单点登陆配置-CAS服务器', 'account.sysproperties.casConfig.serverUrl', 'http://xxx.com', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bc', '任务链tomcat-端口号', 'server.port', '8200', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bd', '单点登陆配置-项目地址', 'account.sysproperties.casConfig.projectUrl', 'http://127.0.0.1:8100/account/account', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918be', '单点登陆配置-前端部署地址', 'account.sysproperties.casConfig.redirectUrl', 'http://127.0.0.1:9101/#/sslogin', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bf', '单点登陆配置-是否启用CAS认证', 'account.sysproperties.casConfig.isEnable', 'false', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bg', '系统退出-配置', 'account.sysproperties.callbackUrl', 'http://127.0.0.1:9101/#/login', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bh', '消息中心配置-部署地址', 'account.sysproperties.nettyServiceConfig.url', '127.0.0.1', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bi', '消息中心配置-部署地址-端口号', 'account.sysproperties.nettyServiceConfig.port', '8899', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bl', '消息中心配置-部署地址-消息长度', 'account.sysproperties.nettyServiceConfig.maxLength', '600', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bm', 'redis配置-地址', 'spring.redis.host', '192.168.52.130', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bn', '数据库配置-用户名', 'spring.datasource.username', 'root', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bo', 'redis配置-默认数据库', 'spring.redis.database', '0', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bp', 'redis配置-端口', 'spring.redis.port', '6379', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bq', 'redis配置-密码', 'spring.redis.password', '123456', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918br', '注册中心配置-地址', 'eureka.client.serviceUrl.defaultZone', 'http://daxunihao:123456@127.0.0.1:9100/account/eureka/', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bs', '注册中心配置-当前服务部署地址', 'eureka.instance.ipAddress', '127.0.0.1', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '1');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bt', '第三方认证配置-部署地址', 'ucxxx.url', 'http://10.1.135.40:5020', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bu', '第三方认证配置-加密秘钥', 'ucxxx.signature', 'ky6xbsOp1UotNvz3H7fI', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bv', '第三方认证配置-产品ID', 'ucxxx.appId', 'OhtZG', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918bw', '账户服务tomcat-端口号', 'server.port', '8100', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('89eb12c8-b8e2-4b17-87c7-f9a6bfc918by', '数据库配置-密码', 'spring.datasource.password', '123456', 'account-controller', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('c53cda79-056f-11eb-a27d-0242ac110004', '数据库配置-地址', 'spring.datasource.url', 'jdbc:mysql://192.168.52.130:3306/mysqlDb?serverTimezone=UTC&useSSL=false', 'account-fileStore', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('c53f1a78-056f-11eb-a27d-0242ac110004', '数据库配置-用户名', 'spring.datasource.username', 'root', 'account-fileStore', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('c541b333-056f-11eb-a27d-0242ac110004', '数据库配置-密码', 'spring.datasource.password', '123456', 'account-fileStore', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('f80ff8b0-056e-11eb-a27d-0242ac110004', '数据库配置-地址', 'spring.datasource.url', 'jdbc:mysql://192.168.52.130:3306/mysqlDb?serverTimezone=UTC&useSSL=false', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');
INSERT INTO `cs_registered_config` (`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`, `createTime`, `createBy`, `updateTime`, `updateBy`, `isPublicComponent`) VALUES ('fee78356-056e-11eb-a27d-0242ac110004', '数据库配置-用户名', 'spring.datasource.username', 'root', 'account-account', '0', 'dev', 'master', '2020-08-22 09:23:11', 'admin', '2020-08-22 09:23:11', 'admin', '0');


/*
 Navicat Premium Data Transfer

 Source server         : 10.1.241.152
 Source server Type    : MySQL
 Source server Version : 50717
 Source Host           : 10.1.241.152:3306
 Source Schema         : mysqlDb

 Target server Type    : MySQL
 Target server Version : 50717
 File Encoding         : 65001

 Date: 12/09/2020 15:22:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ns_message_information
-- ----------------------------
DROP TABLE IF EXISTS `ns_message_information`;
CREATE TABLE `ns_message_information`  (
                                           `Id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                           `from_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                           `to_group_Id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                           `to_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                           `content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                           `type` int(11) DEFAULT NULL,
                                           `file_url` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                           `file_Size` int(11) DEFAULT NULL,
                                           `status` int(11) DEFAULT 0,
                                           `created_by` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                           `created_time` datetime(0) DEFAULT NULL,
                                           `tenant_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                           `school_year_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                           `send_count` int(11) DEFAULT NULL,
                                           PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fs_file_directory
-- ----------------------------
DROP TABLE IF EXISTS `fs_file_directory`;
CREATE TABLE `fs_file_directory`  (
                                      `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                      `fileDirectoryName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件目录',
                                      `parentDirectoryId` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父目录id',
                                      `createTime` datetime(0) DEFAULT NULL,
                                      `createBy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                      `updateTime` datetime(0) DEFAULT NULL,
                                      `updateBy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                      `isDelete` bit(1) DEFAULT NULL COMMENT '是否删除：0否 1是',
                                      `tenantId` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fs_file_directory_detail
-- ----------------------------
DROP TABLE IF EXISTS `fs_file_directory_detail`;
CREATE TABLE `fs_file_directory_detail`  (
                                             `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                             `fileDirectoryId` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件目录',
                                             `fileType` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件类型',
                                             `fileName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件名称',
                                             `fileDownloadCount` int(12) DEFAULT NULL COMMENT '文件下载次数',
                                             `fileSize` int(12) DEFAULT NULL COMMENT '文件大小',
                                             `fileUrl` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件地址',
                                             `fileRemarks` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件备注',
                                             `createTime` datetime(0) DEFAULT NULL,
                                             `createBy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                             `updateTime` datetime(0) DEFAULT NULL,
                                             `updateBy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                             `isDelete` bit(1) DEFAULT NULL COMMENT '是否删除：0否 1是',
                                             `tenantId` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '租户ID',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


/*
truncate cs_registered_config;
SELECT * from cs_registered_config;
UPDATE   cs_registered_config set isPublicComponent=1 ,isEnable=0 where   paramName like '%redis%' or  paramName like '%注册中心%' or  paramName like '%消息中心%'or  paramName like '%系统退出-配置%';

SELECT * from cs_registered_config;

-- 公共组件
SELECT * from cs_registered_config where  isPublicComponent=1;
GROUP BY paramName;
 -- 其他组件
SELECT * from cs_registered_config where  isPublicComponent=0  and application='account-controller';
SELECT * from cs_registered_config where  isPublicComponent=0  and application='account-account';
SELECT * from cs_registered_config where  isPublicComponent=0  and application='bigscreen';

*/


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `transaction_log`;
CREATE TABLE `transaction_log`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(255) DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `messageBody` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '消息体',
  `producer` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '生产者回调地址',
  `consumer` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '消费者回调地址',
  `createTime` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;