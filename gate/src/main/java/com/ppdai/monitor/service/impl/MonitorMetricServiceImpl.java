package com.ppdai.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.ppdai.monitor.api.model.AddMetricReqDTO;
import com.ppdai.monitor.api.model.AddMetricResDTO;
import com.ppdai.monitor.api.model.MetricDataDetailReqDTO;
import com.ppdai.monitor.api.model.MonitorMetricResDTO;
import com.ppdai.monitor.common.BusinessException;
import com.ppdai.monitor.common.CommonConstants;
import com.ppdai.monitor.common.ErrorEnum;
import com.ppdai.monitor.config.SpringConfig;
import com.ppdai.monitor.dao.MonitorMetricDao;
import com.ppdai.monitor.dao.domain.MonitorMetric;
import com.ppdai.monitor.dao.domain.MonitorTag;
import com.ppdai.monitor.dao.malsmonitor.MonitorMetricMapper;
import com.ppdai.monitor.service.IGraphicsService;
import com.ppdai.monitor.service.IKairosDbService;
import com.ppdai.monitor.service.IMonitorMetricService;
import com.ppdai.monitor.service.IMonitorTagService;
import com.ppdai.monitor.service.entity.BaseResult;
import com.ppdai.monitor.service.entity.TagEntity;
import com.ppdai.monitor.util.StringUtils;
import org.kairosdb.client.builder.Grouper;
import org.kairosdb.client.builder.grouper.TagGrouper;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author haijiang
 * @Description 指标详细实现类
 * @Date 2018/11/15
 **/
@Service
public class MonitorMetricServiceImpl implements IMonitorMetricService {

    @Autowired
    private MonitorMetricMapper monitorMetricMapper;
    @Autowired
    private MonitorMetricDao monitorMetricDao;
    @Autowired
    private IMonitorTagService monitorTagService;
    @Autowired
    private IKairosDbService kairosDbService;

    @Override
    public AddMetricResDTO addMonitorMetric(AddMetricReqDTO req) {
        MonitorMetric monitorMetric = new MonitorMetric();
        monitorMetric.setAggFun(req.getFunc());
        monitorMetric.setChart(req.getChart());
        monitorMetric.setInsertTime(new Date());
        monitorMetric.setUpdateTime(new Date());
        monitorMetric.setIsActive(CommonConstants.ACTIVE);
        monitorMetric.setMetric(req.getMetric());
        monitorMetric.setsId(req.getsId());
        monitorMetric.setTitle(req.getTitle());
        int i = monitorMetricMapper.insertSelective(monitorMetric);

        AddMetricResDTO addMetricResDTO = new AddMetricResDTO();
        if (i > 0) {
            MonitorTag monitorTag = new MonitorTag();
            monitorTag.setmId(monitorMetric.getId());
            TagEntity tagEntity = new TagEntity();
            tagEntity.setTags(req.getTagName());
            monitorTag.setTagName(JSON.toJSONString(tagEntity));
            monitorTagService.addMonitorTag(monitorTag);
            addMetricResDTO.setStatus(Boolean.TRUE);
            return addMetricResDTO;
        }
        addMetricResDTO.setStatus(Boolean.FALSE);
        return addMetricResDTO;
    }

    @Override
    public boolean delMonitorMetric(Long id) {
        Example example = new Example(MonitorMetric.class);
        example.createCriteria().andEqualTo(CommonConstants.ID, id);

        MonitorMetric monitorMetric = new MonitorMetric();
        monitorMetric.setIsActive(CommonConstants.NOT_ACTIVE);
        int i = monitorMetricMapper.updateByExampleSelective(monitorMetric, example);
        if (i > 0) {
            monitorTagService.delMonitorTag(id);
            return true;
        }
        return false;
    }

    @Override
    public List<MonitorMetricResDTO> queryMonitorMetricBySId(Long id) {
        List<Long> listId = new ArrayList<>();
        List<MonitorMetric> monitorMetrics = StringUtils.isEmpty(id) ? monitorMetricDao.queryMonitorMetricAll() : monitorMetricDao.queryMonitorMetricBySId(id);
        for (MonitorMetric monitorMetric : monitorMetrics) {
            listId.add(monitorMetric.getId());
        }
        return queryMonitorMetric(monitorMetrics, listId);
    }

    private List<MonitorMetricResDTO> queryMonitorMetric(List<MonitorMetric> monitorMetrics, List<Long> listId) {
        List<MonitorMetricResDTO> listRes = new ArrayList<>();
        List<MonitorTag> monitorTags = monitorTagService.queryMonitorTag(listId);
        for (MonitorMetric monitorMetric : monitorMetrics) {
            MonitorMetricResDTO monitorMetricResDTO = new MonitorMetricResDTO();
            for (MonitorTag monitorTag : monitorTags) {
                if (monitorTag.getmId().equals(monitorMetric.getId())) {
                    monitorMetricResDTO.setTags(monitorTag.getTagName());
                }
            }
            monitorMetricResDTO.setmId(monitorMetric.getId());
            monitorMetricResDTO.setAggFun(monitorMetric.getAggFun());
            monitorMetricResDTO.setChart(monitorMetric.getChart());
            monitorMetricResDTO.setMetric(monitorMetric.getMetric());
            monitorMetricResDTO.setTitle(monitorMetric.getTitle());
            listRes.add(monitorMetricResDTO);
        }
        return listRes;

    }

    @Override
    public BaseResult queryMonitorMetricById(MetricDataDetailReqDTO req) {
        List<MonitorMetric> monitorMetrics = monitorMetricDao.queryMonitorMetricByMId(req.getmId());
        if (StringUtils.isEmpty(monitorMetrics)) {
            throw new BusinessException(ErrorEnum.NO_EXISTS);
        }
        MonitorMetric monitorMetric = monitorMetrics.get(0);
        List<MonitorTag> monitorTags = monitorTagService.queryMonitorTagById(monitorMetric.getId());
        if (StringUtils.isEmpty(monitorTags)) {
            throw new BusinessException(ErrorEnum.NO_EXISTS);
        }
        MonitorTag monitorTag = monitorTags.get(0);
        String tagName = monitorTag.getTagName();
        List<Grouper> grouperList = new ArrayList<>();
        //不是单个图
        if (!CommonConstants.SINGLE.equals(monitorMetric.getChart())) {
            List<String> list = new ArrayList<>(Arrays.asList(tagName.split(StringUtils.COMMA)));
            grouperList.add(new TagGrouper(list));
        }
        QueryResponse queryResponse = kairosDbService.queryKairos(monitorMetric.getMetric(), monitorMetric.getAggFun(), req.getTimeScale(), grouperList);
        IGraphicsService graphicsService = (IGraphicsService) SpringConfig.getApplicationContext().getBean(monitorMetric.getChart());
        return graphicsService.process(queryResponse);
    }

}
