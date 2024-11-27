package com.msp.spring.service.exmpl3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.function.Supplier;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
public class Exmpl3Config {

    @Bean
    public SingleService singleService() {
        return new SingleService();
    }

    /*
    Если просто добавить к определению бина аннотацию @Scope(SCOPE_PROTOTYPE), и использовать этот бин в синглтоне
    через аннотацию @Autowired – будет создан только один объект. Потому что синглтон создается только однажды,
    и обращение к прототипу случится тоже однажды при его создании при внедрении зависимости.

    аттрибут proxyMode создает из класса прокси. В результате какждое обращение к методу класса будет идти через создание
    нового инстанса класса.
    proxyMode=TARGET_CLASS создание прокси с помощью наследования (CGLIB).
    proxyMode = INTERFACES -  c использованием интерфейсов
     */
    @Bean
    @Scope(value = SCOPE_PROTOTYPE/*, proxyMode = ScopedProxyMode.TARGET_CLASS*/)
    public ProtoService protoService() {
        return new ProtoService();
    }

    /*
    Второй вариант не требует proxyMode
    Supplier каждый раз обращается к контексту дать ему класс. Т.к. в контексте такого класса нет, спринг будет его создавать
     */
    @Bean
    Supplier<ProtoService> supplier() {
        return this::protoService;
    }

}
