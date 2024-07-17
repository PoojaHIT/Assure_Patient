package com.hit.assure.Model.VcSlotDateData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VcDataDate {

    @SerializedName("day")
    private String day;

    @SerializedName("date")
    private String date;

    @SerializedName("timings")
    private List<TimingList> timingLists;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TimingList> getTimingLists() {
        return timingLists;
    }

    public void setTimingLists(List<TimingList> timingLists) {
        this.timingLists = timingLists;
    }
}
