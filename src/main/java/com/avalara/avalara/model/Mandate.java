package com.avalara.avalara.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mandate {
    private String countryMandate;
    private String countryCode;
    private String description;
    private String supportedByELRAPI;
    private String mandateFormat;
    private String eInvoicingFlow;
    private String eInvoicingFlowDocumentationLink;
    private List<String> getInvoiceAvailableMediaType;
    private String supportsInboundDigitalDocument;
    private List<InputDataFormat> inputDataFormats;
    private List<WorkflowId> workflowIds;
    private String mandateId;

}
