CREATE TABLE `audit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL COMMENT '发送请求者',
  `client_ip` varchar(32) DEFAULT NULL,
  `http_method` varchar(64) DEFAULT NULL COMMENT '请求方法：GET/POST/PUT/DELETE',
  `http_uri` varchar(256) DEFAULT NULL COMMENT '请求URI',
  `class_method` varchar(128) DEFAULT NULL COMMENT '调用方法',
  `class_method_args` varchar(1024) DEFAULT NULL COMMENT '调用方法参数',
  `class_method_return` varchar(1024) DEFAULT NULL COMMENT '调用方法返回值',
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `insert_by` varchar(45) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(45) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

CREATE TABLE `locks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `node_id` varchar(64) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `tick` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `heart_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

