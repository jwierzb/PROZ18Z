package com.proz2018.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement

public class VariableModel {


    private String name;


    private String tags;
    private String description;
    private String unit;




    public VariableModel(){}
}


