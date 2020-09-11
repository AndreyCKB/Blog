package com.example.rest.spring.blog.controller;


import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.service.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String getSignUp() {
        logger.trace("Method getSignUp() started");
        return "/auth/registration";
    }


    @PostMapping("/registration")
    public String signUp(@ModelAttribute User user,
                         @RequestParam(name = "password", required = true) String password,
                         @RequestParam(name = "password_confirmation", required = true) String confirmPassword,
                         Model model) {

        logger.trace("Method signUp(@ModelAttribute User user = \"{}\",\n" +
                "                         @RequestParam(name = \"password\") String password = ?,\n" +
                "                         @RequestParam(name = \"password_confirmation\") String confirmPassword = ?,\n" +
                "                         Model model)");

        if ( !confirmPassword.equals(password)) {
            logger.debug("Password not equals confirmPassword");
            model.addAttribute("errorMessage", "Подтверждения пароля и пароль не совпадают");
            return "/auth/registration";
        }

        this.userService.registration(user, password);
        logger.debug("Password equals confirmPassword and User = \"{}\" is registration", user);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(name ="error_auth", required = false) boolean isError,
                        Model model) {
        logger.debug("User is signIn = \"{}\"", !isError);
        model.addAttribute("error_auth", isError);
        return "/auth/sign_in";
    }

}
