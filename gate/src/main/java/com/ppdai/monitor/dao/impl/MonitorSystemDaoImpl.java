package com.ppdai.monitor.dao.impl;

import com.ppdai.monitor.common.CommonConstants;
import com.ppdai.monitor.dao.MonitorSystemDao;
import com.ppdai.monitor.dao.domain.MonitorSystem;
import com.ppdai.monitor.dao.malsmonitor.MonitorSystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author haijiang
 * @Description 系统监控Dao实现类
 * @Date 2018/11/13
 **/
@Repository
public class MonitorSystemDaoImpl implements MonitorSystemDao {
    @Autowired
    private MonitorSystemMapper monitorSystemMapper;

    @Override
    public List<MonitorSystem> queryMonitorSystemAll() {
        Example example = new Example(MonitorSystem.class);
        example.createCriteria().andEqualTo(CommonConstants.IS_ACTIVE, CommonConstants.ACTIVE);
        return monitorSystemMapper.selectByExample(example);
    }

    @Override
    public List<MonitorSystem> queryMonitorSystemBySId(Long sId) {
        Example example = new Example(MonitorSystem.class);
        example.createCriteria().andEqualTo(CommonConstants.ID, sId).andEqualTo(CommonConstants.IS_ACTIVE, CommonConstants.ACTIVE);
        return monitorSystemMapper.selectByExample(example);
    }
}
