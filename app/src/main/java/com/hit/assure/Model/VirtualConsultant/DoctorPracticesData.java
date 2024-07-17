package com.hit.assure.Model.VirtualConsultant;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorPracticesData {

    @SerializedName("clinic_id")
    private String clinic_id;

    @SerializedName("clinic_lat")
    private String clinic_lat;

    @SerializedName("clinic_lng")
    private String clinic_lng;

    @SerializedName("clinic_image")
    private String clinic_image;

    @SerializedName("clinic_name")
    private String clinic_name;

    @SerializedName("clinic_phone")
    private String clinic_phone;

    @SerializedName("consultation_charges")
    private String consultation_charges;

    @SerializedName("clinic_address")
    private String clinic_address;

    @SerializedName("clinic_state")
    private String clinic_state;

    @SerializedName("clinic_city")
    private String clinic_city;

    @SerializedName("clinic_pincode")
    private String clinic_pincode;

    @SerializedName("opening_day")
    private List<OpeningDayData> openingDayData;

    @SerializedName("working_hour")
    private String working_hour;

    public String getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(String clinic_id) {
        this.clinic_id = clinic_id;
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

    public String getClinic_image() {
        return clinic_image;
    }

    public void setClinic_image(String clinic_image) {
        this.clinic_image = clinic_image;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getClinic_phone() {
        return clinic_phone;
    }

    public void setClinic_phone(String clinic_phone) {
        this.clinic_phone = clinic_phone;
    }

    public String getConsultation_charges() {
        return consultation_charges;
    }

    public void setConsultation_charges(String consultation_charges) {
        this.consultation_charges = consultation_charges;
    }

    public String getClinic_address() {
        return clinic_address;
    }

    public void setClinic_address(String clinic_address) {
        this.clinic_address = clinic_address;
    }

    public String getClinic_state() {
        return clinic_state;
    }

    public void setClinic_state(String clinic_state) {
        this.clinic_state = clinic_state;
    }

    public String getClinic_city() {
        return clinic_city;
    }

    public void setClinic_city(String clinic_city) {
        this.clinic_city = clinic_city;
    }

    public String getClinic_pincode() {
        return clinic_pincode;
    }

    public void setClinic_pincode(String clinic_pincode) {
        this.clinic_pincode = clinic_pincode;
    }

    public List<OpeningDayData> getOpeningDayData() {
        return openingDayData;
    }

    public void setOpeningDayData(List<OpeningDayData> openingDayData) {
        this.openingDayData = openingDayData;
    }

    public String getWorking_hour() {
        return working_hour;
    }

    public void setWorking_hour(String working_hour) {
        this.working_hour = working_hour;
    }
}
