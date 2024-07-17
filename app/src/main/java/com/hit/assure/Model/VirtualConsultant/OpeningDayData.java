package com.hit.assure.Model.VirtualConsultant;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpeningDayData {

    @SerializedName("day")
    private String day;

    @SerializedName("time")
    private List<TimeData> timeData;

    @SerializedName("status")
    private List<StatusData> statusData ;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<TimeData> getTimeData() {
        return timeData;
    }

    public void setTimeData(List<TimeData> timeData) {
        this.timeData = timeData;
    }

    public List<StatusData> getStatusData() {
        return statusData;
    }

    public void setStatusData(List<StatusData> statusData) {
        this.statusData = statusData;
    }
}
