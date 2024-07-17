package com.hit.assure.Model.BlogDetails;

import com.google.gson.annotations.SerializedName;

public class BlogDetailsData {

    @SerializedName("article_id")
    private String article_id;

    @SerializedName("category_id")
    private String category_id;

    @SerializedName("article_title")
    private String article_title;

    @SerializedName("article_description")
    private String article_description;

    @SerializedName("article_image")
    private String article_image;

    @SerializedName("article_date")
    private String article_date;

    @SerializedName("author")
    private String author;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_description() {
        return article_description;
    }

    public void setArticle_description(String article_description) {
        this.article_description = article_description;
    }


    public String getArticle_image() {
        return article_image;
    }

    public void setArticle_image(String article_image) {
        this.article_image = article_image;
    }

    public String getArticle_date() {
        return article_date;
    }

    public void setArticle_date(String article_date) {
        this.article_date = article_date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
