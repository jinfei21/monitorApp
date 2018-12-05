package com.ppdai.monitor.service;

import com.ppdai.monitor.dao.domain.MonitorTag;

import java.util.List;

/**
 * @Author haijiang
 * @Description 系统指标服务
 * @Date 2018/11/15
 **/
public interface IMonitorTagService {

    /**
     * 新增系统指标
     *
     * @param monitorTag monitorTag
     * @return boolean
     */
    boolean addMonitorTag(MonitorTag monitorTag);

    /**
     * 通过id删除系统指标信息
     *
     * @param id id
     * @return boolean
     */
    boolean delMonitorTag(Long id);

    /**
     * 通过多个id查询系统指标信息
     *
     * @param id id
     * @return
     */
    List<MonitorTag> queryMonitorTag(List<Long> id);

    /**
     * 通过id查询系统指标信息
     *
     * @param id id
     * @return
     */
    List<MonitorTag> queryMonitorTagById(Long id);
}
