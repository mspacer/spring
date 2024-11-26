package com.msp.spring.service.exmpl1;

import com.msp.spring.service.exmpl1.config.JekaQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JekaService {

    @Autowired
    //@Qualifier("messages")
    @JekaQualifier
    private List<String> list;

    /*
    поскольку генерик тип List<String>, то под него подходит list() и list1() из JekaConfig
     */
    @Autowired
    private List<List<String>> list1;

    /*
    здесь конкретный тип ArrayList, поэтому только  list1()
     */
    @Autowired
    private ArrayList<String> list2;

    public void printList() {
        System.out.println("jeka list:" + list);
    }

    public void printList1() {
        System.out.println("jeka list1:" + list1);
    }

    public void printList2() {
        System.out.println("jeka list2:" + list2);
    }

}
