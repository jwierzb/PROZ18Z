package com.proz2018.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.proz2018.entities.Device;
import com.proz2018.entities.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Cacheable;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceDao extends JpaRepository<Device, Integer> {
    List<Device> findByUserId(Integer id);
    Optional<Device> findByUserIdAndId(Integer userId, Integer id);
    List<Device> findAllByUserId(Integer userId);
    Page<Device> findByUser(User user, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update device set device_name = :name, description = :description, tags = :tags, enabled = :enabled where device_id = :id and user_id = :userId", nativeQuery = true)
    void update(@Param("userId") Integer userId,
                      @Param("id") Integer id,
                      @Param("description") String description,
                      @Param("tags") String tags,
                      @Param("enabled") Boolean enabled,
                      @Param("name") String name);
}
