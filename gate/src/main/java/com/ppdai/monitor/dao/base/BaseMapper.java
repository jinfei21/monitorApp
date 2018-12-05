package com.ppdai.monitor.dao.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author haijiang
 * @Description BaseMapper
 * @Date 2018/09/18
 **/
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
