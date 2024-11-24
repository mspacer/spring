package com.msp.spring.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class PostInitInvokerListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("do onApplicationEvent()");
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, String> beanNames = new HashMap<>();
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(beanName -> {
                    String className = factory.getBeanDefinition(beanName).getBeanClassName();
                    beanNames.put(className, beanName);
                    return className;
                })
                .forEach(className -> {
                    try {
                        Arrays.stream(Class.forName(className).getDeclaredMethods ()) // унаследованные не нужжны getMethods()
                                .filter(method -> method.isAnnotationPresent(PostInitialisation.class))
                                .forEach(method -> {
                                    Object bean = applicationContext.getBean(beanNames.get(className));
                                    Class proxyClass = bean.getClass();
                                    try {
                                        Method method1 = proxyClass.getMethod(method.getName());
                                        method1.setAccessible(true);
                                        ReflectionUtils.invokeMethod(method1, bean);
                                    } catch (NoSuchMethodException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
