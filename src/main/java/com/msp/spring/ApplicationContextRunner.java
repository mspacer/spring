package com.msp.spring;

import com.msp.spring.bfpp.LogBeanFactoryPostProcessor;
import com.msp.spring.config.ApplicationConfiguration;
import com.msp.spring.database.entity.Company;
import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.database.repository.CompanyRepository;
import com.msp.spring.database.repository.CrudRepository;
import javafx.application.Application;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.annotation.Annotation;

/**
 * <p>
 *    DI внедряет зависимости тремя способами
 *    <p>- параметры конструктора</p>
 *    <p>- параметры статического метода инициолизации (фабричный метод)</p>
 *    <p>- свойства объекта(set* методы)</p>
 * </p>
 */
public class ApplicationContextRunner {

    public static void main(String[] args) {
        // информация о бинах в context->beanFactory->beanDefinitionMap (singletonObjects)
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        //profiles
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ApplicationConfiguration.class);
        context.getEnvironment().setActiveProfiles(/*"web",*/ "prod");
        context.refresh();

        System.out.println(context.getBean("driver"));
        System.out.println(context.getBean("webConfiguration"));

        // используется базовый интерфейс
        CrudRepository companyRepository = context.getBean("companyRepository", CrudRepository.class);
        System.out.println(companyRepository.findById(11));

        context.close();
    }

}
