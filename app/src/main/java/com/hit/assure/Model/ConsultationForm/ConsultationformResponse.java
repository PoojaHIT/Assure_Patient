package com.hit.assure.Model.ConsultationForm;

import com.google.gson.annotations.SerializedName;

public class ConsultationformResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("data")
    private ConsultationformData consultationformData;

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

    public ConsultationformData getConsultationformData() {
        return consultationformData;
    }

    public void setConsultationformData(ConsultationformData consultationformData) {
        this.consultationformData = consultationformData;
    }
}
