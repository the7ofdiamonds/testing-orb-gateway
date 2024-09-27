package tech.orbfin.api.gateway.payload;

import lombok.*;

@Builder
@Data
public class RequestVerify {
    private String email;
    private String password;
    private String confirmationCode;
}
