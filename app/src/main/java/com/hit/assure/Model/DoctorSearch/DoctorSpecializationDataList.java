package com.hit.assure.Model.DoctorSearch;

import com.google.gson.annotations.SerializedName;

public class DoctorSpecializationDataList {

    @SerializedName("speciality")
    private String speciality;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
