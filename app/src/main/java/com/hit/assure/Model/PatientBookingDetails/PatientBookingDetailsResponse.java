package com.hit.assure.Model.PatientBookingDetails;

import com.google.gson.annotations.SerializedName;

public class PatientBookingDetailsResponse {

    @SerializedName("ResponseCode")
    private int ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("data")
    private PatientBookingDetailsData patientBookingDetailsData;

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

    public PatientBookingDetailsData getPatientBookingDetailsData() {
        return patientBookingDetailsData;
    }

    public void setPatientBookingDetailsData(PatientBookingDetailsData patientBookingDetailsData) {
        this.patientBookingDetailsData = patientBookingDetailsData;
    }
}
