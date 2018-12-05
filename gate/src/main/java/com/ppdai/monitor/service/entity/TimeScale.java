package com.ppdai.monitor.service.entity;

import org.kairosdb.client.builder.TimeUnit;

/**
 * @Author haijiang
 * @Description 周期类
 * @Date 2018/11/19
 **/
public class TimeScale {

    private int time;
    private TimeUnit unit;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }
}
