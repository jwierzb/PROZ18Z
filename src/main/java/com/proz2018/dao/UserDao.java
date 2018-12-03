package com.proz2018.dao;

import com.proz2018.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    @Modifying
    @Query(value = "insert into user (user_name, user_password) VALUES (:userName,:userPassword)", nativeQuery = true)
    @Transactional
    void save(@Param("userName") String userName,@Param("userPassword") String userPassword);
}
