package com.example.rest.spring.blog.service.user;


import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public <S extends User> S save(S user) {
        User userExist = this.userRepository.findByEmailIgnoreCase( user.getEmail() );

        if ( userExist == null ) {
            user.setEmail( user.getEmail().toLowerCase() );
            user.setPassword( passwordEncoder.encode( user.getPassword() ) );
            user.setDateRegistration( new Date() );
        } else {
            user.setPassword( userExist.getPassword() );
            user.setId( userExist.getId() );
            user.setDateRegistration( userExist.getDateRegistration() );
            if ( userExist.getBirthday() != null )
                user.setBirthday( userExist.getBirthday() );
        }

        return this.userRepository.save( user );
    }


    @Override
    public Optional<User> findById(Long userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Iterable<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteById(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public User findByEmailIgnoreCase(String email) {
        return this.userRepository.findByEmailIgnoreCase(email);
    }


    @Override
    public void changeEmail(User user, String newEmail, String password) throws ErrorMessageForUserException{
        newEmail = newEmail.toLowerCase();

        if (newEmail == null || newEmail.isEmpty()) {
            throw new ErrorMessageForUserException("Email не может быть пустым");
        }

        if (user.getEmail().equals(newEmail)){
            throw new ErrorMessageForUserException("Вы не изменили ваш email(текущий email= "
                    + user.getEmail()
                    + " новый email = "
                    + newEmail
                    + " ).<br>Email не чувствителен к регистру.");
        }
        if (this.userRepository.findByEmailIgnoreCase(newEmail) != null) {
            throw new ErrorMessageForUserException("Не удалось зарегистрировать данный email( "
                    + newEmail
                    + " ).<br>Данный email уже зарегистрирован в системе.");
        }

        User savedUser = findById(user.getId()).get();
        if (!this.passwordEncoder.matches(password, savedUser.getPassword())) {
            throw new ErrorMessageForUserException("Введён невернй пароль");
        }
        this.userRepository.updateEmail(newEmail, savedUser.getId());
    }

    @Override
    public void changePassword(User user,String currentPassword, String newPassword) throws ErrorMessageForUserException{
        if (newPassword == null || newPassword.isEmpty()) {
            throw new ErrorMessageForUserException("Пароль не может быть пустым");
        }

        User savedUser = findById(user.getId()).get();
        if (!this.passwordEncoder.matches(currentPassword, savedUser.getPassword())) {
            throw new ErrorMessageForUserException("Введён невернй пароль");
        }

        if (this.passwordEncoder.matches(newPassword, savedUser.getPassword())) {
            throw new ErrorMessageForUserException("Введённй пароль такоже как и текущий");
        }

        newPassword = this.passwordEncoder.encode(newPassword);
        int isUpdate = this.userRepository.updatePassword(newPassword, savedUser.getId());
        if (isUpdate != 1) {
            throw new ErrorMessageForUserException("Произошла ошибка пароль не был изменён");
        }
    }

    public void resetPassword(Authentication auth){
        StringBuilder newPassword = new StringBuilder();
        char c = 0;
        for (int i = 0; i < 8; i++){
            c = (char) (48 + Math.random()* 75);
            newPassword.append(c);
        }
        User user = (User) auth.getPrincipal();
        this.userRepository.updatePassword(newPassword.toString(), user.getId());

    }

}
