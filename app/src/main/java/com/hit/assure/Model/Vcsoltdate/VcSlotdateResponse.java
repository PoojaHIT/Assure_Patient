package com.hit.assure.Model.Vcsoltdate;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VcSlotdateResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<VcSlotDateData> vcSlotDateData;

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

    public List<VcSlotDateData> getVcSlotDateData() {
        return vcSlotDateData;
    }

    public void setVcSlotDateData(List<VcSlotDateData> vcSlotDateData) {
        this.vcSlotDateData = vcSlotDateData;
    }
}
