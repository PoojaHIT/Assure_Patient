package com.hit.assure.Model.Prescriptionlist;

import com.google.gson.annotations.SerializedName;

public class Prescriprionlistdata {

    @SerializedName("medicine_name")
    private String medicine_name;

    @SerializedName("date")
    private String date;

    @SerializedName("dosage")
    private String dosage;

    @SerializedName("taken")
    private String taken;

    @SerializedName("note")
    private String note;

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
