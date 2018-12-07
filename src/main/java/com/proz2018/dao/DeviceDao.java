package com.proz2018.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.proz2018.entities.Device;
import com.proz2018.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceDao extends JpaRepository<Device, Integer> {
    List<Device> findByUserId(Integer id);
    Optional<Device> findByUserIdAndId(Integer userId, Integer id);
    List<Device> findAllByUserId(Integer userId);
    Page<Device> findAllByUser(User user, Pageable pageable);

}
