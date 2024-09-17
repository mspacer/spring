package com.msp.spring.web.configuration;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
//@Profile("web")
public class WebConfiguration implements WebMvcConfigurer {

/*
    @Bean
    public String webConfiguration() {
        return "It`s import from webConfiguration";
    }
*/

    //регистрация интерцепторов
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public Validator validator2(AutowireCapableBeanFactory beanFactory) {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory))
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
      //  registry.addConverter(Jsr310Converters.StringToLocalDateConverter.INSTANCE);
       // registry.addConverter(String.class, LocalDate.class,
       //         source -> LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    //Специальный интерцептор, отслеживающий локаль. Требует реализацию LocaleResolver.
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public LocaleResolver localeResolver() {
        // один из вариантов реализации LocaleResolver
        // определяет способ хранения локали
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.US);
        return cookieLocaleResolver;
    }
}
