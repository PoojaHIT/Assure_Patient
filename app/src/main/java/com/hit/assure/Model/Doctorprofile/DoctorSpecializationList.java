package com.hit.assure.Model.Doctorprofile;

import com.google.gson.annotations.SerializedName;

public class DoctorSpecializationList {

    @SerializedName("speciality")
    private String speciality;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }


}
