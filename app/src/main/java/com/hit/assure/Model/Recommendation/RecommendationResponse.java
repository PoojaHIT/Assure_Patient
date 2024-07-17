package com.hit.assure.Model.Recommendation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendationResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;


    @SerializedName("data")
    private List<RecommendationData> recommendationData;

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        ResponseMsg = responseMsg;
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

    public List<RecommendationData> getRecommendationData() {
        return recommendationData;
    }

    public void setRecommendationData(List<RecommendationData> recommendationData) {
        this.recommendationData = recommendationData;
    }
}
