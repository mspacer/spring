package com.msp.spring.web.rest;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.ResourceBundle;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
@Slf4j()
public class MessageRestController {

    private final MessageSource messageSource;

    @GetMapping
    public String getMessage(@RequestParam() String key, @RequestParam() String language) {
        return messageSource.getMessage(key, null, null, new Locale(language));
    }

    @GetMapping("/prev")
    public String getMessagePrev(@RequestParam() String key, @RequestParam() String language) {
        try {
            return ResourceBundle.getBundle("messages", new Locale(language)).getString(key);
        } catch (Exception e) {
            log.error(" error for key {"+key+"}", e);
        }

        return null;
    }

}
