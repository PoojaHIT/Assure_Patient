package com.hit.assure.Model.DoctorSearch;

import com.google.gson.annotations.SerializedName;

public class DoctorServicesDataList {

    @SerializedName("service")
    private String service;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
