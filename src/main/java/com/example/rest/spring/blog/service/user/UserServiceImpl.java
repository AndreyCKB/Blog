package com.example.rest.spring.blog.service.user;

import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public <S extends User> S save(S user) {
        return this.userRepository.save(user);
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> users) {
        return this.userRepository.saveAll(users);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public boolean existsById(Long userId) {
        return this.userRepository.existsById(userId);
    }

    @Override
    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> userIdes) {
        return this.userRepository.findAllById(userIdes);
    }

    @Override
    public long count() {
        return this.userRepository.count();
    }

    @Override
    public void deleteById(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public void delete(User user) {
        this.userRepository.delete(user);
    }

    @Override
    public void deleteAll(Iterable<? extends User> users) {
        this.userRepository.deleteAll(users);
    }

    @Override
    public void deleteAll() {
        this.userRepository.deleteAll();
    }

    @Override
    public User findByEmailIgnoreCase(String email) {
        return this.userRepository.findByEmailIgnoreCase(email);
    }
}
