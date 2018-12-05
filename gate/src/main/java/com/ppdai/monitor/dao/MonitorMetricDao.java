package com.ppdai.monitor.dao;

import com.ppdai.monitor.dao.domain.MonitorMetric;

import java.util.List;

/**
 * @Author haijiang
 * @Description 指标详细Dao
 * @Date 2018/11/13
 **/
public interface MonitorMetricDao {

    /**
     * 通过sId查询所有指标监控信息
     *
     * @param sId sId
     * @return List<MonitorMetric>
     */
    List<MonitorMetric> queryMonitorMetricBySId(Long sId);

    /**
     * 通过sId查询所有指标监控信息
     *
     * @param id id
     * @return List<MonitorMetric>
     */
    List<MonitorMetric> queryMonitorMetricByMId(Long id);

    /**
     * 查询所有
     *
     * @return List<MonitorMetric>
     */
    List<MonitorMetric> queryMonitorMetricAll();

}
