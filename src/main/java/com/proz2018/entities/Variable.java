package com.proz2018.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "variable")
public class Variable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variable_id")
    private Integer id;

    @Column(name="variable_name")
    private String deviceName;


    private String tags;
    private String description;

    // Foreign key
    @Column(name = "user_id")
    private Integer userId;

    // Foreign key
    @Column(name = "device_id")
    private Integer deviceId;

    @GeneratedValue
    @Column(name = "created_at")
    private LocalDateTime createdAtDate;

    @GeneratedValue
    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    public Variable(String deviceName, String description, Integer userId) {
        this.deviceName = deviceName;
        this.description = description;
        this.userId=userId;

    }
    public Variable(){}



}
