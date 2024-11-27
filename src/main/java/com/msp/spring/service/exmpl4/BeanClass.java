package com.msp.spring.service.exmpl4;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BeanClass {
    Class<?> value();
}
