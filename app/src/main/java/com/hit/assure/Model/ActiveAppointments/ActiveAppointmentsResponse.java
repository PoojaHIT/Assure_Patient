package com.hit.assure.Model.ActiveAppointments;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActiveAppointmentsResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<ActiveAppointmentsData> activeAppointmentsData;

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

    public List<ActiveAppointmentsData> getActiveAppointmentsData() {
        return activeAppointmentsData;
    }

    public void setActiveAppointmentsData(List<ActiveAppointmentsData> activeAppointmentsData) {
        this.activeAppointmentsData = activeAppointmentsData;
    }
}
