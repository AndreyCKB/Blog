package com.example.rest.spring.blog.models;


import javax.persistence.*;
import java.util.List;

//@Entity
//@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id")
    private long id;


    @OneToMany(mappedBy = "author",  orphanRemoval = true)
    private List<Post> posts;

    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
