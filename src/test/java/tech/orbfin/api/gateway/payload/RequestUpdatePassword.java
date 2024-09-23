package tech.orbfin.api.gateway.payload;

import lombok.*;

import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Component
public class RequestUpdatePassword {
    private String email;
    private String confirmationCode;
    private String password;
    private String confirmPassword;
}
