package com.ppdai.monitor.aspect;

import com.alibaba.fastjson.JSON;
import com.ppdai.monitor.common.ApiResult;
import com.ppdai.monitor.common.BusinessException;
import com.ppdai.monitor.common.ErrorEnum;
import com.ppdai.monitor.util.LogFormat;
import com.ppdai.monitor.util.ServletContextUtils;
import com.ppdai.monitor.util.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author haijiang
 * @Description 数据统计、日志记录  相关拦截器
 * @Date 2018/09/18
 **/
@Component
@Aspect
@Order(1)
public class LogAspect {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义拦截规则：拦截com.tenant.common.api包下面的所有类
     */
    @Pointcut("execution(* com.ppdai.monitor.api..*(..))")
    public void apiMethodPointcut() {
    }

    @Before(value = "apiMethodPointcut()")
    public void before(JoinPoint joinPoint) {
    }

    /**
     * 统一记录未知异常
     */
    @Around("apiMethodPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest currentRequest = ServletContextUtils.getCurrentRequest();
        ConcurrentHashMap<String, Object> param = new ConcurrentHashMap<>(16);
        Object result = null;
        boolean hasError = false;
        long start = System.currentTimeMillis();
        LogFormat logFormat = LogFormat.createLog();
        logFormat.setPath(currentRequest.getRequestURI());
        logFormat.setSerialNo(UUID.randomUUID().toString());
        try {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                int i = 0;
                for (Object arg : args) {
                    if (arg instanceof BindingResult || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                        continue;
                    }
                    if (arg != null) {
                        param.put(arg.getClass().getName(), arg);
                    }
                    i++;
                }
            }
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            long cost = System.currentTimeMillis() - start;
            Map<String, Object> copyParam = new HashMap<>(param);
            hasError = true;
            logFormat.setRequest(JSON.toJSONString(copyParam)).setResponse(JSON.toJSONString(result));
            if (ex instanceof BusinessException) {
                BusinessException businessException = (BusinessException) ex;
                result = new ApiResult(businessException.getErrorCode(), businessException.getErrorMsg());
                if (!(businessException.getErrorCode() >= ErrorEnum.EXCEPTION_100200.getErrorCode() && businessException.getErrorCode() < ErrorEnum.EXCEPTION_100300.getErrorCode())) {
                    logger.error("api={}【cost:{}ms】,details:{}", currentRequest.getRequestURL(), cost, logFormat.toString(), ex);
                }
            } else {
                logger.error("api={}【cost:{}ms】,details:{}", currentRequest.getRequestURL(), cost, logFormat.toString(), ex);
                result = new ApiResult(ErrorEnum.EXCEPTION.getErrorCode(), StringUtils.isEmpty(ex.getMessage()) ? ErrorEnum.EXCEPTION.getErrorMsg() : ex.getMessage());
            }
        } finally {
            long cost = System.currentTimeMillis() - start;
            if (result instanceof ApiResult) {
                ApiResult api = (ApiResult) result;
                api.setSerialNo(logFormat.getSerialNo());
            }
            if (!hasError) {
                logFormat.setRequest(JSON.toJSONString(param)).setResponse(JSON.toJSONString(result));
                logger.info("api={}【cost:{}ms】,details:{}", currentRequest.getRequestURL(), cost, logFormat.toString());
            }
        }
        return result;
    }

    @AfterReturning(value = "apiMethodPointcut()", returning = "returnVal")
    public void afterReturing(JoinPoint joinPoint, Object returnVal) {
    }


}
