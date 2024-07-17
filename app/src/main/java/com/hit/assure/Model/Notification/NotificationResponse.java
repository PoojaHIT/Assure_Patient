package com.hit.assure.Model.Notification;

import com.google.gson.annotations.SerializedName;
import com.hit.assure.Model.OtpVerification.UserData;

import java.util.List;

public class NotificationResponse {

    @SerializedName("ResponseCode")
    private int ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("total_page")
    private int total_page;

    @SerializedName("data")
    private List<NotificationData> notificationData;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
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

    public List<NotificationData> getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(List<NotificationData> notificationData) {
        this.notificationData = notificationData;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
}
