package com.avalara.avalara.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestClientRequestSender {
    private static final String HTTP_ERROR ="HTTP error occurred in %s at %s with status text %s";
    private final RestClient restClient;

    public<R>ResponseEntity<R> get(
            String uri,
            Class<R> responseType,
            HttpHeaders headers
    ) {
        return restClient.get()
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    logClientError(uri,response, HttpMethod.GET.name());
                })
                        .toEntity(responseType);
    }


    private void logClientError(String uri, ClientHttpResponse response, String httpMethod) throws IOException {
        var errorMsg = String.format(HTTP_ERROR, httpMethod, uri, response.getStatusText());
        if (response.getStatusCode().is4xxClientError()) {
          //  log.warn(errorMsg);
        } else if (response.getStatusCode().is5xxServerError()) {
            try (var inputStream = response.getBody(); var stringWriter = new StringWriter()) {
                IOUtils.copy(inputStream, stringWriter, StandardCharsets.UTF_8);
           //     log.error("{} Response body: {}", errorMsg, stringWriter.toString());
            }
        } else {
          //  log.error(errorMsg);
        }
    }

}
