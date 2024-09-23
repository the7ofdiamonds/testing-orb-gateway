package tech.orbfin.api.gateway.payload;

import lombok.*;

@Data
public class RequestVerify {
    private String email;
    private String confirmationCode;
}
