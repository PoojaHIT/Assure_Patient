package com.hit.assure.Model.SocialLogin;

import com.google.gson.annotations.SerializedName;

public class SocialUserData {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private String gender;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lng")
    private String lng;

    @SerializedName("application_form")
    private int application_form;

    @SerializedName("skin_application_form")
    private int skin_application_form;

    @SerializedName("nutrition_application_form")
    private int nutrition_application_form;

    @SerializedName("")

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

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
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
}
