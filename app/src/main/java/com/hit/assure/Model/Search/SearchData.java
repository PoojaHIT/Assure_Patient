package com.hit.assure.Model.Search;

import com.google.gson.annotations.SerializedName;

public class SearchData {

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("pname")
    private String pname;

    @SerializedName("pbname")
    private String pbname;

    @SerializedName("cid")
    private String cid;

    @SerializedName("sid")
    private String sid;

    @SerializedName("pspec")
    private String pspec;

    @SerializedName("psdesc")
    private String psdesc;

    @SerializedName("pgms")
    private String pgms;

    @SerializedName("pprice")
    private String pprice;

    @SerializedName("status")
    private String status;

    @SerializedName("stock")
    private String stock;

    @SerializedName("stock_count")
    private String stock_count;

    @SerializedName("pimg")
    private String pimg;

    @SerializedName("discount")
    private String discount;

    @SerializedName("popular")
    private String popular;

    @SerializedName("date")
    private String date;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPbname() {
        return pbname;
    }

    public void setPbname(String pbname) {
        this.pbname = pbname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPspec() {
        return pspec;
    }

    public void setPspec(String pspec) {
        this.pspec = pspec;
    }

    public String getPsdesc() {
        return psdesc;
    }

    public void setPsdesc(String psdesc) {
        this.psdesc = psdesc;
    }

    public String getPgms() {
        return pgms;
    }

    public void setPgms(String pgms) {
        this.pgms = pgms;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStock_count() {
        return stock_count;
    }

    public void setStock_count(String stock_count) {
        this.stock_count = stock_count;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
