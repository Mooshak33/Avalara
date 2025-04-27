package com.avalara.avalara.controllers;

import com.avalara.avalara.model.MandateResponse;
import com.avalara.avalara.service.AvalaraService;
import com.avalara.avalara.service.impl.AvalaraServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/einvoicing")
public class AvalaraController {

    @Autowired
    private AvalaraServiceImpl avalaraService;
    /**
     * This endpoint is used to get the list of mandates.
     *
     * @param avalaraVersion The Avalara version.
     * @param avalaraClient  The Avalara client.
     * @return The MandateResponse object containing the list of mandates.
     */
    @GetMapping("/mandates")
    public ResponseEntity<MandateResponse> getMandates(@RequestHeader("avalara-version") String avalaraVersion,
                                                      @RequestHeader("X-Avalara-Client") String avalaraClient) {

      return ResponseEntity.ok(avalaraService.getMandate(avalaraVersion, avalaraClient));
    }

}
