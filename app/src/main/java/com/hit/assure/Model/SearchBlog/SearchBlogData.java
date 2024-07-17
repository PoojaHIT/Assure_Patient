package com.hit.assure.Model.SearchBlog;

import com.google.gson.annotations.SerializedName;

public class SearchBlogData {

    @SerializedName("article_id")
    private String article_id;

    @SerializedName("article_title")
    private String article_title;

    @SerializedName("article_image")
    private String article_image;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_image() {
        return article_image;
    }

    public void setArticle_image(String article_image) {
        this.article_image = article_image;
    }
}
