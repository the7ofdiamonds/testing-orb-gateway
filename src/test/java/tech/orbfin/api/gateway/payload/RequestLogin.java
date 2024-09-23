package tech.orbfin.api.gateway.payload;

import lombok.Data;

@Data
public class RequestLogin {
    private String username;
    private String password;
    private Location location;
    private String ip;
    private String userAgent;
    private String deviceToken;

    public RequestLogin(String username, String password, Location location){
        this.username = username;
        this.password = password;
        this.location = new Location(location.getLongitude(), location.getLatitude());
    }
}
