package com.example.rest.spring.blog.models;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank()
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", length = 25)
    private String firstName;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "middle_name", length = 25)
    private String middleName;

    @Column(name = "Date_registration", nullable = false)
    private Date dateRegistration;

    @Column(name = "birthday")
    private Date birthday;

//    @OneToMany(mappedBy = "user",  orphanRemoval = true)
//    private List<Post> posts;
//
//    public List<Post> getPosts() {
//        return posts;
//    }
//
//    public void setPosts(List<Post> posts) {
//        this.posts = posts;
//    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public String fullName(){
        String fullName;
        String surname = this.surname != null ? this.surname : null;
        if (surname != null){
            String name  = this.firstName != null ? this.firstName.substring(0,1).toUpperCase() + "." : "";
            String middleName  = this.middleName != null ? this.middleName.substring(0,1).toUpperCase() +"." : "";
            fullName = surname + name + middleName;
        }
        else{
            fullName = this.email.substring(0, email.indexOf("@") );
        }
        return fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", dateRegistration=" + dateRegistration +
                ", birthday=" + birthday +
                '}';
    }
}
