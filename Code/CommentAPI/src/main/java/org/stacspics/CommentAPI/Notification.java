package org.stacspics.CommentAPI;

import com.google.gson.*;

//Notification object
//Each user has a list of these

public class Notification {
    private String notificationText;
    private boolean read;
    private Comment notificationComment;

    public Notification(String text, Comment comment) {
        notificationText = text;
        read = false;
        notificationComment = comment;

    }

    public Comment getComment() {
        return notificationComment;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public boolean checkIfRead() {
        return read;
    }

    public void markAsRead() {
        read = true;
    }

    public String turnToJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);


    }

}