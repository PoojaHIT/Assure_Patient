package com.hit.assure.Model.DoctorSearch;

import com.google.gson.annotations.SerializedName;

public class DoctorDegreeDataList {

    @SerializedName("degree")
    private String degree;

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }


}
