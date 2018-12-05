package com.ppdai.monitor.service.entity;

import java.util.List;

/**
 * @Author haijiang
 * @Description 饼状图
 * @Date 2018/11/27
 **/
public class PieBaseResult extends BaseResult {
    private List<String> names;
    private List<PieDataResult> data;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<PieDataResult> getData() {
        return data;
    }

    public void setData(List<PieDataResult> data) {
        this.data = data;
    }
}
