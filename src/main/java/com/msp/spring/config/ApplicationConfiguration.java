package com.msp.spring.config;

import com.msp.spring.database.entity.Company;
import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.database.repository.CrudRepository;
import com.msp.spring.database.repository.UserRepository;
import com.msp.spring.service.CompanyService;
import com.msp.spring.service.CountryService;
import com.msp.spring.service.UserService;
import com.msp.web.config.WebConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ImportResource("classpath:application.xml") // импорт бина driver
@Import(WebConfiguration.class) //импорт других файлов конфигурации
@Configuration()
@PropertySource("classpath:application.properties") // аналог <context:property-placeholder
@ComponentScan(basePackages = "com.msp.spring",
        useDefaultFilters = false,
        includeFilters = {
            @Filter(type = FilterType.ANNOTATION, classes = {Component.class, Service.class}),
            @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CrudRepository.class),
            @Filter(type = FilterType.REGEX, pattern = ".+Repository")
        })
public class ApplicationConfiguration implements ApplicationContextAware {

        private ApplicationContext applicationContext;

        @Bean
        @Scope(BeanDefinition.SCOPE_SINGLETON)
        public ConnectionPool pool(@Value("${db.pool.size}") int poolSize) {
                return new ConnectionPool("test-name", poolSize + 10);
        }

        @Bean
        public ConnectionPool pool1() {
                return new ConnectionPool("test-name-1", 25);
        }

        @Bean
        public UserRepository userRepository1() {
                // вернется один бин, потому что ApplicationConfiguration - proxy и метод pool1() повторно не вызываетя
                ConnectionPool pool = pool1();
                ConnectionPool pool1 = pool1();
                return new UserRepository(pool1());
        }

        @Bean
        @Profile("prod")
        public UserRepository userRepository2() {
                return new UserRepository(pool1());
        }

        @Bean
        public UserService userService(UserRepository userRepository,
                                       CrudRepository<Integer, Company> companyRepository,
                                       CompanyService companyService) {
                // без Service.class в @Filter(....) не автовайрится CompanyService ???
                // при useDefaultFilters = true - нормально
                return new UserService(userRepository, companyRepository, companyService);
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
                this.applicationContext = applicationContext;
        }
}
