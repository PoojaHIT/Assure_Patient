package com.hit.assure.Model.PatientBookingDetails;

import com.google.gson.annotations.SerializedName;

public class PatientBookingDetailsData {

    @SerializedName("booking_id")
    private String booking_id;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("session_image")
    private String session_image;

    @SerializedName("clinic_name")
    private String clinic_name;

    @SerializedName("type")
    private String type;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSession_image() {
        return session_image;
    }

    public void setSession_image(String session_image) {
        this.session_image = session_image;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
