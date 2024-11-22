package com.msp.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;

public class InjectBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(InjectBean.class))
                .forEach(field -> {
                    System.out.println("inject to bean: " + bean + " beanName: " + beanName);
                    Object contextBean = applicationContext.getBean(field.getType());
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, bean, contextBean);
/*
                    field.setAccessible(true);
                    try {
                        field.set(bean, contextBean);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
*/
                });
        //в postProcessBeforeInitialization следует возвращать оригинальный бин, а не обертку.
        //обертку детать в postProcessAfterInitialization
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
