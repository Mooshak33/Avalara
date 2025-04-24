package com.avalara.avalara.service;

import com.avalara.avalara.model.MandateResponse;

public interface AvalaraService {
    public MandateResponse getMandate(String avalaraVersion, String avalaraClient);
}
