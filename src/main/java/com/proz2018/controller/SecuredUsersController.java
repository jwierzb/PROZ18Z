package com.proz2018.controller;

import com.proz2018.entities.UserEntity;
import com.proz2018.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Data
@RequestMapping("/api/users")
final class SecuredUsersController {

    private UserService userService;

    @GetMapping("/current")
    private Resource<UserEntity> getCurrent() {
        return new Resource<UserEntity>(userService.getCurrent());
    }

    @Autowired
    SecuredUsersController(UserService userService){
        this.userService=userService;
    }

}