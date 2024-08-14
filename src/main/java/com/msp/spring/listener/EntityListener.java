package com.msp.spring.listener;

import com.msp.spring.listener.entity.EntityEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {

    @EventListener(condition = "#root.args[0].accessType.name() == 'CREATE'")
    public void acceptEntity(EntityEvent event) {
        System.out.println("Entity: " + event);
    }
}
