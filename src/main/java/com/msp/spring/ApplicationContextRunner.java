package com.msp.spring;

import com.msp.spring.web.NoComponentBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan // ищет все классы с аннотацией ConfigurationProperties
@Slf4j
public class ApplicationContextRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationContextRunner.class, args);

        //Register bean in runtime - https://medium.com/@venkivenki4b6/spring-dynamically-register-beans-in-4-ways-at-run-time-c1dc45dcbeb9
        BeanDefinitionBuilder b = BeanDefinitionBuilder.rootBeanDefinition(NoComponentBean.class)
                                  .addPropertyValue("str", "myStringValue");
        if (context.getBeanFactory() instanceof DefaultListableBeanFactory) {
            ((DefaultListableBeanFactory)context.getBeanFactory()).registerBeanDefinition("noComponentBean", b.getBeanDefinition());
            log.info("noComponentBean: {}", context.getBean("noComponentBean", NoComponentBean.class));
        }

/*
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getAutowireCapableBeanFactory();
        registry.removeBeanDefinition("noComponentBean");
        try {
            log.info("noComponentBean: {}", context.getBean("noComponentBean", NoComponentBean.class));
        } catch (BeansException ex) {
            log.info("no noComponentBean found");
        }
*/

        //log.debug(context.getBean("webConfiguration", String.class));
/*
        SpringApplication app = new SpringApplication(ApplicationContextRunner.class);
        //app.setAdditionalProfiles("prod");
        app.setBannerMode(Banner.Mode.OFF);
        ConfigurableApplicationContext context = app.run(args);
        CompanyService companyService = context.getBean("companyService", CompanyService.class);
        log.info(companyService.toString());
        log.info(companyService.findById(123).toString());

        log.info(SpringProperties.getProperty("test.message"));
*/
    }


}

