package com.msp.spring;

import com.msp.spring.config.ApplicationConfiguration;
import com.msp.spring.service.CompanyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

        // используется базовый интерфейс
        CompanyService companyService = context.getBean("companyService", CompanyService.class);
        System.out.println(companyService.findById(11));

        context.close();
    }

}
