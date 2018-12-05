package com.ppdai.monitor.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;

/**
 * @Author haijiang
 * @Description 请求工具类
 * @Date 2018/10/17
 **/
public class ServletContextUtils {

    /**
     * 调用方应用ID
     */
    public static final String CALLER_APPID = "X-GW-APPID";

    /**
     * 租户id
     */
    public static final String CALLER_TENANTID = "X-GW-TENANTID";

    /**
     * 调用方应用版本
     */
    public static final String CALLER_APPVERSION = "X-PPD-APPVERSION";

    /**
     * 调用方设备ID
     */
    public static final String CALLER_DEVICEID = "TENANET-DEVICEID";

    /**
     * 调用方请求加签
     */
    public static final String CALLER_SIGN = "X-PPD-SIGN";

    /**
     * 调用方密钥ID
     */
    public static final String CALLER_KEY = "X-PPD-KEY";

    /**
     * 调用方密钥版本
     */
    public static final String CALLER_KEYVERSION = "X-PPD-KEYVERSION";

    /**
     * 调用IP链
     */
    public static final String CALLER_IP = "X-Forwarded-For";

    /**
     * 调用方真实IP
     */
    public static final String CLIENT_IP = "X-PPD-IPADDRESS";

    /**
     * 请求的UA
     */
    public static final String CALLER_USERAGENT = "X-PPD-USERAGENT";

    /**
     * Token
     */
    public static final String CALLER_TOKEN = "X-PPD-TOKEN";

    /**
     * 请求Via_Getaway
     */
    public static final String CALLER_VIA_GATEWAY = "X-PPD-VIAGATEWAY";

    /**
     * 请求头 Referer
     */
    public static final String CALLER_REFERER = "Referer";

    /**
     * HTTP 请求的 appid 参数
     */
    public static final String REQUEST_APPID = "appid";

    /**
     * 请求头 X-PPD-TIMESTAMP  时间戳
     */
    public static final String CALLER_TIMESTAMP = "X-PPD-TIMESTAMP";

    /**
     * 请求头 X-PASSPORT-SIGN  时间戳
     */
    public static final String CALLER_PASSPORT_SIGN = "X-PASSPORT-SIGN";

    /**
     * unKnown
     */
    public static final String UN_KNOWN = "unKnown";


    /**
     * 获取当前请求上下文
     *
     * @return 当前请求上下文
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    /**
     * 获取当前上下文请求流水号
     */
    public static String getCurrentRequestSerialNo() {
        HttpServletRequest currentRequest = getCurrentRequest();
        String result = "";
        if (currentRequest != null) {
            Object serialNo = currentRequest.getAttribute("serial_no");
            if (serialNo != null || serialNo instanceof String) {
                result = serialNo.toString();
            }
        }
        return result;
    }

    /**
     * 从获取头获取参数
     *
     * @param key 参数key
     * @return
     */
    public static String getHeader(String key) {
        HttpServletRequest currentRequest = getCurrentRequest();
        if (currentRequest != null) {
            return currentRequest.getHeader(key);
        }
        return null;
    }

    /**
     * 获取调用方应用ID
     *
     * @return
     */
    public static String getCallerAppid() {
        return getHeader(CALLER_APPID);
    }

    /**
     * 获取调用方应用租户id
     *
     * @return
     */
    public static String getCallerTenantId() {
        return getHeader(CALLER_TENANTID);
    }

    /**
     * 获取调用方版本
     *
     * @return
     */
    public static String getCallerAppVersion() {
        return getHeader(CALLER_APPVERSION);
    }

    /**
     * 获取调用方设备号
     *
     * @return
     */
    public static String getCallerDeviceid() {
        return getHeader(CALLER_DEVICEID);
    }

    /**
     * 获取请求加签
     *
     * @return
     */
    public static String getCallerSign() {
        return getHeader(CALLER_SIGN);
    }

    /**
     * 获取请求密钥ID
     *
     * @return
     */
    public static String getCallerKey() {
        return getHeader(CALLER_KEY);
    }

    /**
     * 获取请求密钥版本
     *
     * @return
     */
    public static String getCallerKeyVersion() {
        return getHeader(CALLER_KEYVERSION);
    }

    /**
     * 获取调用方IP
     *
     * @return
     */
    public static String getCallerIP() {
        String ip = getHeader(CLIENT_IP);
        if (StringUtils.isNotEmpty(ip)) {
            return ip;
        }
        return getIp();
    }

    /**
     * 获取客户端真实IP
     *
     * @return
     */
    public static String getIp() {
        HttpServletRequest request = getCurrentRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !UN_KNOWN.equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !UN_KNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * IP调用链
     *
     * @return
     */
    public static String getCallerIPS() {
        return getHeader(CALLER_IP);
    }

    /**
     * 获取调用方UserAgent
     */
    public static String getCallerUserAgent() {
        return getHeader(CALLER_USERAGENT);
    }

    /**
     * 获取调用方Token
     */
    public static String getCallerToken() {
        return getHeader(CALLER_TOKEN);
    }

    /**
     * 获取调用方ViaGateway
     */
    public static String getCallerViaGateway() {
        return getHeader(CALLER_VIA_GATEWAY);
    }

    /**
     * 获取当前项目的部署地址
     */
    public static String getApplicationPath() {
        URL resource = ServletContextUtils.class.getClassLoader().getResource("");
        if (resource != null) {
            return resource.toString();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取当前请求的RefereceUrl
     */
    public static String getCallerRefrenceUrl() {
        return getHeader(CALLER_REFERER);
    }


    /**
     * 获取调用方AppId
     */
    public static String getHttpAppId() {
        String appid = getCurrentRequest().getParameter(REQUEST_APPID);
        if (StringUtils.isEmpty(appid)) {
            appid = getCallerAppid();
        }
        return appid;
    }

    /**
     * 获取请求时间戳
     *
     * @return
     */
    public static String getCallerTimestamp() {
        return getHeader(CALLER_TIMESTAMP);
    }

    /**
     * 获取请求加签
     *
     * @return
     */
    public static String getCallerPassportSign() {
        return getHeader(CALLER_PASSPORT_SIGN);
    }

    /**
     * 获取当前请求上下文对应的响应实体
     *
     * @return 当前Response实体
     */
    public static HttpServletResponse getCurrentResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletResponse response = requestAttributes.getResponse();
        return response;
    }

}
