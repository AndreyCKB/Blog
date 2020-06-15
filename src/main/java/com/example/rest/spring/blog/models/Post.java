package com.example.rest.spring.blog.models;



import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name="title", unique = true, length = 100)
    private String title;

    @Column(name="anons")
    private String anons;


    @Type(type = "text")
    @Column(name="full_text")
    private String fullText;

    @Column(name="views")
    private int views;

    @Basic
    @Column(name="created_Post_Date")
    private LocalDateTime createdPostDate;

    @Basic
    @Column(name="changed_Post_Date")
    private LocalDateTime changedPostDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;


    public LocalDateTime getCreatedPostDate() {
        return createdPostDate;
    }

    public void setCreatedPostDate(LocalDateTime createdPostDate) {
        this.createdPostDate = createdPostDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", anons='" + anons + '\'' +
                ", fullText='" + fullText + '\'' +
                ", views=" + views +
                '}';
    }
}
