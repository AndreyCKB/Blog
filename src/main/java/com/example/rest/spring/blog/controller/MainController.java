package com.example.rest.spring.blog.controller;


import com.example.rest.spring.blog.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private UserService userService;

    public MainController(UserService userService) {

        this.userService = userService;
    }

//    @GetMapping("/")
//    public String home(HttpSession session) {
//        session.setAttribute("userId", this.userService.getAuthUser().getId());
//        return "redirect:/post/list_posts";
//    }

    @GetMapping("/")
    public String home() {
        return "redirect:/post/list_posts";
    }


    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "ABOUT");
        return "about";
    }


}