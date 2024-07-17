package com.hit.assure.Model.SubCategory;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<SubCategoryData> subCategoryData;

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

    public List<SubCategoryData> getSubCategoryData() {
        return subCategoryData;
    }

    public void setSubCategoryData(List<SubCategoryData> subCategoryData) {
        this.subCategoryData = subCategoryData;
    }
}
