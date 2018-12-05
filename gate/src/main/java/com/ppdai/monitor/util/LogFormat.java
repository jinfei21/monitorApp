package com.ppdai.monitor.util;

import org.springframework.util.StringUtils;

/**
 * @Author haijiang
 * @Description LogFormat
 * @Date 2018/09/18
 **/
public class LogFormat {

    /**
     * 创建日志对象
     *
     * @return
     */
    public static LogFormat createLog() {
        return new LogFormat();
    }

    /**
     * 序列号
     */
    private String serialNo;

    /**
     * 请求URI
     */
    private String path;

    /**
     * 请求报文
     */
    private String request;

    /**
     * 返回报文
     */
    private String response;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getPath() {
        return path;
    }

    public LogFormat setPath(String path) {
        this.path = path;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public LogFormat setRequest(String request) {
        this.request = request;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public LogFormat setResponse(String response) {
        this.response = response;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(this.serialNo)) {
            sb.append(String.format("serialNo=%s", this.serialNo));
        }
        sb.append(String.format(",path=%s", this.path));
        sb.append(String.format(",request=%s", request));
        sb.append(String.format(",response=%s", response));
        return sb.toString();
    }


}
