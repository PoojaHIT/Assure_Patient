package com.hit.assure.Model.SocialLogin;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialLoginResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;


    @SerializedName("data")
    private List<SocialUserData> socialUserData;

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

    public List<SocialUserData> getSocialUserData() {
        return socialUserData;
    }

    public void setSocialUserData(List<SocialUserData> socialUserData) {
        this.socialUserData = socialUserData;
    }
}
