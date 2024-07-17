package com.hit.assure.Model.Testimonials;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeforeAfterResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("testimonials_media")
    private List<BeforeAfterDatalist> beforeAfterDatalists;

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public List<BeforeAfterDatalist> getBeforeAfterDatalists() {
        return beforeAfterDatalists;
    }

    public void setBeforeAfterDatalists(List<BeforeAfterDatalist> beforeAfterDatalists) {
        this.beforeAfterDatalists = beforeAfterDatalists;
    }
}
