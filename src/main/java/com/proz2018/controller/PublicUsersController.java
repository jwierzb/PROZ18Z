package com.proz2018.controller;

import com.proz2018.dao.UserDao;
import com.proz2018.entities.User;
import com.proz2018.exception.InvalidPasswordException;
import com.proz2018.security.TokenWraper;
import com.proz2018.security.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/users")
@Data
final class PublicUsersController {
    @NonNull
    private UserAuthenticationService authentication;
    @NonNull
    private UserDao users;

    @PostMapping("/register")
    private Resource<TokenWraper> register(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password,
            @RequestParam("email") final String email) {
        users.saveAndFlush(username, password, email, LocalDateTime.now());
        return login(username, password);
    }

    @PostMapping("/login")
    private Resource<TokenWraper> login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return new Resource <TokenWraper> (new TokenWraper(
            authentication
                .login(username, password)
                .orElseThrow(() -> new InvalidPasswordException(username))));
    }


}


