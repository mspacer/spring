package com.msp.spring.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionBeanPostProcessors implements BeanPostProcessor, Ordered {

    private final Map<String, Class<?>> transactionsBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
/*
        if (bean.getClass().isAnnotationPresent(Transaction.class)) {
            //CompanyRepository.class превращается в какой-то $ProxyXX.class и
            // доступ по имени или типу "com.msp.spring.database.repository.CompanyRepository" больше не работает
            // Поэтому в этом случае на этапе Initializing callbacks жизненного цикла не будет вызова метода CompanyRepository.init() помеченного @PostConstruct
            // Таким образом, создание прокси объекта должно быть в методе postProcessAfterInitialization
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(),
                    (proxy, method, args) ->  {
                        try {
                            System.out.println("Open transaction");
                            return method.invoke(args);
                        } finally {
                            System.out.println("Close transaction");
                        }
                    });
        }
*/

        if (bean.getClass().isAnnotationPresent(Transaction.class)) {
            transactionsBeans.put(beanName, bean.getClass());
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // Если есть несколько bpp, делающих прокси, то сюда в качестве bean может прийти уже другой прокси,
        // у которого нет аннотации Transaction. Поэтому нужно хранить nap по ключу beanName первоначальных классов.
        // Это map возможно сделать только в postProcessBeforeInitialization, поскольку по принятому соглашению
        // в нем не может быть прокси и при первом заходе в него bean принимает оригинальный класс

        Class<?> originalClass = transactionsBeans.get(beanName);

        if (originalClass != null /*bean.getClass().isAnnotationPresent(Transaction.class)*/) {
            return Proxy.newProxyInstance(originalClass.getClassLoader(), originalClass.getInterfaces(),
                    (proxy, method, args) ->  {
                        try {
                            System.out.println("Open transaction");
                            return method.invoke(bean, args);
                        } finally {
                            System.out.println("Close transaction");
                        }
                    });
        }

        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
