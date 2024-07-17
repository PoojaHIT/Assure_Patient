package com.hit.assure.Model.Blog;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlogResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("ResponseMsg")
    private int ResponseMsg;

    @SerializedName("Result")
    private String Result;

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public int getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(int responseMsg) {
        ResponseMsg = responseMsg;
    }

    @SerializedName("data")
    private List<BlogData> blogData;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BlogData> getBlogData() {
        return blogData;
    }

    public void setBlogData(List<BlogData> blogData) {
        this.blogData = blogData;
    }
}
