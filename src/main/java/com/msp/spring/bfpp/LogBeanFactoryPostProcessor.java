package com.msp.spring.bfpp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * В жизненном цикле бинов beans post processors выполняются в первую очередь.
 * Для этого Spring в списка бинов из конфигурации отбирает их и сортирует в порядке объявления, либо наличии
 * реализуемых интерфейсов Ordered и PriorityOrdered (имеет высший приоритет) и возвращаемого значения из
 * getOrder() (чем меньше число, тем больший приоритет)
 *
 * определение принадлежности к классу beans post processors
 *      BeanFactoryPostProcessor.class.isAssignableFrom(bfpp.getClass())
 */
@Component
@Slf4j
public class LogBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("execute LogBeanFactoryPostProcessor.postProcessBeanFactory()");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
