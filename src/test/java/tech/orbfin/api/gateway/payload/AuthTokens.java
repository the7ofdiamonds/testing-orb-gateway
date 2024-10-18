package tech.orbfin.api.gateway.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
public class AuthTokens {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
