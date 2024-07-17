package com.hit.assure.Model.SocialMedia;

import com.google.gson.annotations.SerializedName;

public class SocialMediaData {

    @SerializedName("id")
    private String id;

    @SerializedName("social_media")
    private String social_media;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocial_media() {
        return social_media;
    }

    public void setSocial_media(String social_media) {
        this.social_media = social_media;
    }
}
