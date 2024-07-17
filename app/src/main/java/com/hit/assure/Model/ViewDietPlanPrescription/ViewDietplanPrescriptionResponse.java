package com.hit.assure.Model.ViewDietPlanPrescription;

import com.google.gson.annotations.SerializedName;

public class ViewDietplanPrescriptionResponse {

    @SerializedName("ResponseCode")
    private int ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("data")
    private ViewDietplanPrescriptionData viewDietplanPrescriptionData;


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

    public ViewDietplanPrescriptionData getViewDietplanPrescriptionData() {
        return viewDietplanPrescriptionData;
    }

    public void setViewDietplanPrescriptionData(ViewDietplanPrescriptionData viewDietplanPrescriptionData) {
        this.viewDietplanPrescriptionData = viewDietplanPrescriptionData;
    }
}
