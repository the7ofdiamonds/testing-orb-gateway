package tech.orbfin.api.gateway.payload;

import lombok.*;

import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Setter
@Getter
@Component
public class RequestChangeName {
    private String email;
    private String firstName;
    private String lastName;
}
