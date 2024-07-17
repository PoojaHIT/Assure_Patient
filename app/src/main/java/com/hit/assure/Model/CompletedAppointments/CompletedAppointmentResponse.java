package com.hit.assure.Model.CompletedAppointments;

import com.google.gson.annotations.SerializedName;
import com.hit.assure.Model.ActiveAppointments.ActiveAppointmentsData;

import java.util.List;

public class CompletedAppointmentResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<CompletedAppointmentsData> completedAppointmentsData;

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

    public List<CompletedAppointmentsData> getCompletedAppointmentsData() {
        return completedAppointmentsData;
    }

    public void setCompletedAppointmentsData(List<CompletedAppointmentsData> completedAppointmentsData) {
        this.completedAppointmentsData = completedAppointmentsData;
    }
}
