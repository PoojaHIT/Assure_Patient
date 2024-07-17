package com.hit.assure.Model.VirtualConsultant;

import com.google.gson.annotations.SerializedName;

public class DoctorConsultationData {

    @SerializedName("doctor_user_id")
    private String doctor_user_id;

    @SerializedName("consultation_name")
    private String consultation_name;

    @SerializedName("charges")
    private String charges;

    @SerializedName("duration")
    private String duration;

    public String getDoctor_user_id() {
        return doctor_user_id;
    }

    public void setDoctor_user_id(String doctor_user_id) {
        this.doctor_user_id = doctor_user_id;
    }

    public String getConsultation_name() {
        return consultation_name;
    }

    public void setConsultation_name(String consultation_name) {
        this.consultation_name = consultation_name;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
