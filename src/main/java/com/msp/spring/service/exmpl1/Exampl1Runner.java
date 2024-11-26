package com.msp.spring.service.exmpl1;

import com.msp.spring.service.exmpl1.bean.ActionQualifier;
import com.msp.spring.service.exmpl1.bean.ComedyQualifier;
import com.msp.spring.service.exmpl1.bean.Hero;
import com.msp.spring.service.exmpl1.bean.MagicQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Exampl1Runner {

    @Autowired
    Map<String, Hero> mainCharactersByFilmName;

    @Autowired
    @ActionQualifier
    @MagicQualifier
    @ComedyQualifier
    List<Hero> heroes;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.msp.spring.service.exmpl1");

        context.getBean(BaruhService.class).printList();
        context.getBean(JekaService.class).printList();
        context.getBean(JekaService.class).printList1();
        context.getBean(JekaService.class).printList2();

        Exampl1Runner exampl1Runner = context.getBean(Exampl1Runner.class);
        /*
        Аналогично с Map - несмотря на существующую в конфиге mainCharactersByFilmName, спринг создаст новую Map,
        где ключами будут имя бинов
         */
        System.out.println("----Map of keys:");
        exampl1Runner.mainCharactersByFilmName.keySet().stream()
                .forEach(System.out::println);

        /*
        Qualifiers работают по принципу AND. Поэтому только Jim Cary, который включает все аннотации в AnyGenreQualifier
        Если убрать @ComedyQualifier - добавится Gandalf
         */
        System.out.println("----List of heroes:");
        exampl1Runner.heroes.stream()
                .forEach(System.out::println);
    }
}
