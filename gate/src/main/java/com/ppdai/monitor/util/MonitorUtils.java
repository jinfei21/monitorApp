package com.ppdai.monitor.util;

import com.ppdai.monitor.common.TimeScaleConstant;
import com.ppdai.monitor.service.entity.TimeScale;
import org.kairosdb.client.builder.TimeUnit;

/**
 * @Author haijiang
 * @Description 监控工具类
 * @Date 2018/11/19
 **/
public class MonitorUtils {

    /**
     * 解析周期
     *
     * @param timeScale timeScale
     * @return TimeScale
     */
    public static TimeScale parseTimeScale(String timeScale) {
        TimeScale tmeScale = new TimeScale();
        String[] split = timeScale.split(StringUtils.UNDER_LINE);
        for (TimeUnit timeUnit : TimeScaleConstant.TIMESCALE_FORMS) {
            if (timeUnit.toString().contains(split[0].toUpperCase())) {
                tmeScale.setTime(Integer.valueOf(split[1]));
                tmeScale.setUnit(timeUnit);
            }
        }
        return tmeScale;
    }
}
