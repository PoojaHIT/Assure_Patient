package com.hit.assure.Model.ReviewList;

import com.facebook.rebound.SteppingLooper;
import com.google.gson.annotations.SerializedName;

public class ReviewListData {

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("rating")
    private String rating;

    @SerializedName("review")
    private String review;

    @SerializedName("review_date")
    private String review_date;

    @SerializedName("like_count")
    private String like_count;

    @SerializedName("profile_pic")
    private String profile_pic;

    @SerializedName("like_yes_no")
    private String like_yes_no;

    @SerializedName("comment_count")
    private String comment_count;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("title")
    private String title;

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getLike_yes_no() {
        return like_yes_no;
    }

    public void setLike_yes_no(String like_yes_no) {
        this.like_yes_no = like_yes_no;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
