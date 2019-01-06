package com.proz2018.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    @JsonIgnore
    private UserEntity user;


    @Column(name = "created_at")
    @CreationTimestamp
    @JsonIgnore
    private Timestamp createdAtDate;



    @Column(name = "last_activity")
    @UpdateTimestamp
    @JsonIgnore
    private Timestamp lastActivity;

    @Column(name = "variables_count", nullable = true)
    private Integer variablesCount;


    @JsonGetter
    public ZonedDateTime getCreatedAtDate() {
        return createdAtDate.toLocalDateTime().atZone(ZoneId.systemDefault());
    }
    @JsonGetter
    public ZonedDateTime getLastActivity() {
        return lastActivity.toLocalDateTime().atZone(ZoneId.systemDefault());
    }

    public Device(String deviceName, Boolean enabled, String description, String tags) {
        this.deviceName = deviceName;
        this.enabled = enabled;
        this.description = description;
        this.tags = tags;
    }
    public Device(){}



}
