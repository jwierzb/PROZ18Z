package com.proz2018;

import java.util.Date;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
public class Devices {

    String deviceName;
    Boolean enabled;
    String description;

    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="CUST_SEQ")
    String Id;
    @GeneratedValue
    Date createdAtDate;
    @GeneratedValue
    Date lastActivity;

    com.proz2018.model.Context context;

    public Devices(String deviceName, Boolean enabled, String description) {
        this.deviceName = deviceName;
        this.enabled = enabled;
        this.description = description;
        this.createdAtDate = new Date();
    }



}
