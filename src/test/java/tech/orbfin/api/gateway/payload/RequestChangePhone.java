package tech.orbfin.api.gateway.payload;

import lombok.*;

import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Component
public class RequestChangePhone {
    private String email;
    private String phone;
}
