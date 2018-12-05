package com.ppdai.monitor.service.impl;

import com.ppdai.monitor.api.model.AddSystemReqDTO;
import com.ppdai.monitor.api.model.AddSystemResDTO;
import com.ppdai.monitor.api.model.MonitorSystemResDTO;
import com.ppdai.monitor.common.CommonConstants;
import com.ppdai.monitor.dao.MonitorSystemDao;
import com.ppdai.monitor.dao.domain.MonitorMetric;
import com.ppdai.monitor.dao.domain.MonitorSystem;
import com.ppdai.monitor.dao.malsmonitor.MonitorSystemMapper;
import com.ppdai.monitor.service.IMonitorSystemService;
import com.ppdai.monitor.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author haijiang
 * @Description 监控系统service实现类
 * @Date 2018/11/15
 **/
@Service
public class MonitorSystemServiceImpl implements IMonitorSystemService {

    @Autowired
    private MonitorSystemMapper monitorSystemMapper;
    @Autowired
    MonitorSystemDao monitorSystemDao;

    @Override
    public AddSystemResDTO addMonitorSystem(AddSystemReqDTO req) {

        MonitorSystem monitorSystem = new MonitorSystem();
        monitorSystem.setDepart(req.getDepart());
        monitorSystem.setName(req.getName());
        monitorSystem.setOwner(req.getOwner());
        monitorSystem.setInsertTime(new Date());
        monitorSystem.setUpdateTime(new Date());
        monitorSystem.setIsActive(CommonConstants.ACTIVE);
        int i = monitorSystemMapper.insertSelective(monitorSystem);
        AddSystemResDTO addSystemResDTO = new AddSystemResDTO();
        if (i > 0) {
            addSystemResDTO.setStatus(Boolean.TRUE);
            addSystemResDTO.setsId(monitorSystem.getId());
            return addSystemResDTO;
        }
        addSystemResDTO.setStatus(Boolean.FALSE);
        return addSystemResDTO;
    }

    @Override
    public List<MonitorSystemResDTO> queryMonitorSystemAll(Long sId) {
        List<MonitorSystemResDTO> list = new ArrayList<>();
        List<MonitorSystem> monitorSystems = StringUtils.isEmpty(sId) ? monitorSystemDao.queryMonitorSystemAll() : monitorSystemDao.queryMonitorSystemBySId(sId);
        for (MonitorSystem res : monitorSystems) {
            MonitorSystemResDTO monitorSystemResDTO = new MonitorSystemResDTO();
            monitorSystemResDTO.setId(res.getId());
            monitorSystemResDTO.setName(res.getName());
            monitorSystemResDTO.setDepart(res.getDepart());
            monitorSystemResDTO.setOwner(res.getOwner());
            list.add(monitorSystemResDTO);
        }
        return list;
    }

    @Override
    public boolean delMonitorSystem(Long id) {
        Example example = new Example(MonitorSystem.class);
        example.createCriteria().andEqualTo(CommonConstants.ID, id);

        MonitorSystem monitorSystem = new MonitorSystem();
        monitorSystem.setIsActive(CommonConstants.NOT_ACTIVE);
        int i = monitorSystemMapper.updateByExampleSelective(monitorSystem, example);
        return i > 0;
    }
}
