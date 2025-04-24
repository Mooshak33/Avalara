package com.avalara.avalara.service.impl;

import com.avalara.avalara.model.MandateResponse;
import com.avalara.avalara.service.AvalaraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
@Slf4j
@Service
public class AvalaraServiceImpl implements AvalaraService {
    @Autowired
    RestTemplate restTemplate;
    @Override
    public MandateResponse getMandate(String avalaraVersion, String avalaraClient) {

      //  log.info("Fetching mandates from Avalara API with version: {} and client: {}", avalaraVersion, avalaraClient);
        HttpHeaders headers = prepareHeaders(avalaraVersion, avalaraClient);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<MandateResponse> response = restTemplate.exchange("https://api.sbx.avalara.com/einvoicing/mandates",
                    HttpMethod.GET,
                    entity,
                    MandateResponse.class);
            MandateResponse mandateResponse = response.getBody();
            if (mandateResponse != null) {
              //  log.info("Successfully fetched mandates: {}", mandateResponse);
                return mandateResponse;
            } else {
              //  log.error("Failed to fetch mandates, response body is null");
            }
        } catch (Exception e) {
          //  log.error("Error fetching mandates from Avalara API: {}", e.getMessage());
        }
        return null;
    }

    private HttpHeaders prepareHeaders(String avalaraVersion, String avalaraClient) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
