package org.stacspics.CommentAPI;

//Notification object
//Each user has a list of these

public class Notification {
    private String notificationText;
    private boolean read;
    private Comment commentText;

    public Notification(String text, Comment comment) {
        notificationText = text;
        read = false;
        commentText = comment;

    }

    public Comment getComment() {
        return commentText;
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

}