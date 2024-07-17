package com.hit.assure.Model.SkinAi;

import com.google.gson.annotations.SerializedName;

public class OutputData {

    @SerializedName("oxygen_score")
    private int oxygen_score;

    @SerializedName("dark_circle_percentage")
    private int dark_circle_percentage;

    @SerializedName("dark_spot_percentage")
    private int dark_spot_percentage;

    @SerializedName("acne_percentage")
    private int acne_percentage;

    @SerializedName("uneven_skin_percentage")
    private int uneven_skin_percentage;

    @SerializedName("skin_dullness_percentage")
    private int skin_dullness_percentage;

    @SerializedName("face_wrinkle_percentage")
    private int face_wrinkle_percentage;

    @SerializedName("crowsfeet_percentage")
    private int crowsfeet_percentage;

    @SerializedName("eye_wrinkle_percentage")
    private int eye_wrinkle_percentage;

    @SerializedName("face_firmness_percentage")
    private int face_firmness_percentage;

    @SerializedName("Smoothness")
    private int Smoothness;

    @SerializedName("hydration_score")
    private int hydration_score;

    @SerializedName("skin_health_score")
    private int skin_health_score;

    public int getOxygen_score() {
        return oxygen_score;
    }

    public void setOxygen_score(int oxygen_score) {
        this.oxygen_score = oxygen_score;
    }

    public int getDark_circle_percentage() {
        return dark_circle_percentage;
    }

    public void setDark_circle_percentage(int dark_circle_percentage) {
        this.dark_circle_percentage = dark_circle_percentage;
    }

    public int getDark_spot_percentage() {
        return dark_spot_percentage;
    }

    public void setDark_spot_percentage(int dark_spot_percentage) {
        this.dark_spot_percentage = dark_spot_percentage;
    }

    public int getAcne_percentage() {
        return acne_percentage;
    }

    public void setAcne_percentage(int acne_percentage) {
        this.acne_percentage = acne_percentage;
    }

    public int getUneven_skin_percentage() {
        return uneven_skin_percentage;
    }

    public void setUneven_skin_percentage(int uneven_skin_percentage) {
        this.uneven_skin_percentage = uneven_skin_percentage;
    }

    public int getSkin_dullness_percentage() {
        return skin_dullness_percentage;
    }

    public void setSkin_dullness_percentage(int skin_dullness_percentage) {
        this.skin_dullness_percentage = skin_dullness_percentage;
    }

    public int getFace_wrinkle_percentage() {
        return face_wrinkle_percentage;
    }

    public void setFace_wrinkle_percentage(int face_wrinkle_percentage) {
        this.face_wrinkle_percentage = face_wrinkle_percentage;
    }

    public int getCrowsfeet_percentage() {
        return crowsfeet_percentage;
    }

    public void setCrowsfeet_percentage(int crowsfeet_percentage) {
        this.crowsfeet_percentage = crowsfeet_percentage;
    }

    public int getEye_wrinkle_percentage() {
        return eye_wrinkle_percentage;
    }

    public void setEye_wrinkle_percentage(int eye_wrinkle_percentage) {
        this.eye_wrinkle_percentage = eye_wrinkle_percentage;
    }

    public int getFace_firmness_percentage() {
        return face_firmness_percentage;
    }

    public void setFace_firmness_percentage(int face_firmness_percentage) {
        this.face_firmness_percentage = face_firmness_percentage;
    }

    public int getSmoothness() {
        return Smoothness;
    }

    public void setSmoothness(int smoothness) {
        Smoothness = smoothness;
    }

    public int getHydration_score() {
        return hydration_score;
    }

    public void setHydration_score(int hydration_score) {
        this.hydration_score = hydration_score;
    }

    public int getSkin_health_score() {
        return skin_health_score;
    }

    public void setSkin_health_score(int skin_health_score) {
        this.skin_health_score = skin_health_score;
    }
}
