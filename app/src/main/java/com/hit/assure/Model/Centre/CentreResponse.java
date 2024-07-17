package com.hit.assure.Model.Centre;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CentreResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("data")
    private List<CentreData> centreData;

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

    public List<CentreData> getCentreData() {
        return centreData;
    }

    public void setCentreData(List<CentreData> centreData) {
        this.centreData = centreData;
    }
}
