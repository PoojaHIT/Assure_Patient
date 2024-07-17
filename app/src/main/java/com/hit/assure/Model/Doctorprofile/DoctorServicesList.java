package com.hit.assure.Model.Doctorprofile;

import com.google.gson.annotations.SerializedName;

public class DoctorServicesList {

    @SerializedName("service")
    private String service;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
