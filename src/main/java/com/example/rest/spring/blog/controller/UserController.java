package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.models.wrapper.entitys.HtmlUser;
import com.example.rest.spring.blog.service.user.UserService;
import com.example.rest.spring.blog.util.DateConverter;


import org.springframework.security.core.Authentication;
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

//    @GetMapping("/profile")
//    public String profile(Model model) {
//        addUserInModel( model, this.userService.getAuthUser() );
//        return "users/profile";
//    }
//
//    @PostMapping("/profile")
//    public String changeProfile(@ModelAttribute("user") User user,
//                                @RequestParam(name = "birthday_str", required = false) String birthday,
//                                Model model) {
//        user.setBirthday( this.dateConverter.stringInFormat_yyyy_MM_dd_toDate(birthday) );
//        User savedUser =  this.userService.updateUser(user);
//        this.addUserInModel(model, savedUser);
//        return "users/profile";
//    }

    @GetMapping("/profile")
    public String profile(Model model, HtmlUser htmlUser) {
        htmlUser = htmlUser.setUser(this.userService.getAuthUser());
        System.out.println(htmlUser);
        model.addAttribute( "userHtml", htmlUser.setUser(this.userService.getAuthUser()));
        return "users/profile";
    }

    @PostMapping("/profile")
    public String changeProfile(@ModelAttribute("userHtml") HtmlUser htmlUser,
//                                @RequestParam(name = "birthday_str", required = false) String birthday,
                                Model model) {
            System.out.println(htmlUser);
//        user.setBirthday( this.dateConverter.stringInFormat_yyyy_MM_dd_toDate(birthday) );
//        User savedUser =  this.userService.updateUser(user);
//        this.addUserInModel(model, savedUser);
//        return "users/profile";
        return "redirect:/post/list_posts";
    }

    @GetMapping("/change-email")
    public String changeEmailPage(Model model) {
        model.addAttribute("email", this.userService.getAuthUser().getEmail());
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
            model.addAttribute("email", this.userService.getAuthUser().getEmail());
            model.addAttribute("errorMessage", e.getMessage());
            return "users/change-email";
        }
        return "redirect:/logout";
    }

    @GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute("email", this.userService.getAuthUser().getEmail());
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
            model.addAttribute("email", this.userService.getAuthUser().getEmail());
            model.addAttribute("errorMessage", e.getMessage());
            return "users/change-password";
        }
        return "redirect:/logout";
    }

    private void addUserInModel(Model model, User user){
        model.addAttribute("user", user);
        model.addAttribute("birthday" ,this.dateConverter.dateToStringMonthInWords(user.getBirthday()));
    }





}
