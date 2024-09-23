package tech.orbfin.api.gateway.payload;

import lombok.*;

import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Component
public class RequestDeleteAccount {
    private String email;
    private String username;
    private String confirmationCode;
}
