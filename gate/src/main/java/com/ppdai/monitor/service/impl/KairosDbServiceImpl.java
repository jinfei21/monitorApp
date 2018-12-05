package com.ppdai.monitor.service.impl;

import com.ppdai.monitor.common.CommonConstants;
import com.ppdai.monitor.service.IKairosDbService;
import com.ppdai.monitor.util.MonitorUtils;
import com.ppdai.monitor.util.StringUtils;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.*;
import org.kairosdb.client.builder.aggregator.SamplingAggregator;
import org.kairosdb.client.response.GetResponse;
import org.kairosdb.client.response.QueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author haijiang
 * @Description KairosDb服务实现类
 * @Date 2018/11/15
 **/
@Service
public class KairosDbServiceImpl implements IKairosDbService {

    @Value("${logmetric.handler.kairosdb.url}")
    private String kairosdbUrl;

    @Override
    public QueryResponse queryDataPointsTest(String metric) {
        try {
            HttpClient client = new HttpClient(kairosdbUrl);
            QueryBuilder builder = QueryBuilder.getInstance();
            builder.setStart(2, TimeUnit.MINUTES)
                    .addMetric(metric)
                    .addAggregator(AggregatorFactory
                            .createAverageAggregator(5, TimeUnit.MINUTES));
            QueryResponse query = client.query(builder);
            return query;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> queryDataPointsMetricNames() {
        try {
            HttpClient client = new HttpClient(kairosdbUrl);
            GetResponse metricNames = client.getMetricNames();
            return metricNames.getResults();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QueryResponse queryKairosDb(String metric, String func, String timeScale, Map<String, List<String>> tags) {
        try {
            HttpClient client = new HttpClient(kairosdbUrl);
            QueryBuilder builder = QueryBuilder.getInstance();
            builder.setStart(MonitorUtils.parseTimeScale(timeScale).getTime(), MonitorUtils.parseTimeScale(timeScale).getUnit())
                    .addMetric(metric)
                    .addMultiValuedTags(tags)
                    .addAggregator(createAggregator(func));
            QueryResponse query = client.query(builder);
            return query;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QueryResponse queryKairos(String metric, String func, String timeScale, List<Grouper> grouperList) {
        timeScale = StringUtils.isEmpty(timeScale) ? CommonConstants.DEFAULT_TIME_SCALE : timeScale;
        try {
            HttpClient client = new HttpClient(kairosdbUrl);
            QueryBuilder builder = QueryBuilder.getInstance();
            builder.setStart(MonitorUtils.parseTimeScale(timeScale).getTime(), MonitorUtils.parseTimeScale(timeScale).getUnit());
            QueryMetric queryMetric = builder.addMetric(metric);
            queryMetric.addAggregator(createAggregator(func));
            for (Grouper grouper : grouperList) {
                queryMetric.addGrouper(grouper);
            }
            QueryResponse query = client.query(builder);
            return query;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QueryResponse queryDataPointsBySingle(String metric, String timeScale, String func) {
        timeScale = StringUtils.isEmpty(timeScale) ? CommonConstants.DEFAULT_TIME_SCALE : timeScale;
        Map<String, List<String>> tags = new HashMap<>(16);
        QueryResponse queryResponse = queryKairosDb(metric, func, timeScale, tags);
        return queryResponse;
    }

    private SamplingAggregator createAggregator(String func) {
        try {
            Class aClass = Class.forName("org.kairosdb.client.builder.AggregatorFactory");
            Object obj = aClass.newInstance();
            Method[] methods = obj.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().contains("create" + StringUtils.firstUpName(func))) {
                    return (SamplingAggregator) method.invoke(obj, new Object[]{5, TimeUnit.MINUTES});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QueryResponse queryDataPointsByTimeScale(String timeScale) {
        return null;
    }

    @Override
    public QueryResponse queryDataPointsByFunc(String func) {
        return null;
    }

    @Override
    public QueryResponse queryDataPointsByTags(String metric, String timeScale, String func, Map<String, List<String>> tags) {
        timeScale = StringUtils.isEmpty(timeScale) ? CommonConstants.DEFAULT_TIME_SCALE : timeScale;
        QueryResponse queryResponse = queryKairosDb(metric, func, timeScale, tags);
        return queryResponse;
    }

    @Override
    public QueryResponse queryDataPointsByTag(String metric, String timeScale, String func, Map<String, String> tag) {
        timeScale = StringUtils.isEmpty(timeScale) ? CommonConstants.DEFAULT_TIME_SCALE : timeScale;
        Map<String, List<String>> tags = new HashMap<>();
        List<String> listAppId = new ArrayList<>();
        List<String> listHost = new ArrayList<>();
        List<String> listInstanceIp = new ArrayList<>();
        String appIdValue = tag.get(CommonConstants.TAG_APP_ID);
        String hostValue = tag.get(CommonConstants.TAG_HOST);
        String instanceIpValue = tag.get(CommonConstants.TAG_INSTANCE_IP);
        if (StringUtils.isNotEmpty(appIdValue)) {
            listAppId.add(appIdValue);
            tags.put(CommonConstants.TAG_APP_ID, listAppId);
        }
        if (StringUtils.isNotEmpty(hostValue)) {
            listHost.add(hostValue);
            tags.put(CommonConstants.TAG_HOST, listHost);
        }
        if (StringUtils.isNotEmpty(instanceIpValue)) {
            listInstanceIp.add(instanceIpValue);
            tags.put(CommonConstants.TAG_INSTANCE_IP, listInstanceIp);
        }
        QueryResponse queryResponse = queryKairosDb(metric, func, timeScale, tags);
        return queryResponse;
    }

}
