package tech.orbfin.api.gateway.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class RequestActivateAccount {
    private String email;
    private String userActivationCode;
}
