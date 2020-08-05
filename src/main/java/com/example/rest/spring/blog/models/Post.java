package com.example.rest.spring.blog.models;



import org.hibernate.annotations.Type;

import javax.persistence.*;

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

    @Column(name="title", unique = true, length = 100)
    private String title;

    @Column(name="anons")
    private String anons;

    @Type(type = "text")
    @Column(name="full_text")
    private String fullText;

    @Column(name="views")
    private int views;

    @Temporal(TemporalType.DATE)
    @Column(name="created_Post_Date")
    private Date createdPostDate;

    @Temporal(TemporalType.DATE)
    @Column(name="changed_Post_Date")
    private Date changedPostDate;

    @OneToMany(mappedBy = "post",  orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

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
