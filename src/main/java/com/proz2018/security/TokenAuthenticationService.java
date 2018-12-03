package com.proz2018.security;

import com.google.common.collect.ImmutableMap;
import com.proz2018.dao.UserDao;
import com.proz2018.entities.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class TokenAuthenticationService implements UserAuthenticationService {
    @NonNull
    TokenService tokens;
    @NonNull
    UserDao users;

    @Override
    public Optional<String> login(final String username, final String password) {
        return users
                .findByUserName(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> tokens.expiring(ImmutableMap.of("username", username)));
    }

    public Optional<String> newToken(final String username, final String password)
    {
        return users
                .findByUserName(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> tokens.expiring(ImmutableMap.of("username", username)));
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional
                .of(tokens.verify(token))
                .map(map -> map.get("username"))
                .flatMap(users::findByUserName);
    }

    @Override
    public void logout(final User user) {
        // Nothing to doy
    }
}