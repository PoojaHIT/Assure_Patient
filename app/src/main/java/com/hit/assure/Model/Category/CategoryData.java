package com.hit.assure.Model.Category;

import com.google.gson.annotations.SerializedName;

public class CategoryData {

    @SerializedName("id")
    private String id;

    @SerializedName("cat_name")
    private String cat_name;

    @SerializedName("parent_cat_id")
    private String parent_cat_id;

    @SerializedName("photo")
    private String photo;

    @SerializedName("pc_status")
    private String pc_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getParent_cat_id() {
        return parent_cat_id;
    }

    public void setParent_cat_id(String parent_cat_id) {
        this.parent_cat_id = parent_cat_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPc_status() {
        return pc_status;
    }

    public void setPc_status(String pc_status) {
        this.pc_status = pc_status;
    }


}
