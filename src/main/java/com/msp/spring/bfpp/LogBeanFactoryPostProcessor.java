package com.msp.spring.bfpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

/**
 * В жизненном цикле бинов beans post processors выполняются в первую очередь.
 * Для этого Spring в списка бинов из конфигурации отбирает их и сортирует в порядке объявления, либо наличии
 * реализуемых интерфейсов Ordered и PriorityOrdered (имеет высший приоритет) и возвращаемого значения из
 * getOrder() (чем меньше число, тем больший приоритет)
 *
 * определение принадлежности к классу beans post processors
 *      BeanFactoryPostProcessor.class.isAssignableFrom(bfpp.getClass())
 */
public class LogBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("execute LogBeanFactoryPostProcessor.postProcessBeanFactory()");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
