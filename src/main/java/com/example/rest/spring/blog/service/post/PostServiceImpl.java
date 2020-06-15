package com.example.rest.spring.blog.service.post;

import com.example.rest.spring.blog.models.Post;
import com.example.rest.spring.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements  PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public <S extends Post> S save(S s) {
        return this.postRepository.save(s);
    }

    @Override
    public <S extends Post> Iterable<S> saveAll(Iterable<S> iterable) {
        return this.postRepository.saveAll(iterable);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return this.postRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.postRepository.existsById(id);
    }

    @Override
    public Page findAllAndSortByParameter(int page, Sort parametrSort) {
        Pageable title = PageRequest.of(page, 10, parametrSort);
        return this.postRepository.findAll(title);
    }

    @Override
    public Iterable<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Iterable<Post> findAllById(Iterable<Long> iterable) {


        return this.postRepository.findAllById(iterable);
    }

    @Override
    public long count() {
        return this.postRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        this.postRepository.deleteById(id);
    }

    @Override
    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    @Override
    public void deleteAll(Iterable<? extends Post> iterable) {
        this.postRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        this.postRepository.deleteAll();
    }

    @Override
    public void updateAnonsAndFullText(long postID, String anons, String fullText) {
        this.postRepository.updateAnonsAndFullText(postID,anons,fullText, LocalDateTime.now().withNano(0));
    }

    @Override
    public void updateViews(long postID, int views) {
        this.postRepository.updateViews(postID,views);
    }

    @Override
    public Iterable<Post> findByKeyword(String keyword) {
        return this.postRepository.findByKeyword(keyword);
    }
}

