package com.hit.assure.Model;

import com.google.gson.annotations.SerializedName;

public class OnlyResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

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
}
