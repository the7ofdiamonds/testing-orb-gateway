package tech.orbfin.api.gateway.payload;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestLogin {
    private String email;
    private String password;
    private Location location;
    private String ip;
    private String userAgent;
    private String deviceToken;
}
