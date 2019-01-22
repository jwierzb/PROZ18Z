package com.proz2018.service;


import com.proz2018.dao.UserDao;
import com.proz2018.entities.UserEntity;
import com.proz2018.exception.CustomException;
import com.proz2018.model.UserModel;
import com.proz2018.model.UserModelLogin;
import com.proz2018.security.JwtTokenProvider;
import com.proz2018.security.TokenWraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class UserService {

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
    }

    public void register(UserModel userModel) throws Exception {
        if(userDao.existsByUsername(userModel.getUsername()) || userDao.existsByEmail(userModel.getEmail())) throw new Exception("User already exists");
        UserEntity userEntity =
                UserEntity.builder()
                        .username(userModel.getUsername())
                        .password(userModel.getPassword())
                        .enabled(true)
                        .email(userModel.getEmail())
                        .build();
        userDao.save(userEntity);
   }

   public TokenWraper login(UserModelLogin userModelLogin) {
       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModelLogin.getUsername(), (userModelLogin.getPassword())));
           updateLastLogin(userModelLogin.getUsername());
           return new TokenWraper(jwtTokenProvider.createToken(userModelLogin.getUsername()));
       } catch (AuthenticationException e) {
           throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
       }
   }

   private void updateLastLogin(String username) {
       UserEntity user = userDao.findByUsername(username);
       user.setLastLogin(Timestamp.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime()));
       userDao.save(user);
   }

    public UserEntity getCurrent() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDao.findByUsername(((UserDetails) principal).getUsername());

    }

}
