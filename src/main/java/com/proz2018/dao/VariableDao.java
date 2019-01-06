package com.proz2018.dao;

import com.proz2018.entities.Device;
import com.proz2018.entities.UserEntity;
import com.proz2018.entities.Variable;
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
    List<Variable> findAllByUser(UserEntity user);
    List<Variable> findAllByUserAndDevice(UserEntity user, Device device);



    @Modifying
    @Transactional
    @Query(value = "update variable set variable_name = :name, description = :description, tags = :tags, enabled = :enabled, unit = :unit where variable_id = :id", nativeQuery = true)
    void update(@Param("id") Integer id,
                @Param("description") String description,
                @Param("unit") String unit,
                @Param("tags") String tags,
                @Param("enabled") Boolean enabled,
                @Param("name") String name);
}
