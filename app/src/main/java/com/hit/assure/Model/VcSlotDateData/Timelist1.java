package com.hit.assure.Model.VcSlotDateData;

import com.google.gson.annotations.SerializedName;

public class Timelist1 {
    @SerializedName("from_time")
    private String from_time;

    @SerializedName("to_time")
    private String to_time;

    @SerializedName("status")
    private String status;

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
