package com.geoServer.conmmon;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * main 中获取容器中的对象
 * ddService=SpringBeanFactoryUtils.getBean(ddService.class)
 */
@Component
public class SpringBeanFactoryUtils implements ApplicationContextAware {
    public static ApplicationContext applicationContextCurrent = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContextCurrent == null)
            this.applicationContextCurrent = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContextCurrent;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //根据类型（@Autowired）
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
