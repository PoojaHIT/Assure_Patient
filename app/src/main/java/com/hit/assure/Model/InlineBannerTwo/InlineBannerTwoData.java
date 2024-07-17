package com.hit.assure.Model.InlineBannerTwo;

import com.google.gson.annotations.SerializedName;

public class InlineBannerTwoData {

    @SerializedName("id")
    private String id;

    @SerializedName("banner_city_id")
    private String banner_city_id;

    @SerializedName("banner_type")
    private String banner_type;

    @SerializedName("banner_image")
    private String banner_image;

    @SerializedName("banner_link_type")
    private String banner_link_type;

    @SerializedName("banner_link")
    private String banner_link;

    @SerializedName("banner_category_id")
    private String banner_category_id;

    @SerializedName("banner_sub_category_id")
    private String banner_sub_category_id;

    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner_city_id() {
        return banner_city_id;
    }

    public void setBanner_city_id(String banner_city_id) {
        this.banner_city_id = banner_city_id;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getBanner_link_type() {
        return banner_link_type;
    }

    public void setBanner_link_type(String banner_link_type) {
        this.banner_link_type = banner_link_type;
    }

    public String getBanner_link() {
        return banner_link;
    }

    public void setBanner_link(String banner_link) {
        this.banner_link = banner_link;
    }

    public String getBanner_category_id() {
        return banner_category_id;
    }

    public void setBanner_category_id(String banner_category_id) {
        this.banner_category_id = banner_category_id;
    }

    public String getBanner_sub_category_id() {
        return banner_sub_category_id;
    }

    public void setBanner_sub_category_id(String banner_sub_category_id) {
        this.banner_sub_category_id = banner_sub_category_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
