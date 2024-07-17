package com.hit.assure.Model.UserDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetailsResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<UserDetailsData> userDetailsData;

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

    public List<UserDetailsData> getUserDetailsData() {
        return userDetailsData;
    }

    public void setUserDetailsData(List<UserDetailsData> userDetailsData) {
        this.userDetailsData = userDetailsData;
    }
}
