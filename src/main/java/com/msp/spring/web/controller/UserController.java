package com.msp.spring.web.controller;

import com.msp.spring.database.dto.PageResponse;
import com.msp.spring.database.dto.UserCreateEditDto;
import com.msp.spring.database.dto.UserFilter;
import com.msp.spring.database.dto.UserReadDto;
import com.msp.spring.database.entity.Role;
import com.msp.spring.service.CompanyService;
import com.msp.spring.service.UserService;
import com.msp.spring.validator.group.CreationAction;
import com.msp.spring.validator.group.UpdateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@SessionAttributes(value = {"loginUser"})
@Slf4j
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping
    //@PreFilter(value = "filter.firstName.contains('a')", filterTarget = "filter")
    public String findAll(Model model,
                          UserFilter filter,
                          Pageable pageable,
                          @AuthenticationPrincipal UserDetails userDetails) {
//        model.addAttribute("users", userService.findAll(filter));
        Page<UserReadDto> page = userService.findAll(filter, pageable);
        model.addAttribute("users", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("loginUser", userDetails);
        log.info("security context: {}", SecurityContextHolder.getContext());
        return "user/users";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') and @userService.findAll().size() > 0")
    //@PostAuthorize("returnObject")
    public String findById(@PathVariable("id") Long id,
                           Model model,
                           //@CurrentSecurityContext SecurityContext securityContext
                           @AuthenticationPrincipal UserDetails userDetails) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("loginUser", userDetails);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "user/registration";
    }

    @PostMapping("/create")
    //@ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute @Validated({Default.class, CreationAction.class }) UserCreateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
/*
            attributes.addAttribute("username", user.getUsername());
            attributes.addAttribute("firstname", user.getFirstname());
            attributes.addAttribute("birthDate", "2000-12-13");
*/
            attributes.addFlashAttribute("user", user);
            attributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        userService.create(user);
        return "redirect:/login";
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated({Default.class, UpdateAction.class }) UserCreateEditDto user) {
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
