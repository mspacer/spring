package com.msp.spring.service.exmpl4;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * меняет класс в beanDefinition на указанный в {@link DeprecatedClass}
 */
@Component
public class DeprecatedClassBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Arrays.stream(beanFactory.getBeanDefinitionNames())
                .forEach(beanName -> {
                    String beanClassName = beanFactory.getBeanDefinition(beanName).getBeanClassName();
                    if (beanClassName != null) {
                        try {
                            Class<?> aClass = Class.forName(beanClassName);
                            DeprecatedClass annotation = aClass.getAnnotation(DeprecatedClass.class);
                            if (annotation != null) {
                                beanFactory.getBeanDefinition(beanName).setBeanClassName(annotation.newImpl().getName());
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } );
    }
}
