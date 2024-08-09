package com.msp.spring;

import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.database.repository.CompanyRepository;
import com.msp.spring.database.repository.UserRepository;
import com.msp.spring.ioc.Container;
import com.msp.spring.service.UserService;

/**
 * <p>Inversion of Control - принцип программирования, при котором управлением выполнения программы (созданием объектов в частности) занимается фреймворк, а не программист
 * Происходит потеря контроля над управлением кодом: фреймворк управляет кодом программиста. Однако программист сосредотачивается над написанием бизнесслогики.
 *
 * <p>IoC Spring - Dependency Injection (DI)
 * DI внедряет зависимости через конструктор класса, параметры статического метода инициализации (фабричный метод), свойства объекта (set-методы)
 *
 * <p>IoC Container - объект, который занимается созданием других объектов (Bean типа Controller, Service, Repository) и внедрением в них зависимостей (DI).
 * Представляет собой ассоциативный массив (Map).
 *
 * <p>IoC Container-у требуется метаинформация (Bean Definitions), которая описывае как создавать Beans, конфигурировать и внедрять в них зависимости.
 * Работает на основе Reflection API
 * Существует три реализации метаинформации: XML-based, Annotation-based, Java-based
 *
 * <p>IoC Container pеализует интерфейсы org.springframework.beans.factory.BeanFactory и org.springframework.context.ApplicationContext
 */
public class ApplicationRunner {

    public static void main(String[] args) {
        // Программист создает объекты и внедряет зависимости.
        // это может делать фреймворк

/*
        ConnectionPool pool = new ConnectionPool();
        UserRepository userRepository = new UserRepository(pool);
        CompanyRepository companyRepository = new CompanyRepository(pool);
        UserService userService = new UserService(userRepository, companyRepository);
*/
        UserService userService = Container.get(UserService.class);

        System.out.println(userService);
    }
}
