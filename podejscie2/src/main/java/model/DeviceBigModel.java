package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceBigModel {

    @JsonProperty("id")
    private int id;
    @JsonProperty("deviceName")
    private String deviceName;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("tags")
    private String tags;
    @JsonProperty("description")
    private String description;
    @JsonProperty("createdAtDate")
    private String createdAtDate;
    @JsonProperty("lastActivity")
    private String lastActivity;




    public DeviceBigModel(){}
}


