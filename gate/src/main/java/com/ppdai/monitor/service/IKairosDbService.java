package com.ppdai.monitor.service;

import org.kairosdb.client.builder.Grouper;
import org.kairosdb.client.response.QueryResponse;

import java.util.List;
import java.util.Map;

/**
 * @Author haijiang
 * @Description kairosdb的service接口
 * @Date 2018/11/15
 **/
public interface IKairosDbService {

    /**
     * 查询KairosDb中的数据
     *
     * @return QueryResponse
     */
    QueryResponse queryDataPointsTest(String metric);

    /**
     * 获取所有名字
     *
     * @return List<String>
     */
    List<String> queryDataPointsMetricNames();

    /**
     * 查询KairosDb
     *
     * @param metric    metric
     * @param func      func
     * @param timeScale timeScale
     * @param tags      tags
     * @return QueryResponse
     */
    QueryResponse queryKairosDb(String metric, String func, String timeScale, Map<String, List<String>> tags);

    /**
     * 查询KairosDb
     *
     * @param metric      metric
     * @param func        func
     * @param timeScale   timeScale
     * @param grouperList grouperList
     * @return QueryResponse
     */
    QueryResponse queryKairos(String metric, String func, String timeScale, List<Grouper> grouperList);

    /**
     * 通过metric查询
     *
     * @param metric    metric
     * @param timeScale timeScale
     * @param func      func
     * @return QueryResponse
     */
    QueryResponse queryDataPointsBySingle(String metric, String timeScale, String func);

    /**
     * 通过时间期间查询
     *
     * @param timeScale timeScale
     * @return QueryResponse
     */
    QueryResponse queryDataPointsByTimeScale(String timeScale);

    /**
     * 通过聚合函数查询
     *
     * @param func func
     * @return QueryResponse
     */
    QueryResponse queryDataPointsByFunc(String func);

    /**
     * 通过自定义参数查询(tags)
     *
     * @param metric    metric
     * @param func      func
     * @param timeScale timeScale
     * @param tags      tags
     * @return QueryResponse
     */
    QueryResponse queryDataPointsByTags(String metric, String timeScale, String func, Map<String, List<String>> tags);

    /**
     * 通过自定义参数查询(tag)
     *
     * @param metric    metric
     * @param timeScale timeScale
     * @param func      func
     * @param tag       tag
     * @return QueryResponse
     */
    QueryResponse queryDataPointsByTag(String metric, String timeScale, String func, Map<String, String> tag);

}
