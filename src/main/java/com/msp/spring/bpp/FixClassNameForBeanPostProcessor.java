package com.msp.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class FixClassNameForBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);

        if (beanDefinition != null && beanDefinition.getBeanClassName() == null) {
            beanDefinition.setBeanClassName(bean.getClass().getName());
        }

        return bean;
    }
}
