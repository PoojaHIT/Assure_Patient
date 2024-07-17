package com.hit.assure.Model.Testimonials;

import com.google.gson.annotations.SerializedName;

public class TestimonialsReviewDatalist {

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("review")
    private String review;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
