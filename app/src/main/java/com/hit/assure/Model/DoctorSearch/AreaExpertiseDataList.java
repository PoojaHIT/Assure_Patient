package com.hit.assure.Model.DoctorSearch;

import com.google.gson.annotations.SerializedName;

public class AreaExpertiseDataList {

    @SerializedName("id")
    private String id;

    @SerializedName("area_expertise")
    private String area_expertise;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea_expertise() {
        return area_expertise;
    }

    public void setArea_expertise(String area_expertise) {
        this.area_expertise = area_expertise;
    }
}
