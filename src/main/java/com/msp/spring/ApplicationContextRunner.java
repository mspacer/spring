package com.msp.spring;

import com.msp.spring.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.SpringProperties;

@SpringBootApplication
@ConfigurationPropertiesScan // ищет все классы с аннотацией ConfigurationProperties
@Slf4j
public class ApplicationContextRunner {

    public static void main(String[] args) {
        //ConfigurableApplicationContext context = SpringApplication.run(ApplicationContextRunner.class, args);
        SpringApplication app = new SpringApplication(ApplicationContextRunner.class);
        app.setAdditionalProfiles("prod");
        app.setBannerMode(Banner.Mode.OFF);
        ConfigurableApplicationContext context =app.run(args);
        CompanyService companyService = context.getBean("companyService", CompanyService.class);
        log.info(companyService.toString());
        log.info(companyService.findById(123).toString());

        log.info(SpringProperties.getProperty("test.message"));
        context.close();
    }

}
