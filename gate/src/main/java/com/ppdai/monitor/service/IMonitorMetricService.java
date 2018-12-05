package com.ppdai.monitor.service;

import com.ppdai.monitor.api.model.AddMetricReqDTO;
import com.ppdai.monitor.api.model.AddMetricResDTO;
import com.ppdai.monitor.api.model.MetricDataDetailReqDTO;
import com.ppdai.monitor.api.model.MonitorMetricResDTO;
import com.ppdai.monitor.service.entity.BaseResult;

import java.util.List;

/**
 * @Author haijiang
 * @Description 指标详细服务
 * @Date 2018/11/15
 **/
public interface IMonitorMetricService {

    /**
     * 添加指标详细
     *
     * @param req req
     * @return boolean
     */
    AddMetricResDTO addMonitorMetric(AddMetricReqDTO req);

    /**
     * 删除指标
     *
     * @param id id
     * @return boolean
     */
    boolean delMonitorMetric(Long id);

    /**
     * 通过系统id查询所有指标信息
     *
     * @param id id
     * @return MonitorMetricResDTO
     */
    List<MonitorMetricResDTO> queryMonitorMetricBySId(Long id);

    /**
     * 通过mId查询所有指标监控信息
     *
     * @param req req
     * @return List<MonitorMetric>
     */
    BaseResult queryMonitorMetricById(MetricDataDetailReqDTO req);
}
