package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueModel
{
    @JsonProperty("timestamp")
    private Date timestamp;
    @JsonProperty("value")
    private Integer value;
    @JsonProperty("variableId")
    private Integer variableId;
    @JsonProperty("unit")
    private String unit;

    public ValueModel(){};
}
