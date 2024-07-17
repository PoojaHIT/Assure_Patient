package com.hit.assure.Model.Rating;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RatingResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private String count;

    @SerializedName("data")
    private List<RatingListData> ratingListData;

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

    public List<RatingListData> getRatingListData() {
        return ratingListData;
    }

    public void setRatingListData(List<RatingListData> ratingListData) {
        this.ratingListData = ratingListData;
    }
}
