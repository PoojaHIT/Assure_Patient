package com.hit.assure.Model.Doctorprofile;

import com.google.gson.annotations.SerializedName;

public class DoctorDegreeList {

    @SerializedName("degree")
    private String degree;

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
