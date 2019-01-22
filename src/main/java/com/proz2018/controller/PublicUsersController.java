package com.proz2018.controller;

import com.proz2018.dao.UserDao;
import com.proz2018.entities.UserEntity;
import com.proz2018.exception.CustomException;
import com.proz2018.model.UserModel;
import com.proz2018.model.UserModelLogin;
import com.proz2018.security.JwtTokenProvider;
import com.proz2018.security.TokenWraper;
import com.proz2018.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/api/users")
@Data
final class PublicUsersController {

    private UserService userService;

    @Autowired
    public PublicUsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    private void register(@RequestBody UserModel userModel) throws Exception {
        userService.register(userModel);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    private TokenWraper login(@RequestBody UserModelLogin userModelLogin)  {
        return userService.login(userModelLogin);
    }


}


