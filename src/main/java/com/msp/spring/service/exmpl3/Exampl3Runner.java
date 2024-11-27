package com.msp.spring.service.exmpl3;

import com.msp.spring.service.exmpl2.SomeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Exampl3Runner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.msp.spring.service.exmpl3");

        SingleService singleService = context.getBean(SingleService.class);
        System.out.println("uuid1: " + singleService.generateUUID()
                + ",\n autowired class: " + singleService.getAutowiredProtoClass()
                + ",\n class: " + singleService.getProtoClass());
        System.out.println("uuid2: " + singleService.generateUUID()
                + ",\n autowired class: " + singleService.getAutowiredProtoClass()
                + ",\n class: " + singleService.getProtoClass());

        System.out.println("uuid3: " + singleService.generateSupplierUUID());
        System.out.println("uuid4: " + singleService.generateSupplierUUID());

        System.out.println("ok ");
    }
}
