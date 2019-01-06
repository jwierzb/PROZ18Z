package com.proz2018.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @JoinColumn(name = "device_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Device device;

    @GeneratedValue
    @Column(name = "created_at")
    private LocalDateTime createdAtDate;

    @GeneratedValue
    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    public Variable(String deviceName, String description, UserEntity user) {
        this.deviceName = deviceName;
        this.description = description;
        this.user=user;

    }
    public Variable(){}



}
