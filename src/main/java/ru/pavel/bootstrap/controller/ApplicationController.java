package ru.pavel.bootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.pavel.bootstrap.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class ApplicationController {

    private final UserService userService;

    @Autowired
    public ApplicationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String main(Model model, HttpSession session, @Nullable Authentication auth) {
        return userService.getPage(model, session, auth);
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied-page";
    }
}
