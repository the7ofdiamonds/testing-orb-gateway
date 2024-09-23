package tech.orbfin.api.gateway.payload;

import lombok.*;

import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Component
public class RequestAddEmail {
    private String token;
    private String username;
    private String password;
    private String email;
    private String newEmail;
}
