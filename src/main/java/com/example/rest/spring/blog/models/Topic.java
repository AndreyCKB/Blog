package com.example.rest.spring.blog.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "topic_id")
    private long topicId;

    @Column(name="name", unique = true)
    private String name;

    @OneToMany(mappedBy = "topic",  orphanRemoval = true)
    private List<Post> posts;

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String topicName) {
        this.name = topicName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", topicName='" + name + '\'' +
                ", posts size =" + (this.posts != null ? posts.size() : "null") +
                '}';
    }
}
