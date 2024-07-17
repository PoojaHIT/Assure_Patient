package com.hit.assure.Model.CityList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityListData {

    @SerializedName("city")
  private List<CityData> cityData;

    public List<CityData> getCityData() {
        return cityData;
    }

    public void setCityData(List<CityData> cityData) {
        this.cityData = cityData;
    }
}
