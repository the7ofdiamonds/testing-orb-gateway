package tech.orbfin.api.gateway.payload;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestSignup {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String nicename;
    private String nickname;
    private String firstname;
    private String lastname;
    private String phone;
    private Location location;
    private String ip;
    private String userAgent;
}