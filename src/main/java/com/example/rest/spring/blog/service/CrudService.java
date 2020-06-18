package com.example.rest.spring.blog.service;

import com.example.rest.spring.blog.service.post.PostServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CrudService<T, ID>{
    <S extends T> S save(S var);

    <S extends T> Iterable<S> saveAll(Iterable<S> var);

    Optional<T> findById(ID var);

    boolean existsById(ID var);


    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<ID> var);

    long count();

    void deleteById(ID var);

    void delete(T var);

    void deleteAll(Iterable<? extends T> var);

    void deleteAll();



}
