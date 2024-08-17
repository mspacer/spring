package com.msp.spring.listener;

import com.msp.spring.listener.entity.EntityEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EntityListener {

    @EventListener(condition = "#root.args[0].accessType.name() == 'CREATE'")
    public void acceptEntity(EntityEvent event) {
        log.info("Entity: " + event);
    }
}
