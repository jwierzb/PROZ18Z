package com.proz2018.security;


import com.proz2018.dao.UserDao;
import com.proz2018.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserDao userRepository;


    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {


        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("UserEntity '" + username + "' not found");
        }

        return user;
    }




}