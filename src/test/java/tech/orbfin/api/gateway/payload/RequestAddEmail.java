package tech.orbfin.api.gateway.payload;

import lombok.*;

@Builder
@Data
public class RequestAddEmail {
    private String token;
    private String username;
    private String password;
    private String email;
    private String newEmail;
}
