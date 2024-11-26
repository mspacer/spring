package com.msp.spring.service.exmpl1.config;

import com.msp.spring.service.exmpl1.bean.Gandalf;
import com.msp.spring.service.exmpl1.bean.Hero;
import com.msp.spring.service.exmpl1.bean.Rambo;
import com.msp.spring.service.exmpl1.bean.Terminator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.msp.spring.service.exmpl1.bean.Keys.*;

@Configuration
public class MapConfig {

    @Bean
    public Rambo rambo() {
        return new Rambo();
    }

    @Bean
    public Gandalf gandalf() {
        return new Gandalf();
    }

    @Bean
    public Terminator terminator() {
        return new Terminator();
    }

    @Bean
    public Map<String, Hero> mainCharactersByFilmName(Rambo rambo,
                                                      Terminator terminator,
                                                      Gandalf gandalf) {
        Map<String, Hero> result = new HashMap<>();

        result.put(RAMBO, rambo);
        result.put(TERMINATOR, terminator);
        result.put(GANDALF, gandalf);

        return result;
    }
}
