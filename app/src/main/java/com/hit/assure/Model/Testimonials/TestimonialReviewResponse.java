package com.hit.assure.Model.Testimonials;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestimonialReviewResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("reviews")
    private List<TestimonialsReviewDatalist> testimonialsReviewDatalists;

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


    public List<TestimonialsReviewDatalist> getTestimonialsReviewDatalists() {
        return testimonialsReviewDatalists;
    }

    public void setTestimonialsReviewDatalists(List<TestimonialsReviewDatalist> testimonialsReviewDatalists) {
        this.testimonialsReviewDatalists = testimonialsReviewDatalists;
    }
}
