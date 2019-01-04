package com.proz2018.controller;

import com.proz2018.entities.User;
import com.proz2018.security.TokenWraper;
import com.proz2018.security.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@Data
@RequestMapping("/api/users")
final class SecuredUsersController {

    @NonNull
    private UserAuthenticationService authentication;

    @GetMapping("/current")
    private Resource<User> getCurrent(@AuthenticationPrincipal final User user) {
        return new Resource<User> (user);
    }

    @GetMapping("/token")
    private Resource<TokenWraper> newToken(@AuthenticationPrincipal final User user)
    {
        return new Resource<TokenWraper> (new TokenWraper(authentication
                .newToken(user.getUsername(), user.getPassword())
                .orElseThrow(() -> new RuntimeException("AuthenticationService error(!)"))));
    }


    @GetMapping("/logout")
    private boolean logout(@AuthenticationPrincipal final User user) {
        authentication.logout(user);
        return true;
    }
}