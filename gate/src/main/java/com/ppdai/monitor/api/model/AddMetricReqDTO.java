package com.ppdai.monitor.api.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @Author haijiang
 * @Description 添加指标请求类
 * @Date 2018/11/13
 **/
@Api(value = "AddMetricReqDTO", description = "添加指标请求类")
public class AddMetricReqDTO {

    @ApiModelProperty(value = "系统指标id")
    private Long sId;

    @ApiModelProperty(value = "标题，例如：请求错误数(min)")
    private String title;

    @ApiModelProperty(value = "指标名称，例如：order.cost.time")
    private String metric;

    @ApiModelProperty(value = "图形类型，传入line、histogram等，后续更新文档会告知，line:折线图，histogram:柱状图")
    private String chart;

    @ApiModelProperty(value = "聚合函数，传入max或者sum等等，max:最大值，sum:总和等，输入错误后接口会有提示")
    private String func;

    @ApiModelProperty(value = "标签名称(参数：appId、host、instance_ip可以为空，按照需要传)",example = "{\"appId\":[\"xxx\"],\"host\":[\"xxx\"],\"instance_ip\":[\"xxx\"]}")
    private Map<String, List<String>> tagName;

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
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

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public Map<String, List<String>> getTagName() {
        return tagName;
    }

    public void setTagName(Map<String, List<String>> tagName) {
        this.tagName = tagName;
    }
}
