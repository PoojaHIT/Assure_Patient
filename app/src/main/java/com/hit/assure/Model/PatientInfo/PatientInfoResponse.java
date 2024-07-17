package com.hit.assure.Model.PatientInfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PatientInfoResponse {

    @SerializedName("ResponseCode")
    private int ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("data")
    private PatientInfoData patientInfoData;

    @SerializedName("Treatments")
    private List<PatientInfoSessionlist> patientInfoSessionlistList;

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

    public PatientInfoData getPatientInfoData() {
        return patientInfoData;
    }

    public void setPatientInfoData(PatientInfoData patientInfoData) {
        this.patientInfoData = patientInfoData;
    }

    public List<PatientInfoSessionlist> getPatientInfoSessionlistList() {
        return patientInfoSessionlistList;
    }

    public void setPatientInfoSessionlistList(List<PatientInfoSessionlist> patientInfoSessionlistList) {
        this.patientInfoSessionlistList = patientInfoSessionlistList;
    }
}
