package com.proz2018.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@Data
@XmlRootElement
public class DeviceModel {


    private String name;

    private boolean enabled;

    private String tags;
    private String description;




    public DeviceModel(){}
}


