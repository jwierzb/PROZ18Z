package com.proz2018.dao;

import com.proz2018.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
    @Transactional
    UserEntity findByUsername(String userName);

    @Transactional
    void deleteByUsername(String userName);

    Boolean existsByUsername(String name);
    Boolean existsByEmail(String email);


}