package tech.orbfin.api.gateway.payload;

import lombok.*;

@Builder
@Data
public class RequestChangeUsername {
    private String email;
    private String password;
    private String username;
}
