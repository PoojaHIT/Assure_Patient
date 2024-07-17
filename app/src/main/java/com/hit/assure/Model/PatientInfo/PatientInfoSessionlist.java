package com.hit.assure.Model.PatientInfo;

import com.google.gson.annotations.SerializedName;

public class PatientInfoSessionlist {

    @SerializedName("session_list")
    private String session_list;

    @SerializedName("booking_id")
    private String booking_id;

    @SerializedName("date")
    private String date;

    @SerializedName("prescription")
    private int prescription;

    public String getSession_list() {
        return session_list;
    }

    public void setSession_list(String session_list) {
        this.session_list = session_list;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrescription() {
        return prescription;
    }

    public void setPrescription(int prescription) {
        this.prescription = prescription;
    }
}
