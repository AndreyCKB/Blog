package com.example.rest.spring.blog.controller;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.models.Topic;
import com.example.rest.spring.blog.repositories.TopicRepository;
import com.example.rest.spring.blog.service.topic.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/topic")
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/page-add")
    public String addTopicPage() {
        return "/topics/topic-add";
    }

    @PostMapping("/add")
    public String addTopic(@ModelAttribute("topic") Topic topic, Model model) {
        if (topic == null || topic.getName() == null || topic.getName().isEmpty()){
            model.addAttribute("errorMessage", "Вы не ввели название темы");
            return "/topics/topic-add";
        }
        try {
            topicService.save(topic);
        }catch (ErrorMessageForUserException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/topics/topic-add";
        }
        return "/topics/topic-add";
    }

    @GetMapping("/get-topics")
    public String getTopics(Model model) {
        model.addAttribute("topics", this.topicService.findAll());
        return "/topics/topic-add";
    }
}
