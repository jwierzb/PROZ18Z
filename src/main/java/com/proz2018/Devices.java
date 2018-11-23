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
    /** Mandatory */
    String deviceLabel;
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="CUST_SEQ")
    String id;
    /** Default */
    String deviceName;
    Boolean enabled;
    String description;

    Date createdAtDate;
    Date lastActivity;

    com.proz2018.model.Context context;

    public Devices(String deviceLabel, String deviceName, Boolean enabled, String description, Date createdAtDate) {
        this.deviceLabel = deviceLabel;
        this.deviceName = deviceName;
        this.enabled = enabled;
        this.description = description;
        this.createdAtDate = createdAtDate;
    }



}
