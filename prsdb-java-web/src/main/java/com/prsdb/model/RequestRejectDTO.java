package com.prsdb.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestRejectDTO {
    @JsonProperty("ReasonForRejection")
    
    private String reasonForRejection;

    public String getReasonForRejection() {
        return reasonForRejection;
    }

    public void setReasonForRejection(String reasonForRejection) {
        this.reasonForRejection = reasonForRejection;
    }
}