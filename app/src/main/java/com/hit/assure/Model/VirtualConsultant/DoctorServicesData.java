package com.hit.assure.Model.VirtualConsultant;

import com.google.gson.annotations.SerializedName;

public class DoctorServicesData {

    @SerializedName("service")
    private String service;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
