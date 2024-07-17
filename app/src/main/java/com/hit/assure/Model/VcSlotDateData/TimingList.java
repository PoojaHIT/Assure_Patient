package com.hit.assure.Model.VcSlotDateData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimingList {

    @SerializedName("Morning")
    private List<Timelist> morningTimes;

    @SerializedName("Afternoon")
    private List<Timelist> afternoonTimes;

    @SerializedName("Evening")
    private List<Timelist> eveningTimes;

    @SerializedName("Night")
    private List<Timelist> nightTimes;

    public List<Timelist> getMorningTimes() {
        return morningTimes;
    }

    public void setMorningTimes(List<Timelist> morningTimes) {
        this.morningTimes = morningTimes;
    }

    public List<Timelist> getAfternoonTimes() {
        return afternoonTimes;
    }

    public void setAfternoonTimes(List<Timelist> afternoonTimes) {
        this.afternoonTimes = afternoonTimes;
    }

    public List<Timelist> getEveningTimes() {
        return eveningTimes;
    }

    public void setEveningTimes(List<Timelist> eveningTimes) {
        this.eveningTimes = eveningTimes;
    }

    public List<Timelist> getNightTimes() {
        return nightTimes;
    }

    public void setNightTimes(List<Timelist> nightTimes) {
        this.nightTimes = nightTimes;
    }
}
