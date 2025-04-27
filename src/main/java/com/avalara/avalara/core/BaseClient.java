package com.avalara.avalara.core;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

@RequiredArgsConstructor
public abstract class BaseClient {

    public static final String AUTH_HEADER = "Authorization";

    protected final RestClientRequestSender restClient;

    protected void addAuthHeaders(HttpHeaders headers)
    {
        if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
        && SecurityContextHolder.getContext().getAuthentication() instanceof AbstractAuthenticationToken authentication) {
            headers.add(AUTH_HEADER, "Bearer " + authentication.getCredentials().toString());
        }
    }

    protected HttpHeaders getAuthAndClientIdHeaders() {
        var httpHeaders = new HttpHeaders();
        this.addAuthHeaders(httpHeaders);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE)));
        return httpHeaders;
    }
}
