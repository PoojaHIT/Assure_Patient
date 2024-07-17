package com.hit.assure.Model.DoctorSearch;

import com.google.gson.annotations.SerializedName;
import com.hit.assure.Model.VirtualConsultant.StatusData;
import com.hit.assure.Model.VirtualConsultant.TimeData;

import java.util.List;

public class OpeningDayDataList {

    @SerializedName("day")
    private String day;

    @SerializedName("time")
    private List<TimeDataList> timeData;

    @SerializedName("status")
    private List<StatusDataList> statusData ;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<TimeDataList> getTimeData() {
        return timeData;
    }

    public void setTimeData(List<TimeDataList> timeData) {
        this.timeData = timeData;
    }

    public List<StatusDataList> getStatusData() {
        return statusData;
    }

    public void setStatusData(List<StatusDataList> statusData) {
        this.statusData = statusData;
    }
}
