package com.hit.assure.Model.ActiveAppointments;

import com.google.gson.annotations.SerializedName;

public class ActiveAppointmentsData {

    @SerializedName("booking_id")
    private String booking_id;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("doctor_name")
    private String doctor_name;

    @SerializedName("doctor_id")
    private String doctor_id;

    @SerializedName("clinic_id")
    private String clinic_id;

    @SerializedName("clinic_lat")
    private String clinic_lat;

    @SerializedName("clinic_lng")
    private String clinic_lng;

    @SerializedName("clinic_name")
    private String clinic_name;

    @SerializedName("booking_date")
    private String booking_date;

    @SerializedName("consultation_type")
    private String consultation_type;

    @SerializedName("from_time")
    private String from_time;

    @SerializedName("to_time")
    private String to_time;

    @SerializedName("status")
    private String status;

    @SerializedName("display_from_time")
    private String display_from_time;

    @SerializedName("display_to_time")
    private String display_to_time;

    public String getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    @SerializedName("room_url")
    private String room_url;

    public String getRoom_url() {
        return room_url;
    }

    public void setRoom_url(String room_url) {
        this.room_url = room_url;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDisplay_to_time() {
        return display_to_time;
    }

    public void setDisplay_to_time(String display_to_time) {
        this.display_to_time = display_to_time;
    }

    public String getDisplay_from_time() {
        return display_from_time;
    }

    public void setDisplay_from_time(String display_from_time) {
        this.display_from_time = display_from_time;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getConsultation_type() {
        return consultation_type;
    }

    public void setConsultation_type(String consultation_type) {
        this.consultation_type = consultation_type;
    }

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

    public String getClinic_lat() {
        return clinic_lat;
    }

    public void setClinic_lat(String clinic_lat) {
        this.clinic_lat = clinic_lat;
    }

    public String getClinic_lng() {
        return clinic_lng;
    }

    public void setClinic_lng(String clinic_lng) {
        this.clinic_lng = clinic_lng;
    }
}
