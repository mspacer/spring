package com.msp.spring.config;

import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.web.configuration.WebConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Import(WebConfiguration.class) //импорт других файлов конфигурации
@Configuration()
//@PropertySource("classpath:application.properties") - application.properties (зарезервированное название) обрабатывается авто конфигурацией
/* обрабатывается @SpringBootApplication
@ComponentScan(basePackages = "com.msp.spring",
        useDefaultFilters = false,
        includeFilters = {
            @Filter(type = FilterType.ANNOTATION, classes = {Component.class, Service.class}),
            @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CrudRepository.class),
            @Filter(type = FilterType.REGEX, pattern = ".+Repository")
        })
*/
@EnableMethodSecurity
public class ApplicationConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public ConnectionPool pool(@Value("${db.pool.size}") int poolSize) {
        //не работает поскольку в конструкторе стоят @Value
        return new ConnectionPool("test-name", "testPwd",poolSize + 10);
    }

/*
    @Bean
    @ConfigurationProperties("db")
    public DatabaseProperties databaseProperties() {
        return new DatabaseProperties();
    }
*/

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
