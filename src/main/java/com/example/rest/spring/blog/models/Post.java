package com.example.rest.spring.blog.models;



import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;

    @NotBlank()
    @Column(name="title", unique = true, length = 100,nullable = false)
    private String title;

    @NotBlank()
    @Column(name="anons",nullable = false)
    private String anons;

    @NotBlank()
    @Type(type = "text")
    @Column(name="full_text", nullable = false)
    private String fullText;

    @Column(name="views")
    private int views;

    @Temporal(TemporalType.DATE)
    @Column(name="created_Post_Date", nullable = false)
    private Date createdPostDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="changed_Post_Date", nullable = false)
    private Date changedPostDate;

    @OneToMany(mappedBy = "post",  orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
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

    public Date getCreatedPostDate() {
        return createdPostDate;
    }

    public void setCreatedPostDate(Date createdPostDate) {
        this.createdPostDate = createdPostDate;
    }

    public Date getChangedPostDate() {
        return changedPostDate;
    }

    public void setChangedPostDate(Date changedPostDate) {
        this.changedPostDate = changedPostDate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int incrementViewsAndGet(){
        return ++this.views;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", topic=" + topic +
                ", title='" + title + '\'' +
                ", anons='" + anons + '\'' +
                ", fullText='" + fullText + '\'' +
                ", views=" + views +
                ", createdPostDate=" + createdPostDate +
                ", changedPostDate=" + changedPostDate +
                ", comments size=" + (comments != null ? comments.size() : 0) +
                ", user=" + user +
                '}';
    }
}
