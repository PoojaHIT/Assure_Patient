package com.hit.assure.Model.SearchBlog;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchBlogResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;


    @SerializedName("data")
    private List<SearchBlogData> searchDataList;

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

    public List<SearchBlogData> getSearchDataList() {
        return searchDataList;
    }

    public void setSearchDataList(List<SearchBlogData> searchDataList) {
        this.searchDataList = searchDataList;
    }
}
