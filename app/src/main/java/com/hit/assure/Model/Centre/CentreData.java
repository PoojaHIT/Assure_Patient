package com.hit.assure.Model.Centre;

import com.google.gson.annotations.SerializedName;

public class CentreData {

    @SerializedName("centre_id")
    private String centre_id;

    @SerializedName("centre_name")
    private String centre_name;

    public String getCentre_id() {
        return centre_id;
    }

    public void setCentre_id(String centre_id) {
        this.centre_id = centre_id;
    }

    public String getCentre_name() {
        return centre_name;
    }

    public void setCentre_name(String centre_name) {
        this.centre_name = centre_name;
    }
}
