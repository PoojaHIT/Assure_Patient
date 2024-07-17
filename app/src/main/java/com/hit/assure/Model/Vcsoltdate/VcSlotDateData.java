package com.hit.assure.Model.Vcsoltdate;

import com.google.gson.annotations.SerializedName;

public class VcSlotDateData {

    @SerializedName("date")
    private String date;

    @SerializedName("date_1")
    private String date_1;

    @SerializedName("count")
    private int count;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_1() {
        return date_1;
    }

    public void setDate_1(String date_1) {
        this.date_1 = date_1;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
