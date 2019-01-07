package com.proz2018.dao;

import com.proz2018.entities.Device;
import com.proz2018.entities.UserEntity;
import com.proz2018.entities.Variable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariableDao extends JpaRepository<Variable, Integer> {

    Optional<Variable> findByIdAndUser(Integer id, UserEntity user);
    Page<Variable> findAllByUser(UserEntity user, Pageable pageable);
    List<Variable> findAllByUserAndDevice(UserEntity user, Device device);



}
