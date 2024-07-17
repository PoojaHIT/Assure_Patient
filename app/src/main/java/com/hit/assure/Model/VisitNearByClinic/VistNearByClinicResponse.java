package com.hit.assure.Model.VisitNearByClinic;


import com.google.gson.annotations.SerializedName;
import com.hit.assure.Model.VirtualConsultant.VirtualConsultantData;

import java.util.List;

public class VistNearByClinicResponse {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<VisitNearByData> visitNearByData;

    public List<VisitNearByData> getVisitNearByData() {
        return visitNearByData;
    }

    public void setVisitNearByData(List<VisitNearByData> visitNearByData) {
        this.visitNearByData = visitNearByData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
