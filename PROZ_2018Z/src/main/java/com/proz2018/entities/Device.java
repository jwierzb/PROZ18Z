package com.proz2018.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    @Size(max=200)
    private String deviceName;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean enabled;

    @Size(max=200)
    private String tags;

    @Size(max=200)
    private String description;

    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAtDate;

    @Column(name = "last_activity")
    @CreationTimestamp
    private Timestamp lastActivity;

    @Transient
    @JsonInclude
    private int number_of_variables = 0;


    public Device(String deviceName, Boolean enabled, String description, String tags) {
        this.deviceName = deviceName;
        this.enabled = enabled;
        this.description = description;
        this.tags = tags;
    }
    public Device(){}



}
