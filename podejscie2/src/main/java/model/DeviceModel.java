package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceModel {

    @JsonProperty("name")
    private String name;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("tags")
    private String tags;
    @JsonProperty("description")
    private String description;




    public DeviceModel(){}
}


