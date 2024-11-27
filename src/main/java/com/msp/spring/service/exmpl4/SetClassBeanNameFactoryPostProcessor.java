package com.msp.spring.service.exmpl4;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.MethodMetadata;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Устанавливает имя класса в beanDefinition на указанный в {@link BeanClass}
 */
@Component
public class SetClassBeanNameFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Arrays.stream(beanFactory.getBeanDefinitionNames())
                .forEach(beanName -> {
                    String beanClassName = beanFactory.getBeanDefinition(beanName).getBeanClassName();
                    if (beanClassName != null) {
                        try {
                            Class<?> aClass = Class.forName(beanClassName);
                            BeanClass annotation = aClass.getAnnotation(BeanClass.class);
                            if (annotation != null) {
                                beanFactory.getBeanDefinition(beanName).setBeanClassName(annotation.value().getName());
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        beanFactory.getBeanDefinition(beanName).setBeanClassName(evaluateBeanName(beanFactory.getBeanDefinition(beanName)));
                    }
                } );
    }

    private String evaluateBeanName(BeanDefinition beanDefinition) {

        try {
            Object reader = Class.forName("org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader$ConfigurationClassBeanDefinition")
                    .cast(beanDefinition);
            Field factoryMethodMetadata = reader.getClass().getDeclaredField("factoryMethodMetadata");
            factoryMethodMetadata.setAccessible(true);
            MethodMetadata visitor = (MethodMetadata) factoryMethodMetadata.get(reader);
            MergedAnnotation<BeanClass> beanClassMergedAnnotation = visitor.getAnnotations().get(BeanClass.class);
            if (beanClassMergedAnnotation.isPresent()) {
                Class<?> value = (Class)beanClassMergedAnnotation.getValue("value").orElse(null);
                if (value != null) {
                    //beanDefinition.getClass().getClassLoader().loadClass(value.toString());
                    //Class<?> aClass = Class.forName(value.toString());
                    return value.getName();
                } else
                    return null;
            }
            return null;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
