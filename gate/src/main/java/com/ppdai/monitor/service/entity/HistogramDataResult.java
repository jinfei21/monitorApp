package com.ppdai.monitor.service.entity;

import java.util.List;

/**
 * @Author haijiang
 * @Description 柱状图数据
 * @Date 2018/11/27
 **/
public class HistogramDataResult {
    private String name;
    private List<Long> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }
}
