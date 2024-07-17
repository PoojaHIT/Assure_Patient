package com.hit.assure.Model.Testimonials;

import com.google.gson.annotations.SerializedName;

public class TestimonialsDatalist {

    @SerializedName("vid")
    private String vid;

    @SerializedName("video_id")
    private String video_id;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
