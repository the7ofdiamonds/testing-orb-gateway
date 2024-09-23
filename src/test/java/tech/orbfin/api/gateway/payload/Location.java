package tech.orbfin.api.gateway.payload;

import lombok.*;

@AllArgsConstructor
@Data
public class Location {
    private double longitude;
    private double latitude;
}
