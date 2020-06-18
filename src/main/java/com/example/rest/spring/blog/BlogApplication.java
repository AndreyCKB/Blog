package com.example.rest.spring.blog;

import com.example.rest.spring.blog.configuration.Config;
import com.example.rest.spring.blog.models.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(BlogApplication.class, args);

	}

}
