package com.ppdai.monitor.util;

import com.ppdai.monitor.common.TimeScaleConstant;
import org.kairosdb.client.builder.TimeUnit;

/**
 * @Author haijiang
 * @Description 校验工具类
 * @Date 2018/11/19
 **/
public class VerifyUtils {

    /**
     * 校验周期
     *
     * @param timeScale timeScale
     * @return boolean
     */
    public static boolean isTimeScale(String timeScale) {
        if (!timeScale.contains(StringUtils.UNDER_LINE)) {
            return false;
        }
        String[] split = timeScale.split(StringUtils.UNDER_LINE);
        for (TimeUnit timeUnit : TimeScaleConstant.TIMESCALE_FORMS){
            if(timeUnit.toString().contains(split[0].toUpperCase())){
                return true;
            }
        }
        return false;
    }

}
