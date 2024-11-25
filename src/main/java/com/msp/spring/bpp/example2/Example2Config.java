package com.msp.spring.bpp.example2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Example2Config {

    /*
    Фича спринга:
    для бинов, создающихся в java-конфигурации в bean definition отсутствует класс (beanClass == null)
    поэтому в InitMethodRegistryBeanFactoryPostProcessor при вызове
        Class.forName(beanDefinition.getBeanClassName()) будет NullPointerException
    Это связано с тем, что пока не будет вызван метод lgIron() java не знает какой будет результат (влияние полиморфизма)
    Лечится костылем, который определяет тип возвращаемого метода lgIron()
     */
    @Bean
    public LgIron lgIron() {
        return new LgIron();
    }
}
