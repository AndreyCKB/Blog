package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.models.wrapper.entitys.HtmlUser;
import com.example.rest.spring.blog.service.user.UserService;
import com.example.rest.spring.blog.util.DateConverter;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    final private DateConverter dateConverter;
    final private UserService userService;

    public UserController(DateConverter dateConverter, UserService userService) {
        this.dateConverter = dateConverter;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model, HtmlUser htmlUser) {
        htmlUser = htmlUser.setUser(this.userService.getPrincipal());
        model.addAttribute( "user", htmlUser.setUser(this.userService.getPrincipal()));
        return "users/profile";
    }

    @PostMapping("/profile")
    public String changeProfile(@ModelAttribute("user") HtmlUser htmlUser,
                                Model model) {
        User savedUser =  this.userService.updateUser(htmlUser.getUser());
        model.addAttribute("user", htmlUser.setUser(savedUser));
        return "users/profile";
    }

    @GetMapping("/change-email")
    public String changeEmailPage(Model model) {
        model.addAttribute("email", this.userService.getPrincipal().getEmail());
        return "users/change-email";
    }

    @PostMapping("/change-email")
    public String changeEmail(@RequestParam(name = "new_email", required = true) String newEmail,
                              @RequestParam(name = "password", required = true) String password,
                              Model model) {
        try {
            this.userService.changeEmail(newEmail, password);
        } catch (ErrorMessageForUserException e) {
            e.printStackTrace();
            model.addAttribute("email", this.userService.getPrincipal().getEmail());
            model.addAttribute("errorMessage", e.getMessage());
            return "users/change-email";
        }
        return "redirect:/logout";
    }

    @GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute("email", this.userService.getPrincipal().getEmail());
        return "users/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam(name = "password", required = true) String currentPassword,
                                 @RequestParam(name = "new_password", required = true) String newPassword,
                                 @RequestParam(name = "password_confirmation", required = true) String passwordConfirmation,
                                 Model model) {
        if ( !newPassword.equals(passwordConfirmation) ) {
            model.addAttribute("errorMessage", "Подтверждения нового пароля и новый пароль не совпадают");
            return "users/change-password";
            }
        try {
            this.userService.changePassword(currentPassword, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("email", this.userService.getPrincipal().getEmail());
            model.addAttribute("errorMessage", e.getMessage());
            return "users/change-password";
        }
        return "redirect:/logout";
    }
}
