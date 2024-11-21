package com.msp.spring.bpp.example1;

import javax.annotation.PostConstruct;

public class Parent {

    /*
    PostConstruct сработает, несмотря на то, что бин Parent не создается (в xml нет конфигурации)
    модификатор private гарантирует, что метод не переопределяется в потомках
    Spring рекурсивно до Object вызывает getDeclaredMethod и ищет аннотацию PostConstruct
    Таким образом в поиск попадают и родительские классы.
     */
    @PostConstruct
    private void init() {
        System.out.println("Parent init");
    }
}
