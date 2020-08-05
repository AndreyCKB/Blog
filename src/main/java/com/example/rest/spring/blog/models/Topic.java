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
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return String.valueOf(chars);
    }

    public void setName(String topicName) {
        this.name = topicName.toLowerCase();;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getNumberPosts() {
        return this.posts != null ? posts.size() : 0;
    }
    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", topicName=" + name +
                ", posts size =" + getNumberPosts() +
                '}';
    }
}
