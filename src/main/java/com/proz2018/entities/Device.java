package com.proz2018.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer id;

    @Column(name="device_name")
    private String deviceName;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    private boolean enabled;

    private String tags;
    private String description;

    @Column(name = "user_id")
    // todo: consider this is foreign key
    private Integer user;


    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAtDate;
    @Column(name = "last_activity")
    @CreationTimestamp
    private Timestamp lastActivity;




    public Device(String deviceName, Boolean enabled, String description, Integer user) {
        this.deviceName = deviceName;
        this.enabled = enabled;
        this.description = description;
        this.user=user;

    }
    public Device(){}



}
