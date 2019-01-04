package com.proz2018.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
public class TokenWraper {
    @NonNull
    private String token;

}
