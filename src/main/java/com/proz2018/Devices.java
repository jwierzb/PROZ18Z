package com.proz2018;

import java.util.Date;

public class Devices {
    /** Mandatory */
    String deviceLabel;

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

    public String getDeviceLabel() {
        return deviceLabel;
    }


    public String getDeviceName() {
        return deviceName;
    }



    public Boolean getEnabled() {
        return enabled;
    }



    public String getDescription() {
        return description;
    }



    public Date getCreatedAtDate() {
        return createdAtDate;
    }


}
