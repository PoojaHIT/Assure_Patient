package com.hit.assure.Model.ReviewTwo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewTwoResponse {

    @SerializedName("review_count")
    private String review_count;

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("data")
    private List<ReviewTwoData> reviewTwoDataList;

    public List<ReviewTwoData> getReviewTwoDataList() {
        return reviewTwoDataList;
    }

    public void setReviewTwoDataList(List<ReviewTwoData> reviewTwoDataList) {
        this.reviewTwoDataList = reviewTwoDataList;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

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

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        ResponseMsg = responseMsg;
    }
}
