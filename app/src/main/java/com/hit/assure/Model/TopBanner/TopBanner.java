package com.hit.assure.Model.TopBanner;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopBanner {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<BannerData> bannerData;

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

    public List<BannerData> getBannerData() {
        return bannerData;
    }

    public void setBannerData(List<BannerData> bannerData) {
        this.bannerData = bannerData;
    }
}
