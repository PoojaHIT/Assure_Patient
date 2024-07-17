package com.hit.assure.Model.OtpVerification;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private String gender;

    @SerializedName("lat")
    private String lat;

    @SerializedName("long")
    private String loong;

    @SerializedName("application_form")
    private String application_form;

    @SerializedName("skin_application_form")
    private String skin_application_form;

    @SerializedName("nutrition_application_form")
    private String nutrition_application_form;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getApplication_form() {
        return application_form;
    }

    public void setApplication_form(String application_form) {
        this.application_form = application_form;
    }

    public String getSkin_application_form() {
        return skin_application_form;
    }

    public void setSkin_application_form(String skin_application_form) {
        this.skin_application_form = skin_application_form;
    }

    public String getNutrition_application_form() {
        return nutrition_application_form;
    }

    public void setNutrition_application_form(String nutrition_application_form) {
        this.nutrition_application_form = nutrition_application_form;
    }
}
