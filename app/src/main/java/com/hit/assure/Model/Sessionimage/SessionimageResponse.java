package com.hit.assure.Model.Sessionimage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SessionimageResponse {

    @SerializedName("ResponseCode")
    private int ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("data")
    private List<SessionimageData> sessionimageData;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
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

    public List<SessionimageData> getSessionimageData() {
        return sessionimageData;
    }

    public void setSessionimageData(List<SessionimageData> sessionimageData) {
        this.sessionimageData = sessionimageData;
    }
}
