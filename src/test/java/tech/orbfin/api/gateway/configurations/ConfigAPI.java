package tech.orbfin.api.gateway.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigAPI {
    public static String BASE_URI = "http://localhost:8080";
}