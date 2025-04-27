package com.avalara.avalara.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rest-client")
@EnableConfigurationProperties
@Configuration
@Data
public class RestClientProperties {
    private int connectionTimeout;
    private int socketTimeout;
}
