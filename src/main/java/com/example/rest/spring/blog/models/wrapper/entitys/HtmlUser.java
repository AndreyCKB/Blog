package com.example.rest.spring.blog.models.wrapper.entitys;

import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope("prototype")
public class HtmlUser {
//    @Autowired
    private DateConverter dateConverter = new DateConverter();
//    @Autowired
    private User user = new User();


    public String getBirthday() {
        return this.dateConverter.dateToStringMonthInWords(this.user.getBirthday());
    }

    public HtmlUser setBirthday(String birthday) {
        this.user.setBirthday(this.dateConverter.stringInFormat_yyyy_MM_dd_toDate(birthday));
        return this;
    }

    public long getId() {
        return this.user.getId();
    }

    public HtmlUser setId(long id) {
        this.user.setId(id);
        return this;
    }

    public String getFirstName() {
        return this.user.getFirstName();
    }

    public HtmlUser setFirstName(String firstName) {
        this.user.setFirstName(firstName);
        return this;
    }

    public String getSurname() {
        return this.user.getSurname();
    }

    public HtmlUser setSurname(String surname) {
        this.user.setSurname(surname);
        return this;
    }

    public String getMiddleName() {
        return this.user.getMiddleName();
    }

    public HtmlUser setMiddleName(String middleName) {
        this.user.setMiddleName(middleName);
        return this;
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    public HtmlUser setEmail(String email) {
        this.user.setEmail(email);
        return this;
    }

    public String getDateRegistration() {
//        return this.user.getDateRegistration().toString();
        return this.dateConverter.dateToStringMonthInWords(this.user.getDateRegistration());
    }

    public HtmlUser setDateRegistration(String dateRegistration) {
//        this.user.setDateRegistration(this.dateConverter.stringInFormat_yyyy_MM_dd_toDate(dateRegistration));
//        return this;
        return this;
    }

    public User getUser(){
        return this.user;
    }
    @Autowired
    public HtmlUser setDateConverter(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
      return this;
    }

    @Autowired
    public HtmlUser setUser(User user) {
        this.user = user;
        return this;
    }
    public List<Post> getPosts() {
        return this.user.getPosts();
    }

    public HtmlUser setPosts(List<Post> posts) {
        this.user.setPosts(posts);
        return this;
    }
    @Override
    public String toString() {
        return "HtmlUser{" +
                "user=" + user +
                '}';
    }


}
