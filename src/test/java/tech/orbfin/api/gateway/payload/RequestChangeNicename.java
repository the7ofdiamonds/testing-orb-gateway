package tech.orbfin.api.gateway.payload;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestChangeNicename {
    private String email;
    private String password;
    private String nicename;
}
