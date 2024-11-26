package com.msp.spring.service.exmpl1;

import com.msp.spring.service.exmpl1.config.BaruhQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaruhService {

    /*
    спринг с коллекциями работает следующим образом:
    Спринг собирает все бины с типом, указанным в генерике коллекции, создает ArrayList и отдает в Autowired
     */
    @Autowired
    @BaruhQualifier
    private List<String> listB; // - без Qualifier из JekaConfig придет дополнительная строка Jeka
    //private List list; // - придет list из JekaConfig
    //private List listB; // - ошибка No qualifying bean

    public void printList() {
        System.out.println("baruh list:" + listB);
    }
}
