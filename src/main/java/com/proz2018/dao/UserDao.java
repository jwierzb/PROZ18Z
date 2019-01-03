package com.proz2018.dao;

import com.proz2018.entities.User;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    @Transactional
    Optional<User> findByUserName(String userName);
    @Transactional
    void deleteByUserName(String userName);

    @Modifying
    @Query(value = "insert into user (user_name, user_password, user_email, last_login) VALUES (:userName,:userPassword, :userEmail, :lastLogin)", nativeQuery = true)
    @Transactional
    void saveAndFlush(@Param("userName") String userName, @Param("userPassword") String userPassword,
              @Param("userEmail") String userEmail, @Param("lastLogin") LocalDateTime lastLogin);


}