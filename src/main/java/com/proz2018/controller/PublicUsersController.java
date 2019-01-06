package com.proz2018.controller;

import com.proz2018.dao.UserDao;
import com.proz2018.entities.UserEntity;
import com.proz2018.exception.CustomException;
import com.proz2018.model.UserModel;
import com.proz2018.model.UserModelLogin;
import com.proz2018.security.JwtTokenProvider;
import com.proz2018.security.TokenWraper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Data
final class PublicUsersController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserDao users;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    private void register(@RequestBody UserModel userModel) throws Exception {
        if(users.existsByUsername(userModel.getUsername()) || users.existsByEmail(userModel.getEmail())) throw new Exception(); //TODO better exception
        UserEntity userEntity =
                UserEntity.builder()
                        .username(userModel.getUsername())
                        .password((userModel.getPassword()))
                        .enabled(true)
                        .email(userModel.getEmail())
                        .build();
        users.save(userEntity);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    private TokenWraper login(@RequestBody UserModelLogin userModel)  {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));
            return new TokenWraper(jwtTokenProvider.createToken(userModel.getUsername()));
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}


