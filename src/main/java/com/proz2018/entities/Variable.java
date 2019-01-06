package com.proz2018.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "variable")
@Builder
@AllArgsConstructor
public class Variable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variable_id")
    private Integer id;

    @Column(name="variable_name")
    private String name;


    private String tags;
    private String description;
    private String unit;

    @Column(name = "last_value")
    private Float lastValue;

    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserEntity user;


    @JoinColumn(name = "device_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Device device;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonIgnore
    private Timestamp createdAtDate;

    @Column(name = "last_activity")
    @UpdateTimestamp
    @JsonIgnore
    private Timestamp lastActivity;




    @JsonGetter
    public Integer getDeviceId(){return device.getId();}

    @JsonGetter
    public ZonedDateTime getCreatedAtDate() {
        return createdAtDate.toLocalDateTime().atZone(ZoneId.systemDefault());
    }
    @JsonGetter
    public ZonedDateTime getLastActivity() {
        return lastActivity.toLocalDateTime().atZone(ZoneId.systemDefault());
    }


    public Variable(){}

}
