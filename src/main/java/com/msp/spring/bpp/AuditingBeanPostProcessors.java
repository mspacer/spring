package com.msp.spring.bpp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuditingBeanPostProcessors implements BeanPostProcessor, Ordered {

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
                        if ("toString".equals(method.getName())) {
                            return method.invoke(bean, args);
                        }

                        long nanoTime = System.nanoTime();
                        try {
                            log.info("Start auditing: " + nanoTime);
                            return method.invoke(bean, args);
                        } finally {
                            log.info("Stop auditing: " + (System.nanoTime() - nanoTime));
                        }
                    });
        }

        return bean;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
