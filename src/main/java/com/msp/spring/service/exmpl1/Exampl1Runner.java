package com.msp.spring.service.exmpl1;

import com.msp.spring.service.exmpl1.bean.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Exampl1Runner {

    @Autowired
    Map<String, Hero> mainCharactersByFilmName;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.msp.spring.service.exmpl1");

        context.getBean(BaruhService.class).printList();
        context.getBean(JekaService.class).printList();
        context.getBean(JekaService.class).printList1();
        context.getBean(JekaService.class).printList2();

        /*
        Аналогично с Map - несмотря на существующую в конфиге mainCharactersByFilmName, спринг создаст новую Map,
        где ключами будут имя бинов
         */
        context.getBean(Exampl1Runner.class).mainCharactersByFilmName.keySet().stream()
                .forEach(System.out::println);

    }
}
