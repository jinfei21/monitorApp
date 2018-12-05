package com.ppdai.monitor.service.impl;

import com.ppdai.monitor.common.CommonConstants;
import com.ppdai.monitor.dao.domain.MonitorTag;
import com.ppdai.monitor.dao.malsmonitor.MonitorTagMapper;
import com.ppdai.monitor.service.IMonitorTagService;
import com.ppdai.monitor.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author haijiang
 * @Description 系统指标实现类
 * @Date 2018/11/15
 **/
@Service
public class MonitorTagServiceImpl implements IMonitorTagService {
    @Autowired
    private MonitorTagMapper monitorTagMapper;

    @Override
    public boolean addMonitorTag(MonitorTag monitorTag) {
        monitorTag.setIsActive(CommonConstants.ACTIVE);
        monitorTag.setInsertTime(new Date());
        monitorTag.setUpdateTime(new Date());
        int i = monitorTagMapper.insertSelective(monitorTag);
        return i > 0;
    }

    @Override
    public boolean delMonitorTag(Long id) {
        Example example = new Example(MonitorTag.class);
        example.createCriteria().andEqualTo(CommonConstants.M_ID, id);

        MonitorTag monitorTag = new MonitorTag();
        monitorTag.setIsActive(CommonConstants.NOT_ACTIVE);

        int i = monitorTagMapper.updateByExampleSelective(monitorTag, example);
        return i > 0;
    }

    @Override
    public List<MonitorTag> queryMonitorTag(List<Long> id) {
        if (StringUtils.isEmpty(id)) {
            return Collections.EMPTY_LIST;
        }
        Example example = new Example(MonitorTag.class);
        example.createCriteria().andIn(CommonConstants.M_ID, id).andEqualTo(CommonConstants.IS_ACTIVE, CommonConstants.ACTIVE);
        return monitorTagMapper.selectByExample(example);
    }

    @Override
    public List<MonitorTag> queryMonitorTagById(Long id) {
        Example example = new Example(MonitorTag.class);
        example.createCriteria().andEqualTo(CommonConstants.M_ID, id).andEqualTo(CommonConstants.IS_ACTIVE, CommonConstants.ACTIVE);
        return monitorTagMapper.selectByExample(example);
    }
}
