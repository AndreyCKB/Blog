package com.example.rest.spring.blog.repositories;

import com.example.rest.spring.blog.models.UserPassword;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class PasswordRepositoryImpl implements  PasswordRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int updatePassword(String newPassword, Long id) {
        return entityManager.createQuery("UPDATE UserPassword up SET up.password = ?1  WHERE up.id = ?2")
                .setParameter(1, newPassword)
                .setParameter(2, id)
                .executeUpdate();

    }

    @Override
    public void createPassword(String newPassword, Long id) {
        UserPassword userPassword = new UserPassword();
        userPassword.setPassword(newPassword);
        userPassword.setPasswordId(id);
        entityManager.persist(userPassword);
    }

    @Override
    public UserPassword findById(Long id) {
          return entityManager.find(UserPassword.class, id);
    }
}