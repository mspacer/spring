package com.msp.spring;

import com.msp.spring.bfpp.LogBeanFactoryPostProcessor;
import com.msp.spring.bpp.example1.Parent;
import com.msp.spring.bpp.example1.Son;
import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.database.repository.CompanyRepository;
import com.msp.spring.database.repository.CrudRepository;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

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
        System.out.println("\n --------CONTEXT CREATED-------- \n");
        /* бины можно получать по базовому классу или интерфейсу
        Ошибка    available: expected single matching bean but found 3:
        поскольку объявлено три бина с таким интерфейсом
         */
        LogBeanFactoryPostProcessor /*BeanFactoryPostProcessor*/ bfpp = context.getBean(LogBeanFactoryPostProcessor.class/*BeanFactoryPostProcessor.class*/);
        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(bfpp.getClass()));

        System.out.println(context.getBean("driver"));

        // name это alias: context->beanFactory->aliasName
/*
        ConnectionPool connectionPool = context.getBean("pool", ConnectionPool.class);
        System.out.println(connectionPool);
*/

        ConnectionPool connectionPool2 = context.getBean("pool2", ConnectionPool.class);
        System.out.println(connectionPool2);

        // После созадния в TransactionBeanPostProcessors прокси на CompanyRepository ошибка, тк такого типа уже нет в контексте:
        // Bean named 'companyRepository' is expected to be of type 'com.msp.spring.database.repository.CompanyRepository' but was actually of type 'com.sun.proxy.$Proxy10'
        // CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);

        // используется базовый интерфейс
        CrudRepository companyRepository = context.getBean("companyRepository", CrudRepository.class);
        System.out.println(companyRepository.findById(11));

        /*
        На самом деле бина Parent нет, т.к. в xml он не конфигурится
         По имени интерфейса возвращается Son
         */
        Parent parent = context.getBean(Parent.class);
        System.out.println("parent exisis " + parent);

        Son son = context.getBean("son", Son.class);
        System.out.println("son exisis " + son);
        son.doRequest();

        context.close();
    }

}
