package com.proz2018.dao;

import com.proz2018.entities.Device;
import com.proz2018.entities.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariableDao extends JpaRepository<Variable, Integer> {
    List<Variable> findByUserId(Integer id);
    List<Variable> findByUserIdAndDeviceId(Integer userId, Integer id);
    void deleteAllByDeviceId(Integer id);
}
