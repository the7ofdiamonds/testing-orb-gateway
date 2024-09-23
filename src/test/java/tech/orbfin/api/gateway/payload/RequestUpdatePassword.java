package tech.orbfin.api.gateway.payload;

import lombok.*;

@Builder
@Data
public class RequestUpdatePassword {
    private String email;
    private String confirmationCode;
    private String password;
    private String confirmPassword;
}
