package com.hit.assure.Model.Sessionimage;

import com.google.gson.annotations.SerializedName;

public class SessionimageData  {

    @SerializedName("session_list")
    private String session_list;

    @SerializedName("session_image")
    private String session_image;

    @SerializedName("date")
    private String date;

    public String getSession_list() {
        return session_list;
    }

    public void setSession_list(String session_list) {
        this.session_list = session_list;
    }

    public String getSession_image() {
        return session_image;
    }

    public void setSession_image(String session_image) {
        this.session_image = session_image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
