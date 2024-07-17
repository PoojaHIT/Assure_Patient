package com.hit.assure.Model.VirtualConsultant;

import com.google.gson.annotations.SerializedName;

public class DoctorSpecializationData {

    @SerializedName("speciality")
    private String speciality;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
