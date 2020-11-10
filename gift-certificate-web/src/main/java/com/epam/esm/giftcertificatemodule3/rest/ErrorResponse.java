package com.epam.esm.giftcertificatemodule3.rest;

public class ErrorResponse {

    private String status;
    private String responseBody;
    private String errorMessage;
    private String errorCode;

    public ErrorResponse() {
    }

    public ErrorResponse(String status, String responseBody, String errorMessage, String errorCode) {
        this.status = status;
        this.responseBody = responseBody;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
