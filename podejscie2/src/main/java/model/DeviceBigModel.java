package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceBigModel {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("deviceName")
    private String deviceName;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("tags")
    private String tags;
    @JsonProperty("description")
    private String description;
    @JsonProperty("createdAtDate")
    private Date createdAtDate;
    @JsonProperty("lastActivity")
    private Date lastActivity;




    public DeviceBigModel(){}
}


