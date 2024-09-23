package tech.orbfin.api.gateway.payload;

import lombok.*;

@Builder
@Data
public class RequestRemoveEmail {
    private String email;
    private String password;
    private String removeEmail;
}
