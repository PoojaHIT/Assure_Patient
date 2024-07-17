package com.hit.assure.Model.Doctorprofile;

import androidx.recyclerview.widget.LinearSmoothScroller;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorProfileresponse {
    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<DoctorProfileData> doctorProfileDataList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DoctorProfileData> getDoctorProfileDataList() {
        return doctorProfileDataList;
    }

    public void setDoctorProfileDataList(List<DoctorProfileData> doctorProfileDataList) {
        this.doctorProfileDataList = doctorProfileDataList;
    }
}
