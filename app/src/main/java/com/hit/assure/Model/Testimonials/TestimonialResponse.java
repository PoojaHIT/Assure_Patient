package com.hit.assure.Model.Testimonials;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestimonialResponse {

    @SerializedName("ResponseCode")
    private String ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("testimonials")
    private List<TestimonialsDatalist> testimonialsDatalists;

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

    public List<TestimonialsDatalist> getTestimonialsDatalists() {
        return testimonialsDatalists;
    }

    public void setTestimonialsDatalists(List<TestimonialsDatalist> testimonialsDatalists) {
        this.testimonialsDatalists = testimonialsDatalists;
    }
}
