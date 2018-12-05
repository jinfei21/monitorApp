package com.ppdai.monitor.service.entity;

import java.util.List;

/**
 * @Author haijiang
 * @Description 线性图
 * @Date 2018/11/27
 **/
public class LineBaseResult extends BaseResult {
    private List<String> tags;
    private List<String> xValues;
    private List<LineDataResult> series;

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

    public List<LineDataResult> getSeries() {
        return series;
    }

    public void setSeries(List<LineDataResult> series) {
        this.series = series;
    }
}
