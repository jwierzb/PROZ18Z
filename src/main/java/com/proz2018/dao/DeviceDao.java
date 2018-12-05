package com.proz2018.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.proz2018.entities.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceDao extends JpaRepository<Device, Integer> {
    List<Device> findByUserId(Integer id);
    Optional<Device> findByUserIdAndId(Integer userId, Integer id);
    List<Device> findAllByUserId(Integer userId);
    List<Device> findAllByUserIdOrderByDeviceNameAsc(Integer userId, Pageable page);
}
