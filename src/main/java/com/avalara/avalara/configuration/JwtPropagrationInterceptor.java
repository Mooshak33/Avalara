package com.avalara.avalara.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Slf4j
public class JwtPropagrationInterceptor implements ClientHttpRequestInterceptor {

    private static final String AUTHORIZATION_BEARER = "Authorization";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
       if(SecurityContextHolder.getContext() != null
               && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getCredentials() != null) {
            String jwtToken = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
         //   log.info("Adding JWT token to request: {}", jwtToken);
            request.getHeaders().add(AUTHORIZATION_BEARER, "Bearer " + jwtToken);

        }
        return execution.execute(request, body);
    }
}
