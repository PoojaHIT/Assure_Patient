package com.hit.assure.Model.CurrentSessionDoctorlist;

import com.google.gson.annotations.SerializedName;

public class CurrentSessionDoctorlistData {

    @SerializedName("doctor_id")
    private String doctor_id;

    @SerializedName("doctor_name")
    private String doctor_name;

    @SerializedName("user_image")
    private String user_image;

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
