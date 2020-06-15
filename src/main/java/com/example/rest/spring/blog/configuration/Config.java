package com.example.rest.spring.blog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("com.example.rest.spring.blog")
@PropertySource("classpath:application.properties")
public class Config {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig(){
       return new PropertySourcesPlaceholderConfigurer();
    }
}
