DROP TABLE IF EXISTS `m_system`;
CREATE TABLE `m_system` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增列',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '系统名称',
  `depart` varchar(50) NOT NULL DEFAULT '' COMMENT '部门',
  `owner` varchar(50) NOT NULL DEFAULT '' COMMENT '负责人',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '活动状态 1.有效 0 禁用',
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='系统监控表/*# Owner: 高海江 ;Manager: feiyongjun #*/';


DROP TABLE IF EXISTS `m_metric`;
CREATE TABLE `m_metric` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增列',
  `s_id` bigint(20) NOT NULL COMMENT '系统id',
  `title` varchar(50) NOT NULL DEFAULT '' COMMENT '显示标题',
  `metric` varchar(50) NOT NULL DEFAULT '' COMMENT '指标名称',
  `chart` varchar(50) NOT NULL DEFAULT '' COMMENT '图标类型',
  `agg_fun` varchar(50) NOT NULL DEFAULT '' COMMENT '聚合函数',
  `time_scale` bigint(20) NOT NULL COMMENT '时间间隔',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '活动状态 1.有效 0 禁用',
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_s_id` (`s_id`)
) ENGINE=InnoDB COMMENT='系统指标详细表/*# Owner: 高海江 ;Manager: feiyongjun #*/';


DROP TABLE IF EXISTS `m_tag`;
CREATE TABLE `m_tag` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增列',
  `m_id` bigint(20) NOT NULL COMMENT '指标id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '维度名称',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '活动状态 1.有效 0 禁用',
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_m_id` (`m_id`)
) ENGINE=InnoDB COMMENT='系统指标表/*# Owner: 高海江 ;Manager: feiyongjun #*/';
