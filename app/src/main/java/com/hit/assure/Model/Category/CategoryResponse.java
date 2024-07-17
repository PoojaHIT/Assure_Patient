package com.hit.assure.Model.Category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

@SerializedName("status")
    private int status;

@SerializedName("message")
    private String message;

@SerializedName("data")
    private List<CategoryData> categoryData;

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

    public List<CategoryData> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryData> categoryData) {
        this.categoryData = categoryData;
    }
}
