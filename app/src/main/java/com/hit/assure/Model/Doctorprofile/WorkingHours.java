package com.hit.assure.Model.Doctorprofile;

import com.google.gson.annotations.SerializedName;

public class WorkingHours {

    @SerializedName("start_time")
    private String start_time;

    @SerializedName("end_time")
    private String end_time;

    @SerializedName("timeSlot1")
    private String timeSlot1;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTimeSlot1() {
        return timeSlot1;
    }

    public void setTimeSlot1(String timeSlot1) {
        this.timeSlot1 = timeSlot1;
    }
}
