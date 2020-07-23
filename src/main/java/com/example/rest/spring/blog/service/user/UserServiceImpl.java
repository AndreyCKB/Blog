package com.example.rest.spring.blog.service.user;


import com.example.rest.spring.blog.exception.ErrorMessageForUserException;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.repositories.PasswordRepository;
import com.example.rest.spring.blog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
//    private AuthorizedUserRepository authRepository;
    private PasswordRepository passwordRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordRepository passwordRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordRepository = passwordRepository;
    }

    @Override
    public User getPrincipal(){
        return  (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();//
    }

    private long getPrincipalId(){
        return  getPrincipal().getId();
    }

    private String getPrincipalPassword(){
        return this.passwordRepository
                .findById(getPrincipalId())
                .getPassword();
    }


    private User updatePrincipal(User user)  {
        User principal = getPrincipal();
        principal.setFirstName(user.getFirstName());
        principal.setMiddleName(user.getMiddleName());
        principal.setSurname(user.getSurname());
        principal.setBirthday(user.getBirthday());
        principal.setPosts(user.getPosts());
        return principal;
    }


    @Override
    public void registration(User user, String password) {
        checkUser(user);
        if (password == null || password.isEmpty()){
            throw new ErrorMessageForUserException("Пароль не может быть пустым");
        }
        createUser(user, password);
    }

    private void checkUser(User user){
        User savedUser = null;
        if (user != null || !user.getEmail().isEmpty()) {
            savedUser = this.userRepository.findByEmailIgnoreCase(user.getEmail());
        } else {
            throw new ErrorMessageForUserException("Email не может быть пустым");
        }
        if (savedUser != null) {
            throw new ErrorMessageForUserException("Пользователь с email =" + savedUser.getEmail() + "  уже зарегистрирован.");
        }
    }

    private void createUser(User user, String password){
        user.setEmail( user.getEmail().toLowerCase() );
        user.setDateRegistration( new Date() );
        User savedUser = this.userRepository.save(user);
        if (savedUser != null) {
            this.passwordRepository.createPassword(this.passwordEncoder.encode(password), savedUser.getId());
        }
    }



    @Override
    public <S extends User> S updateUser(S user){
        System.out.println("!!!!!" + user);
        if (user == null || user.getEmail().isEmpty())
        {
            throw new ErrorMessageForUserException("Email не может быть пустым");
        }
        User userExist = this.userRepository.findByEmailIgnoreCase( user.getEmail() );
        if (userExist.getId() != getPrincipalId()) {
            throw new ErrorMessageForUserException("Вы не являетесь владельцом данной учетной записи.");
        }
        userExist.setFirstName(user.getFirstName());
        userExist.setMiddleName(user.getMiddleName());
        userExist.setSurname(user.getSurname());
        if ( userExist.getBirthday() == null )
            userExist.setBirthday(user.getBirthday());
        User saveUser = this.userRepository.save(userExist);
        if (saveUser == null)
            throw new RuntimeException("Error while updating user");
        updatePrincipal(saveUser);
        return (S) userExist;
    }

    @Override
    public void changePassword(String currentPassword, String newPassword) {
        checkNewPassword(newPassword);
        checkCurrentPassword(currentPassword);
        newPassword = this.passwordEncoder.encode(newPassword);
        int isUpdate = updatePassword(newPassword, getPrincipalId());
        if (isUpdate != 1) {
            throw new ErrorMessageForUserException("Произошла ошибка пароль не был изменён");
        }
    }

    private int updatePassword(String newPassword, long id) {
        return this.passwordRepository.updatePassword(newPassword, id);
    }

    private void checkNewPassword(String newPassword){
        if (newPassword == null || newPassword.isEmpty()) {
            throw new ErrorMessageForUserException("Пароль не может быть пустым");
        }
        if (this.passwordEncoder.matches(newPassword, getPrincipalPassword())) {
            throw new ErrorMessageForUserException("Введённй пароль такоже как и текущий");
        }
    }


    private void checkCurrentPassword(String currentPassword){
        if (currentPassword == null || !this.passwordEncoder.matches(currentPassword, getPrincipalPassword())) {
            throw new ErrorMessageForUserException("Введён невернй пароль");
        }
    }

    @Override
    public void resetPassword(){
        StringBuilder newPassword = new StringBuilder();
        char c = 0;
        for (int i = 0; i < 8; i++){
            c = (char) (48 + Math.random()* 75);
            newPassword.append(c);
        }
        updatePassword(newPassword.toString(), getPrincipalId());

    }


    @Override
    public <S extends User> S save(S user) {
        return this.updateUser(user);
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
    public void changeEmail(String newEmail, String currentPassword) throws ErrorMessageForUserException{
        User principal = getPrincipal();
        checkNewEmail(principal, newEmail);
        checkCurrentPassword(currentPassword);
        this.userRepository.updateEmail(newEmail.toLowerCase(), principal.getId());
        getPrincipal().setEmail(newEmail);
    }

    private void checkNewEmail(User user, String newEmail){
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
    }

}
