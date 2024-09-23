package tech.orbfin.api.gateway.payload;

import lombok.Data;

@Data
public class RequestForgot {
    private String email;
    private String username;
}
