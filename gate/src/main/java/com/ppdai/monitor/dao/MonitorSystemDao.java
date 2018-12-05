package com.ppdai.monitor.dao;

import com.ppdai.monitor.dao.domain.MonitorSystem;

import java.util.List;

/**
 * @Author haijiang
 * @Description 系统监控Dao
 * @Date 2018/11/13
 **/
public interface MonitorSystemDao {

    /**
     * 查询所有系统监控信息
     *
     * @return List<MonitorSystem>
     */
    List<MonitorSystem> queryMonitorSystemAll();

    /**
     * 通过sid查询
     *
     * @param sId sId
     * @return List<MonitorSystem>
     */
    List<MonitorSystem> queryMonitorSystemBySId(Long sId);
}
