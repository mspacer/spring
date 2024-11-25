package com.msp.spring.bpp.example2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.MethodMetadataReadingVisitor;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class InitMethodRegistryBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            Arrays.stream(beanFactory.getBeanDefinitionNames())
                  .forEach(beanName -> {
                      try {
                          BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                          String beanClassName = beanDefinition.getBeanClassName();
                          if (beanClassName == null) {
                              beanClassName = evaluateBeanClassName(beanDefinition);
                          }
                          Class beanClass = Class.forName(beanClassName);
                          Arrays.stream(ClassUtils.getAllInterfacesForClass(beanClass))
                                  .forEach(aClass -> {
                                      Arrays.stream(aClass.getMethods())
                                              .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                                              .forEach(method -> beanDefinition.setInitMethodName(method.getName()));
                                  });
                      } catch (ClassNotFoundException e) {
                          throw new RuntimeException(e);
                      } catch (Exception ex) {
                          ex.printStackTrace();
                      }
                  });
    }

    private String evaluateBeanClassName(BeanDefinition beanDefinition) {
        try {
            Object reader = Class.forName("org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader$ConfigurationClassBeanDefinition")
                    .cast(beanDefinition);
            Field factoryMethodMetadata = reader.getClass().getDeclaredField("factoryMethodMetadata");
            factoryMethodMetadata.setAccessible(true);
            MethodMetadata visitor = (MethodMetadata) factoryMethodMetadata.get(reader);
            return visitor.getReturnTypeName();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
