package com.ppdai.monitor.dao.impl;

import com.ppdai.monitor.common.CommonConstants;
import com.ppdai.monitor.dao.MonitorMetricDao;
import com.ppdai.monitor.dao.domain.MonitorMetric;
import com.ppdai.monitor.dao.malsmonitor.MonitorMetricMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author haijiang
 * @Description 指标详细Dao实现类
 * @Date 2018/11/13
 **/
@Repository
public class MonitorMetricDaoImpl implements MonitorMetricDao {
    @Autowired
    private MonitorMetricMapper monitorMetricMapper;

    @Override
    public List<MonitorMetric> queryMonitorMetricBySId(Long sId) {
        Example example = new Example(MonitorMetric.class);
        example.createCriteria().andEqualTo(CommonConstants.S_ID, sId).andEqualTo(CommonConstants.IS_ACTIVE, CommonConstants.ACTIVE);
        return monitorMetricMapper.selectByExample(example);
    }

    @Override
    public List<MonitorMetric> queryMonitorMetricByMId(Long id) {
        Example example = new Example(MonitorMetric.class);
        example.createCriteria().andEqualTo(CommonConstants.ID, id).andEqualTo(CommonConstants.IS_ACTIVE, CommonConstants.ACTIVE);
        return monitorMetricMapper.selectByExample(example);
    }

    @Override
    public List<MonitorMetric> queryMonitorMetricAll() {
        Example example = new Example(MonitorMetric.class);
        example.createCriteria().andEqualTo(CommonConstants.IS_ACTIVE, CommonConstants.ACTIVE);
        return monitorMetricMapper.selectByExample(example);
    }
}
