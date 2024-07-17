package com.hit.assure.Model.VcSlotDateData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VcSlotdateDataResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<VcDataDate> vcSlotDateData;

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

    public List<VcDataDate> getVcSlotDateData() {
        return vcSlotDateData;
    }

    public void setVcSlotDateData(List<VcDataDate> vcSlotDateData) {
        this.vcSlotDateData = vcSlotDateData;
    }
}
