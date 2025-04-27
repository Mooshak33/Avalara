package com.avalara.avalara.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
    private final RestClientProperties restClientProperties;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(httpComponentsClientHttpRequestFactory())
                .build();
    }

    private HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        var config = ConnectionConfig.custom()
                .setConnectTimeout(restClientProperties.getConnectionTimeout(), TimeUnit.MICROSECONDS)
                .setSocketTimeout(restClientProperties.getSocketTimeout(), TimeUnit.MICROSECONDS)
                .build();
        var connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(config)
                .build();
        var httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .build();
        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return requestFactory;
    }

}
