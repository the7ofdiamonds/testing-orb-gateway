package tech.orbfin.api.gateway.payload;

import lombok.*;

@Builder
@Data
public class RequestDeleteAccount {
    private String email;
    private String username;
    private String confirmationCode;
}
