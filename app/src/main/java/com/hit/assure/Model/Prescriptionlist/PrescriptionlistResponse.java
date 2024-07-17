package com.hit.assure.Model.Prescriptionlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrescriptionlistResponse {

    @SerializedName("ResponseCode")
    private int ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("data")
    private List<Prescriprionlistdata> prescriprionlistdataList;

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

    public List<Prescriprionlistdata> getPrescriprionlistdataList() {
        return prescriprionlistdataList;
    }

    public void setPrescriprionlistdataList(List<Prescriprionlistdata> prescriprionlistdataList) {
        this.prescriprionlistdataList = prescriprionlistdataList;
    }
}
