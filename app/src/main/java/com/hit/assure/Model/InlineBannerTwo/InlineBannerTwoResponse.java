package com.hit.assure.Model.InlineBannerTwo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InlineBannerTwoResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<InlineBannerTwoData>  inlineBannerDataList;

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

    public List<InlineBannerTwoData> getInlineBannerDataList() {
        return inlineBannerDataList;
    }

    public void setInlineBannerDataList(List<InlineBannerTwoData> inlineBannerDataList) {
        this.inlineBannerDataList = inlineBannerDataList;
    }
}
