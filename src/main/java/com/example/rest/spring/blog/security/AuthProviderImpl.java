package com.example.rest.spring.blog.security;

import com.example.rest.spring.blog.models.UserPassword;
import com.example.rest.spring.blog.models.User;
import com.example.rest.spring.blog.repositories.PasswordRepository;
import com.example.rest.spring.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        User user = this.userService.findByEmailIgnoreCase(email);
        checkEmail(user, email);
        checkPassword(this.passwordRepository.findById(user.getId()), authentication.getCredentials().toString());

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    private void checkEmail(User user, String email){
        if (user == null || !user.getEmail().equalsIgnoreCase(email)){
            throw  new UsernameNotFoundException("Пользователь не найден");
        }
    }
    private void checkPassword(UserPassword authUser, String password){
        if (!this.passwordEncoder.matches(password, authUser.getPassword())) {
            throw new BadCredentialsException("Не верный пароль");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


