package com.msp.spring.web.controller;

import com.msp.spring.database.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(value = {"user"})
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(Model model, LoginDto loginDto) {
        model.addAttribute("user", loginDto);
        return "/user/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, @SessionAttribute(value = "user"/*, required = false*/) LoginDto loginDto) throws Exception {
    // если SessionAttribute true или возникает "ручной" Exception - будет вызван MyErrorController.handleError
/*
        if (loginDto == null || loginDto.getUserName() == null) {
            throw new Exception("No user found");
        }
*/
        return "/user/welcome";
    }

    @GetMapping("/redirect")
    public String testRedirect() {
        //return "forward:/WEB-INF/jsp/user/login.jsp"; // тоже самое что и /user/login
        //return "/user/login";
        return "redirect:/login";// указывается урл. в браузере будет 302 ответ на /redirect с ответом Location: http://localhost:8080/login в response
    }

}
