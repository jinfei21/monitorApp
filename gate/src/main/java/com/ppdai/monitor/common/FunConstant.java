package com.ppdai.monitor.common;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author haijiang
 * @Description
 * @Date 2018/11/19
 **/
public class FunConstant {

    public static final String FUN_FORMS_MIN = "min";
    public static final String FUN_FORMS_MAX = "max";
    public static final String FUN_FORMS_AVERAGE = "average";
    public static final String FUN_FORMS_STANDARD_DEVIATION = "standardDeviation";
    public static final String FUN_FORMS_SUM = "sum";
    public static final String FUN_FORMS_COUNT = "count";
    public static final String FUN_FORMS_LAST = "last";
    public static final String FUN_FORMS_FIRST = "first";
    public static final String FUN_FORMS_GAPS = "gaps";
    public static final String FUN_FORMS_LEASTSQUARES = "leastSquares";
    public static Set<String> FUN_FORMS = new HashSet();

    public FunConstant() {
    }

    static {
        FUN_FORMS.add(FUN_FORMS_MIN);
        FUN_FORMS.add(FUN_FORMS_MAX);
        FUN_FORMS.add(FUN_FORMS_AVERAGE);
        FUN_FORMS.add(FUN_FORMS_STANDARD_DEVIATION);
        FUN_FORMS.add(FUN_FORMS_SUM);
        FUN_FORMS.add(FUN_FORMS_COUNT);
        FUN_FORMS.add(FUN_FORMS_LAST);
        FUN_FORMS.add(FUN_FORMS_FIRST);
        FUN_FORMS.add(FUN_FORMS_GAPS);
        FUN_FORMS.add(FUN_FORMS_LEASTSQUARES);
    }
}
