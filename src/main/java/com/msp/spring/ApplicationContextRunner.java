package com.msp.spring;

import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.database.repository.CompanyRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        System.out.println(context.getBean("driver"));

        // name это alias: context->beanFactory->aliasName
        ConnectionPool connectionPool = context.getBean("pool", ConnectionPool.class);
        System.out.println(connectionPool);

        ConnectionPool connectionPool2 = context.getBean("pool2", ConnectionPool.class);
        System.out.println(connectionPool2);

        CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
        System.out.println(companyRepository);

    }

}
