package com.hit.assure.Model.ReviewList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewListResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private String count;

    @SerializedName("data")
    private List<ReviewListData> reviewListData;

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ReviewListData> getReviewListData() {
        return reviewListData;
    }

    public void setReviewListData(List<ReviewListData> reviewListData) {
        this.reviewListData = reviewListData;
    }
}
