package tech.orbfin.api.gateway.payload;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestChangeNickname {
    private String email;
    private String password;
    private String nickname;
}
