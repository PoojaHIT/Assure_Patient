package com.hit.assure.Model.InlineBanner;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InlineBannerResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<InlineBannerData> inlineBannerDataList;

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

    public List<InlineBannerData> getInlineBannerDataList() {
        return inlineBannerDataList;
    }

    public void setInlineBannerDataList(List<InlineBannerData> inlineBannerDataList) {
        this.inlineBannerDataList = inlineBannerDataList;
    }
}
