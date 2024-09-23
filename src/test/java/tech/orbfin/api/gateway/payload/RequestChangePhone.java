package tech.orbfin.api.gateway.payload;

import lombok.Data;

@Data
public class RequestChangePhone {
    private String email;
    private String phone;
}
