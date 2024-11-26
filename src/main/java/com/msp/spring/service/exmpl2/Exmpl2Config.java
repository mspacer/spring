package com.msp.spring.service.exmpl2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class Exmpl2Config {

    /*
    @Value и @PostConstruct не сработают, если BFPP не будут статик.
    BFPP создаются первыми. В данном случае чтобы создать  PropertySourcesPlaceholderConfigurer (обрабатывает @Value)
     и Example2BeanFactoryPostProcessor
    нужно иметь Exmpl2Config и CommonAnnotationBeanPostProcessor (Обрабатывает PostConstruct)
    Exmpl2Config без статик BFPP создастся первым, но ни Value ни PostConstruct не отработают, т.к. нет соответствующих BFPP
    */

    @Value("${JAVA_HOME}")
    private String javaHome;

    @PostConstruct
    private void init() {
        System.out.println("Exmpl2Config javaHome: " + javaHome);
    }

    @Bean
    public SomeService someService() {
        return new SomeService();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public /*static*/ Example2BeanFactoryPostProcessor example2BeanFactoryPostProcessor() {
        return new Example2BeanFactoryPostProcessor();
    }
}
