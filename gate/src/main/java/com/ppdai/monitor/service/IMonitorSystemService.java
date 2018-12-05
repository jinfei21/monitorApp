package com.ppdai.monitor.service;

import com.ppdai.monitor.api.model.AddSystemReqDTO;
import com.ppdai.monitor.api.model.AddSystemResDTO;
import com.ppdai.monitor.api.model.MonitorSystemResDTO;

import java.util.List;

/**
 * @Author haijiang
 * @Description 监控系统service
 * @Date 2018/11/15
 **/
public interface IMonitorSystemService {

    /**
     * 新增监控系统
     *
     * @param req req
     * @return boolean
     */
    AddSystemResDTO addMonitorSystem(AddSystemReqDTO req);

    /**
     * 查询所有系统配置
     *
     * @return List<MonitorSystemResDTO>
     */
    List<MonitorSystemResDTO> queryMonitorSystemAll(Long sId);

    /**
     * 删除监控系统
     *
     * @param id
     * @return boolean
     */
    boolean delMonitorSystem(Long id);

}
