package com.proz2018.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserModel {


    @NotNull
    @Email
    String email;

    @NotNull
    String password;

    @NotNull
    String username;

}
