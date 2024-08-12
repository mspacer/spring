package com.msp.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuditingBeanPostProcessors implements BeanPostProcessor {

    private final Map<String, Class<?>> auditingBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Auditing.class)) {
            auditingBeans.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // bean уже является прокси после TransactionBeanPostProcessors
        Class<?> originalClass = auditingBeans.get(beanName);

        if (originalClass != null) {
            return Proxy.newProxyInstance(originalClass.getClassLoader(), originalClass.getInterfaces(),
                    (proxy, method, args) ->  {
                        long nanoTime = System.nanoTime();
                        try {
                            System.out.println("Start auditing: " + nanoTime);
                            return method.invoke(bean, args);
                        } finally {
                            System.out.println("Stop auditing: " + (System.nanoTime() - nanoTime));
                        }
                    });
        }

        return bean;
    }
}
