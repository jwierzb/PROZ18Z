package pack;

import lombok.Data;

@Data
public class AuthorizationToken
{
    private String token;

    @Override
    public String toString()
    {
        return "{ \"Authorization\": \"Bearer " + token + "\"}";
    }
}
