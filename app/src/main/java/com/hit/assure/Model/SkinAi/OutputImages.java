package com.hit.assure.Model.SkinAi;

import com.google.gson.annotations.SerializedName;

public class OutputImages {

    @SerializedName("acne_image")
    private String acne_image;

    @SerializedName("face_wrinkle_image")
    private String face_wrinkle_image;

    @SerializedName("eye_wrinkle_image")
    private String eye_wrinkle_image;

    @SerializedName("crowsfeet_image")
    private String crowsfeet_image;

    @SerializedName("skin_dullness_image")
    private String skin_dullness_image;

    @SerializedName("uneven_skin_image")
    private String uneven_skin_image;

    @SerializedName("dark_spot_image")
    private String dark_spot_image;

    @SerializedName("dark_circle_image")
    private String dark_circle_image;

    @SerializedName("strength_image")
    private String strength_image;

    public String getAcne_image() {
        return acne_image;
    }

    public void setAcne_image(String acne_image) {
        this.acne_image = acne_image;
    }

    public String getFace_wrinkle_image() {
        return face_wrinkle_image;
    }

    public void setFace_wrinkle_image(String face_wrinkle_image) {
        this.face_wrinkle_image = face_wrinkle_image;
    }

    public String getEye_wrinkle_image() {
        return eye_wrinkle_image;
    }

    public void setEye_wrinkle_image(String eye_wrinkle_image) {
        this.eye_wrinkle_image = eye_wrinkle_image;
    }

    public String getCrowsfeet_image() {
        return crowsfeet_image;
    }

    public void setCrowsfeet_image(String crowsfeet_image) {
        this.crowsfeet_image = crowsfeet_image;
    }

    public String getSkin_dullness_image() {
        return skin_dullness_image;
    }

    public void setSkin_dullness_image(String skin_dullness_image) {
        this.skin_dullness_image = skin_dullness_image;
    }

    public String getUneven_skin_image() {
        return uneven_skin_image;
    }

    public void setUneven_skin_image(String uneven_skin_image) {
        this.uneven_skin_image = uneven_skin_image;
    }

    public String getDark_spot_image() {
        return dark_spot_image;
    }

    public void setDark_spot_image(String dark_spot_image) {
        this.dark_spot_image = dark_spot_image;
    }

    public String getDark_circle_image() {
        return dark_circle_image;
    }

    public void setDark_circle_image(String dark_circle_image) {
        this.dark_circle_image = dark_circle_image;
    }

    public String getStrength_image() {
        return strength_image;
    }

    public void setStrength_image(String strength_image) {
        this.strength_image = strength_image;
    }
}
