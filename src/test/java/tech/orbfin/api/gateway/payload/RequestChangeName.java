package tech.orbfin.api.gateway.payload;

import lombok.*;

@Builder
@Data
public class RequestChangeName {
    private String email;
    private String firstName;
    private String lastName;
}
