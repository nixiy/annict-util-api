package com.example.annictUtil.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "annict")
@Data
public class AnnictProperties {
    private String accessToken;
    private String graphqlEndpoint;
    private long readTimeoutMs;
}
