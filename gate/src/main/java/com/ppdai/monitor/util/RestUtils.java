package com.ppdai.monitor.util;


import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author haijiang
 * @Description rest工具类
 * @Date 2018/11/14
 **/
public class RestUtils {

    /**
     * 通过restTemplate发送请求
     *
     * @param restTemplate restTemplate
     * @param url          url
     * @param map          请求参数
     * @param clazz        将json转换为的返回类
     * @param <T>          返回类class
     * @return 返回类
     */
    public static <T> T send(RestTemplate restTemplate, String url, Map<String, Object> map, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(url, request, String.class);
        String body = entity.getBody();
        try {
            String result  = new String(body.getBytes("ISO-8859-1"), "UTF-8");
            return JSON.parseObject(result, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
