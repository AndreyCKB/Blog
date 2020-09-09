package com.example.rest.spring.blog.models;


import javax.persistence.*;
import javax.validation.constraints.Email;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "first_name", length = 25)
    private String firstName;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "middle_name", length = 25)
    private String middleName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "Date_registration", nullable = false)
    private Date dateRegistration;

    @OneToMany(mappedBy = "user",  orphanRemoval = true)
    private List<Post> posts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getFullName(){
        String result;
        if (this.surname == null) {
            result = this.email.substring(0,this.email.indexOf('@'));
        }
        else {
            String firstCharName = this.firstName != null ? (Character.toUpperCase(this.firstName.toCharArray()[0]) + ".") : "";
            String firstCharMiddleName = this.middleName != null ? (Character.toUpperCase(this.middleName.toCharArray()[0]) + ".") : "";
            result = this.surname + " " + firstCharName + firstCharMiddleName;
        }
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", date registration=" + dateRegistration +
                '}';
    }
}
