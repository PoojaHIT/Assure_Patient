package com.hit.assure.Model.VirtualConsultant;

import com.google.gson.annotations.SerializedName;

public class DoctorDegreeData {

    @SerializedName("degree")
    private String degree;

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
