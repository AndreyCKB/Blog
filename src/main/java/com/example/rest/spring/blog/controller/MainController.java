package com.example.rest.spring.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpSession;


@Controller
public class MainController {



    @GetMapping("/")
    public String home(HttpSession session) {
        return "redirect:/post/list_posts";
    }


    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "ABOUT");
        return "about";
    }


}