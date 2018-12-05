package com.ppdai.monitor.common;

import com.ppdai.monitor.util.StringUtils;
import org.kairosdb.client.builder.TimeUnit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author haijiang
 * @Description 周期
 * @Date 2018/11/19
 **/
public class TimeScaleConstant {
    public static List<TimeUnit> TIMESCALE_FORMS = new ArrayList<>();
    public static String TIMESCALE = "";

    public TimeScaleConstant() {
    }

    static {
        TIMESCALE_FORMS.add(TimeUnit.DAYS);
        TIMESCALE_FORMS.add(TimeUnit.WEEKS);
        TIMESCALE_FORMS.add(TimeUnit.MONTHS);
        TIMESCALE_FORMS.add(TimeUnit.MINUTES);
        TIMESCALE_FORMS.add(TimeUnit.MILLISECONDS);
        TIMESCALE_FORMS.add(TimeUnit.HOURS);
        TIMESCALE_FORMS.add(TimeUnit.SECONDS);
        TIMESCALE_FORMS.add(TimeUnit.YEARS);
    }

    public static String timeScaleStr(){
        StringBuilder sb = new StringBuilder();
        for (TimeUnit timeUnit: TIMESCALE_FORMS) {
            sb.append(", ");
            sb.append(timeUnit.toString());
            sb.append(StringUtils.UNDER_LINE);
            sb.append("xxx");
        }
        return sb.substring(2).toLowerCase();
    }
}
