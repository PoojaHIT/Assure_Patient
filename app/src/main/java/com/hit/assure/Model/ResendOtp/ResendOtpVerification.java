package com.hit.assure.Model.ResendOtp;

import androidx.annotation.StringRes;

import com.google.gson.annotations.SerializedName;

public class ResendOtpVerification {

    @SerializedName("otp")
    private String otp;

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


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
}
