package com.hit.assure.Model.Rating;

import com.google.gson.annotations.SerializedName;

public class RatingListData {

    @SerializedName("id")
    private String  id;

    @SerializedName("username")
    private String username;

    @SerializedName("profile_pic")
    private String profile_pic;

    @SerializedName("rating")
    private String rating;

    @SerializedName("title")
    private String title;

    @SerializedName("review")
    private String review;

    @SerializedName("user_id")
    private String user_id;

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

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
