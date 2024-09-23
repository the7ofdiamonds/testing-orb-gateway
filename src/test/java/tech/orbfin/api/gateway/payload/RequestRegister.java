package tech.orbfin.api.gateway.payload;

import lombok.*;

@Data
@NoArgsConstructor
public class RequestRegister {
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

    public RequestRegister(String username,
        String email,
        String password,
        String confirmPassword,
        String nicename,
        String nickname,
        String firstname,
        String lastname,
        String phone,
        Location location){
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.nicename = nicename;
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.location = new Location(location.getLongitude(), location.getLatitude());
    }
}