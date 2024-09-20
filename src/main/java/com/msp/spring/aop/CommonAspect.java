package com.msp.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CommonAspect {

    /*
    @within - check annotation on the class level
    проверяется наличие указанной аннотации над классом
 */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    /*
        within - check class type name
        тоже самое только для класса
        com.msp.spring.service.. - вторая точка означает искать в подкаталогах
        *Service - классы оканчивающиеся на Service
     */
    @Pointcut("within(com.msp.spring.service.*Service)")
    public void isServiceLayer() {
    }

    /*
        this - check AOP proxy class type (указывает на конечный объект) (прокси-объект реализует указанный интерфейс)
        target - check target object class type - может быть интерфейсом или исходным объектом (объект реализует указанный интерфейс)
     */
    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("target(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer() {
    }

}
