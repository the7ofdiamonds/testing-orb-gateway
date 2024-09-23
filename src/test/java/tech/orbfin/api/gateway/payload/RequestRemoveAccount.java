package tech.orbfin.api.gateway.payload;

import lombok.*;

import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Component
public class RequestRemoveAccount {
    private String email;
    private String password;
    private String confirmationCode;
}
