package com.hit.assure.Model.TipOfTheDay;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TipOfTheBanneResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<TipOftheDayData> tipOdtheDayData;

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

    public List<TipOftheDayData> getTipOdtheDayData() {
        return tipOdtheDayData;
    }

    public void setTipOdtheDayData(List<TipOftheDayData> tipOdtheDayData) {
        this.tipOdtheDayData = tipOdtheDayData;
    }
}
