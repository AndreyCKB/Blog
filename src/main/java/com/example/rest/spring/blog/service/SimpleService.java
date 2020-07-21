package com.example.rest.spring.blog.service;

import java.util.Optional;

public interface SimpleService<T, ID> {

    <S extends T> S save(S var);

    Optional<T> findById(ID id);

    Iterable<T> findAll();

    void deleteById(ID id);
}
