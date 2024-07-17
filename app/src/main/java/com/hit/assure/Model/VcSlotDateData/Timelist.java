package com.hit.assure.Model.VcSlotDateData;

import com.google.gson.annotations.SerializedName;

public class Timelist {
    @SerializedName("from_time")
    private String from_time;

    @SerializedName("to_time")
    private String to_time;

    @SerializedName("display_from_time")
    private String display_from_time;

    @SerializedName("display_to_time")
    private String display_to_time;


    public String getDisplay_from_time() {
        return display_from_time;
    }

    public void setDisplay_from_time(String display_from_time) {
        this.display_from_time = display_from_time;
    }

    public String getDisplay_to_time() {
        return display_to_time;
    }

    public void setDisplay_to_time(String display_to_time) {
        this.display_to_time = display_to_time;
    }

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
