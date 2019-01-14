package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import model.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Quote
{
    private String type;
    private Value value;
}
