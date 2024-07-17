package com.hit.assure.Model.SocialMedia;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.FormUrlEncoded;

public class SocialMediaResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("data")
    private List<SocialMediaData> socialMediaData;

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public List<SocialMediaData> getSocialMediaData() {
        return socialMediaData;
    }

    public void setSocialMediaData(List<SocialMediaData> socialMediaData) {
        this.socialMediaData = socialMediaData;
    }
}
