package com.hit.assure.Model.BlogDetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlogDetailsResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("data")
    private BlogDetailsData blogDetailsData;

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

    public BlogDetailsData getBlogDetailsData() {
        return blogDetailsData;
    }

    public void setBlogDetailsData(BlogDetailsData blogDetailsData) {
        this.blogDetailsData = blogDetailsData;
    }
}
