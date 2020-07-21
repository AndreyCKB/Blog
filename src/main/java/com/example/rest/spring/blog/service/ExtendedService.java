package com.example.rest.spring.blog.service;

import org.springframework.data.repository.NoRepositoryBean;



@NoRepositoryBean
public interface ExtendedService<T, ID> extends SimpleService<T, ID> {
    <S extends T> S save(S var);

    <S extends T> Iterable<S> saveAll(Iterable<S> var);

    boolean existsById(ID id);

    Iterable<T> findAllById(Iterable<ID> ids);

    long count();

    void delete(T var);

    void deleteAll(Iterable<? extends T> var);

    void deleteAll();



}
