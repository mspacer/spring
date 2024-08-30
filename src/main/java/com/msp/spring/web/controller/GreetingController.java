package com.msp.spring.web.controller;

import com.msp.spring.database.dto.UserReadDto;
import com.msp.spring.database.entity.Role;
import com.msp.spring.database.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1") // общий префикс для контроллера
@SessionAttributes({"userReadDto"})
public class GreetingController {

    private final ApplicationContext applicationContext;

    @ModelAttribute("roles") //по умолчанию будет roleList
    public List<Role> roles() {
        return Arrays.asList(Role.values());
    }

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    public ModelAndView hello(ModelAndView modelAndView,
                              HttpServletRequest request,
                              CompanyRepository companyRepository, // используется механизм DI
                              @RequestParam(/*value = "age", */required = false) Integer age,
                              @RequestHeader(value = "accept") String acceptHeader,
                              @CookieValue(value = "JSESSIONID") String jSessionId,
                              @PathVariable Integer id
                              ) {
        //нативный servlet api
        String agePar = request.getParameter("age");
        String hAccept = request.getHeader("accept");
        String userAgent = request.getHeader("user-agent");
        Cookie[] cookies = request.getCookies();

        modelAndView.setViewName("/greeting/hello");

        return modelAndView;
    }

    @GetMapping(value = "/bye") // аналог @RequestMapping со свойством method
    public ModelAndView bye(@SessionAttribute(value = "user", required = false) UserReadDto user,
                            Model model) {
        // аналог request.getSession().getAttribute("user")
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/greeting/bye");

        return modelAndView;
    }

/*
    @GetMapping(value = "/attrib")
    public ModelAndView attrib() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/greeting/attrib");
        //лежит в requestScope. аналог requset.setAttribute()
        modelAndView.addObject("user", new UserReadDto(123, "Ivan"));

        return modelAndView;
    }
*/
    //другой вариант работы с ModelAndView
    @GetMapping(value = "/attrib")
    public String attrib(Model model,
                         /*@ModelAttribute*/ UserReadDto userReadDto) { //ModelAttribute не является обязательным
       // в этом случае  userReadDto лежит в requestScope, a user в sessionScope
       // model.addAttribute("user", /*new UserReadDto(123, "Ivan")*/ userReadDto);
        return "/greeting/attrib";
    }

}
