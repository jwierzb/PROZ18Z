package com.proz2018.security;

import com.proz2018.entities.User;

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @ret

2018-12-07 16:39:03.329  INFO 10883 --- [       Thread-2] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'urn an {@link Optional} of a user when login succeeds
     */
    Optional<String> login(String username, String password);
    /**
     * Generate new token for user with {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<String> newToken(String username, String password);
    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return
     */
    Optional<User> findByToken(String token);

    /**
     * Logs out the given input {@code user}.
     *
     * @param user the user to logout
     */

    void logout(User user);
}
