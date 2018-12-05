package com.ppdai.monitor.service.entity;

import java.util.List;

/**
 * @Author haijiang
 * @Description 柱状图
 * @Date 2018/11/27
 **/
public class HistogramBaseResult extends BaseResult {
    private List<String> tags;
    private List<String> xValues;
    private List<HistogramDataResult> series;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getxValues() {
        return xValues;
    }

    public void setxValues(List<String> xValues) {
        this.xValues = xValues;
    }

    public List<HistogramDataResult> getSeries() {
        return series;
    }

    public void setSeries(List<HistogramDataResult> series) {
        this.series = series;
    }
}
