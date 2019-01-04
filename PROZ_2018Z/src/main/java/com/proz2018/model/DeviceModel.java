package com.proz2018.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@Data
@XmlRootElement

public class DeviceModel {

    private Integer id;

    private String deviceName;

    private boolean enabled;

    private String tags;
    private String description;

    private Integer user;

    private Timestamp createdAtDate;
    private Timestamp lastActivity;




    public DeviceModel(String deviceName, Boolean enabled, String description, Integer user) {
        this.deviceName = deviceName;
        this.enabled = enabled;
        this.description = description;
        this.user=user;

    }
    public DeviceModel(){}



}
