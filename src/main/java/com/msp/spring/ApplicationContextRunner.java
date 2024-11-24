package com.msp.spring;

import com.msp.spring.config.ApplicationConfiguration;
import com.msp.spring.database.repository.CrudRepository;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * <p>
 * DI внедряет зависимости тремя способами
 * <p>- параметры конструктора</p>
 * <p>- параметры статического метода инициолизации (фабричный метод)</p>
 * <p>- свойства объекта(set* методы)</p>
 * </p>
 */
public class ApplicationContextRunner {

    public static void main(String[] args) {
        // информация о бинах в context->beanFactory->beanDefinitionMap (singletonObjects)
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        //profiles
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ApplicationConfiguration.class);
        context.getEnvironment().setActiveProfiles("web", "prod");
        context.refresh();

        System.out.println(context.getBean("driver"));
        System.out.println(context.getBean("webConfiguration"));

        // используется базовый интерфейс
        CrudRepository companyRepository = context.getBean("companyRepository", CrudRepository.class);
        System.out.println(companyRepository.findById(11));

        /*
        Если бин объявлен в java-конфигурации, в beanDefinition отсутствует информация о его классе.
        Это связано со спецификой java - пока не будет вызван метод неизвестен возвращаемы тип.
        В xml-конфигурации класс прописывает явно в параметре "class", аннотированный (например @Component) бин также имеет класс.
        Решение - костыль в виде BPP, в котором руками устанавливать класс, т.к. на этом этапе он уже известен.
        Метод не работает для бин фектори пост процессоров
         */
        System.out.println("---- beanDefinition.getBeanClassName() == null");
        Iterator<String> beanNamesIterator = context.getBeanFactory().getBeanNamesIterator();
        //beanNamesIterator.forEachRemaining(beanName -> {
        Stream<String> stream = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        beanNamesIterator,
                        Spliterator.ORDERED),
                false);
        // Stream.generate(beanNamesIterator::next);
        stream.forEach(beanName -> {
            try {
                BeanDefinition beanDefinition = context.getBeanFactory().getBeanDefinition(beanName);
                if (beanDefinition.getBeanClassName() == null) {
                    System.out.println(beanName);
                }
            } catch (Exception e) {
            }
        });
        System.out.println("--------");
        context.close();
    }

}
