package com.avalara.avalara.service.impl;

import com.avalara.avalara.core.BaseClient;
import com.avalara.avalara.core.RestClientRequestSender;
import com.avalara.avalara.model.MandateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Component
public class AvalaraServiceImpl extends BaseClient {

    private static final Logger log = LoggerFactory.getLogger(AvalaraServiceImpl.class);
    public AvalaraServiceImpl(RestClientRequestSender restClient) {
        super(restClient);
    }

    public MandateResponse getMandate(String avalaraVersion, String avalaraClient) {

      //  log.info("Fetching mandates from Avalara API with version: {} and client: {}", avalaraVersion, avalaraClient);
        HttpHeaders headers = this.getAuthAndClientIdHeaders();
        headers.add("avalara-version", avalaraVersion);
        headers.add("X-Avalara-Client", avalaraClient);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<MandateResponse> response = restClient.get(
                    "https://api.sbx.avalara.com/einvoicing/mandates",
                    MandateResponse.class,
                    headers
            );
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
}
