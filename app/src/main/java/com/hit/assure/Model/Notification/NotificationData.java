package com.hit.assure.Model.Notification;

import com.google.gson.annotations.SerializedName;

public class NotificationData {

    @SerializedName("notification_id")
    private String notification_id;

    @SerializedName("type")
    private String type;

    @SerializedName("description")
    private String description;

    @SerializedName("is_read")
    private String is_read;

    @SerializedName("created_at")
    private String created_at;

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
