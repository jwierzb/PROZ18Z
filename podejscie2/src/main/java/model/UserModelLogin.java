package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class UserModelLogin {

    @NotNull
    String password;

    @NotNull
    String username;
}
