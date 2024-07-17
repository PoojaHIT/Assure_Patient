package com.hit.assure.Model.PatientDetailsFrom;

import com.google.gson.annotations.SerializedName;

public class PatientDetailsResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("application_form")
    private String application_form;

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        ResponseMsg = responseMsg;
    }

    public String getApplication_form() {
        return application_form;
    }

    public void setApplication_form(String application_form) {
        this.application_form = application_form;
    }
}
