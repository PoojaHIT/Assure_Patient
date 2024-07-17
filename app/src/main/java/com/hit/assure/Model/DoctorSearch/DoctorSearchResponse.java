package com.hit.assure.Model.DoctorSearch;

import com.google.gson.annotations.SerializedName;
import com.hit.assure.Model.Search.SearchData;
import com.hit.assure.Model.VirtualConsultant.VirtualConsultantData;

import java.util.List;

public class DoctorSearchResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<DoctorListData> searchData;

    public List<DoctorListData> getSearchData() {
        return searchData;
    }

    public void setSearchData(List<DoctorListData> searchData) {
        this.searchData = searchData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
