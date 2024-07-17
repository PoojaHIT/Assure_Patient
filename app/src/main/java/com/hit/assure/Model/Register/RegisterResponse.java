package com.hit.assure.Model.Register;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("application_form")
    private int application_form;

    @SerializedName("skin_application_form")
    private int skin_application_form;

    @SerializedName("nutrition_application_form")
    private int nutrition_application_form;

    @SerializedName("phone")
    private String phone;

    @SerializedName("message")
    private String message;

    @SerializedName("otp_code")
    private String otp_code;

    @SerializedName("token")
    private String token;

    @SerializedName("lat")
    private String lat;

    @SerializedName("long")
    private String loong;

    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtp_code() {
        return otp_code;
    }

    public void setOtp_code(String otp_code) {
        this.otp_code = otp_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLoong() {
        return loong;
    }

    public void setLoong(String loong) {
        this.loong = loong;
    }

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

    public int getApplication_form() {
        return application_form;
    }

    public void setApplication_form(int application_form) {
        this.application_form = application_form;
    }

    public int getSkin_application_form() {
        return skin_application_form;
    }

    public void setSkin_application_form(int skin_application_form) {
        this.skin_application_form = skin_application_form;
    }

    public int getNutrition_application_form() {
        return nutrition_application_form;
    }

    public void setNutrition_application_form(int nutrition_application_form) {
        this.nutrition_application_form = nutrition_application_form;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
