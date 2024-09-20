package com.msp.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@Order(1)
public class FirstAspect {

    public static String argsToString(JoinPoint joinPoint) {
        String args = Arrays.stream(joinPoint.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        return "joinPoint " + joinPoint.toString() + ", args=[" + args + "]";
    }

    /*
        @annotation - check annotation on method level
        чтобы spring не смотрел все бины, ограничиваем уровнем контроллеров, в итоге:
        "классы контроллеров с методами помеченными аннотацией GetMapping"
     */
    @Pointcut("com.msp.spring.aop.CommonAspect.isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    /*
        проверяет аргументы
        args - check method param type
        * - any param type (*,* - только два параметра)
        .. - 0+ any params type
        org.springframework.ui.Model,*,* - первый аргумент Model и после него еще два параметра
        org.springframework.ui.Model,.. - неважно сколько аргументов после Model
     */
    @Pointcut("com.msp.spring.aop.CommonAspect.isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelParam() {
    }

    /*
        @args - check annotation on the param type
        * - any param type
        .. - 0+ any params type
        проверяет аннотацию параметра метода
     */
    @Pointcut("com.msp.spring.aop.CommonAspect.isControllerLayer() && @args(com.msp.spring.validator.UserInfo,..)")
    public void hasUserInfoParamAnnotation() {
    }

    /*
        bean - check bean name
        любой бин, заканчивающийся на Service
     */
    @Pointcut("bean(*Service)")
    public void isServiceLayerBean() {
    }

    /*
        проверяет метод
        execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
        public метод возвращающий любой результат (* - ret-type-pattern) из сервиса в пакете com.msp.spring.service и класс заканчивающийся на Service (declaring-type-pattern)
        метод с именем findById (name-pattern), любыми аргументами (* - param-pattern)
     */
    @Pointcut("execution(public * com.msp.spring.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    @Before(value = "anyFindByIdServiceMethod()" +
            "&& args(id) " +
            "&& target(service) " +
            "&& this(serviceProxy) " +
            "&& @within(transactional)",
            argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional) {
       log.info("before findById method args: {}", argsToString(joinPoint));
    }

    @AfterReturning(value = "anyFindByIdServiceMethod() " +
            "&& target(service)",
            returning = "result")
    public void addLoggingAfterReturning(Object result, Object service) {
        log.info("after returning - invoked findById method in class {}, result {}", service, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod() " +
            "&& target(service)",
            throwing = "ex")
    public void addLoggingAfterThrowing(Throwable ex, Object service) {
        log.info("after throwing - invoked findById method in class {}, exception {}: {}", service, ex.getClass(), ex.getMessage());
    }

    @After("anyFindByIdServiceMethod() && target(service)")
    public void addLoggingAfterFinally(Object service) {
        log.info("after (finally) - invoked findById method in class {}", service);
    }

}