package com.ppdai.monitor.util;

import java.util.Collection;
import java.util.Map;

/**
 * @Author haijiang
 * @Description 字符串工具类
 * @Date 2018/09/20
 **/
public class StringUtils {
    public static final String EMPTY = "";
    public static final String COMMA = ",";
    public static final String UNDER_LINE = "-";

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            return "".equals(str) || null == str;
        } else if (obj instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) obj;
            return collection.isEmpty() || collection == null || collection.size() == 0;
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            return map.isEmpty() || map == null || map.size() == 0;
        }
        return null == obj;
    }

    /**
     * 将首字母大写
     *
     * @param name name
     * @return String
     */
    public static String firstUpName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
