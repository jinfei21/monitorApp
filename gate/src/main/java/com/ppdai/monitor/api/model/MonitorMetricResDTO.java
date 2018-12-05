package com.ppdai.monitor.api.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @Author haijiang
 * @Description 监控系统详细信息响应类
 * @Date 2018/11/13
 **/
@Api(value = "MonitorMetricResDTO", description = "监控系统详细信息响应类")
public class MonitorMetricResDTO {

    @ApiModelProperty(value = "监控指标id")
    private Long mId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(name = "名称")
    private String metric;

    @ApiModelProperty(name = "走势图")
    private String chart;

    @ApiModelProperty(name = "聚合函数")
    private String aggFun;

    @ApiModelProperty(name = "维度名称")
    private String tags;

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getAggFun() {
        return aggFun;
    }

    public void setAggFun(String aggFun) {
        this.aggFun = aggFun;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
