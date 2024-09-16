package com.msp.spring.web.controller;

import com.msp.spring.exception.MyValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

// конфликтует с таймлифом и по какой-то причине при 403 ошибке запускается повторно FilterChain и получается 404 ошибка
//@ControllerAdvice(basePackages = "com.msp.spring.web.controller")
@Slf4j
public class MyErrorControllerHandler/* implements ErrorController*/{

    @ExceptionHandler(AuthenticationException.class)
    public String handleError() {
        return "error";
    }

/*
    @RequestMapping("/error")
    public String handleError() {
        return "/error";
    }
*/

/*
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex, BindingResult bindingResult) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("bindingResult", bindingResult);
        mav.setViewName("/error");
        return "error";
    }
*/

}
