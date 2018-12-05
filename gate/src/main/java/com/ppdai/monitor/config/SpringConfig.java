package com.ppdai.monitor.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author haijiang
 * @Description spring工具类
 * @Date 2018/09/21
 **/
@Component
public class SpringConfig implements ApplicationContextAware {

    /**
     * spring实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringConfig.applicationContext == null) {
            SpringConfig.applicationContext = applicationContext;
        }
    }

    /**
     * 获取applicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
