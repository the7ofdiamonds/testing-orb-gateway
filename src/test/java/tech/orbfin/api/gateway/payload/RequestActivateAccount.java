package tech.orbfin.api.gateway.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class RequestActivateAccount {
    private String email;
    @JsonProperty("user_activation_key")
    private String userActivationKey;
}
