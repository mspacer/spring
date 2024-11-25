package com.msp.spring.bpp.example2;

import javax.annotation.PostConstruct;

public interface Iron {
    @PostConstruct
    /*
    в этом случае аннотация не сработает, т.к. сприг не смотрит интерфесы
    Костыль - написать BPP, который ходит по интерфейсам и прописывает в bean definition класса init метод

     */
    void init();
    /*
    в этом случае PostConstruct работает, т.к. метод становится частью унаследованного класса,
    даже если он переопределен
     */
    /*default void init() {
        System.out.println("default init interface");
    }*/


}
