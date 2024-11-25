package com.msp.spring.service.tmp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@EnableAspectJAutoProxy
@ImportResource("classpath:application.xml")
public class IHateInterfaces implements Serializable, Comparable {

    /*
        В данном примере есть три источника для конфигурирования бинов:
        - xml (имеет высший приоритет. так сделано в спринг чтобы иметь возможность конфигурировать проиложение без перекомпиляции)
        - compoment scan процесс запускается в xml
        - java-config IHateInterfacesConfiguration
        Все три процесса создают бин MissionCriticalService
        Из xml если name не прописан явно спринг дает имя в формате com.msp.spring.service.tmp.MissionCriticalService#0
        compoment scan - имя класса с маленькой буквы
        java config - по имени метода
        В данном случае compoment scan и имя метода из java config дают одиноковое имя и бин не дублируется
        xml дает свое имя.
        Таким образом в контексте появляются два бина одинакового класса и PostConstruct срабатывает дважды.
     */

    public void killAllInterfaces() {
        System.out.println("It works without interfaces");
    }

    /*
    Если класс не наследует интерфейсов или интерфейс не содержит методов, спринг для прокси использует CGLIB,
    иначе - dynamic proxy, поэтому даже с implements Serializable - будет CGLIB
    CGLIB использует механизм создания прокси как наследника базового класса, поэтому в контексте будет IHateInterfaces
    Один из недостатков - классы не должны быть final
    dynamic proxy - создает новый класс-реализацию базовых интерфейсов (в данном случае jdk.proxy2.$Proxy14)
    и в контексте IHateInterfaces уже не будет, что вызовет ошибку NoSuchBeanDefinitionException
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.msp.spring.service.tmp");
        //IHateInterfaces iHateInterfaces = context.getBean(IHateInterfaces.class);
        Comparable iHateInterfaces = context.getBean(Comparable.class);
        System.out.println("iHateInterfaces class: " + iHateInterfaces.getClass());
        //iHateInterfaces.killAllInterfaces();
        iHateInterfaces.compareTo(new IHateInterfaces());
    }

    @Override
    public int compareTo(Object o) {
        System.out.println("compare to");
        return 0;
    }
}
