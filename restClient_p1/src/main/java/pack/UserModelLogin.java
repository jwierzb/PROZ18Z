package pack;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class UserModelLogin {

    @NotNull
    String password;

    @NotNull
    String username;
}
