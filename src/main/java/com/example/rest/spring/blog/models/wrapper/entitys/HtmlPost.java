package com.example.rest.spring.blog.models.wrapper.entitys;

import com.example.rest.spring.blog.models.Comment;
import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.models.Topic;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.util.DateConverter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Scope("prototype")
public class HtmlPost {

    private Post post = new Post();

    public long getId() {
        return this.post.getId();
    }

    public void setId(long id){
        this.post.setId(id);
    }

    public Topic getTopic() {
        return this.post.getTopic();
    }

    public void setTopic(Topic topic) {
        this.post.setTopic(topic);
    }

    public String getTitle() {
        return this.post.getTitle();
    }

    public void setTitle(String title) {
        this.post.setTitle(title);
    }

    public String getAnons() {
        return this.post.getAnons();
    }

    public void setAnons(String anons) {
        this.post.setAnons(anons);
    }

    public String getFullText() {
        return this.post.getFullText();
    }

    public void setFullText(String fullText) {
        this.post.setFullText(fullText);
    }

    public int getViews() {
        return this.post.getViews();
    }

    public void setViews(int views) {
        this.post.setViews(views);
    }

    public String getCreatedPostDate() {
        String result ="";
        Date date = this.post.getCreatedPostDate();
        if (date != null )
            result = DateConverter.dateToStringMonthInWords(date);
        return result;
    }
    public void setCreatedPostDate(Date createdPostDate) {
        this.post.setCreatedPostDate(createdPostDate);
    }

    public void setChangedPostDate(Date changedPostDate) {
        this.post.setChangedPostDate(changedPostDate);
    }

    public String getChangedPostDate() {
        String result ="";
        Date date = this.post.getChangedPostDate();
        if (date != null )
            result = DateConverter.dateAndTimeToString(date);
        return result;
    }

    public List<Comment> getComments() {
        return this.post.getComments();
    }

    public User getUser() {
        return this.post.getUser();
    }

    public int incrementViewsAndGet(){
        return this.post.incrementViewsAndGet();
    }

    public Post getPost() {
        return post;
    }

    public HtmlPost setPost(Post post) {
        this.post = post;
        return this;
    }

    @Override
    public String toString() {
        return "HtmlPost{" +
                "getChangedPostDate() = " + getCreatedPostDate() +
                "getCreatedPostDate() = " + getCreatedPostDate() +
                '}';
    }
}
