package com.proz2018.controller;

import com.proz2018.dao.UserDao;
import com.proz2018.entities.UserEntity;
import com.proz2018.security.TokenWraper;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Data
@RequestMapping("/api/users")
final class SecuredUsersController {

    @Autowired
    UserDao userDao;

    @GetMapping("/current")
    private Resource<UserEntity> getCurrent() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new Resource<UserEntity> (userDao.findByUsername(((UserDetails) principal).getUsername()));
    }

}