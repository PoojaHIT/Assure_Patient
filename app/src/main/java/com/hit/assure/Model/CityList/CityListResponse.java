package com.hit.assure.Model.CityList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityListResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private CityListData cityListData;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CityListData getCityListData() {
        return cityListData;
    }

    public void setCityListData(CityListData cityListData) {
        this.cityListData = cityListData;
    }
}
