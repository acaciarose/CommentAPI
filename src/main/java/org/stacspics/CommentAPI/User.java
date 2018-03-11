package org.stacspics.CommentAPI;

import java.util.ArrayList;

//User object - keep track of user's comments and notifications

public class User {

    private String username;
    public ArrayList<Notification> notifications;
    private ArrayList<Comment> comments;
    private boolean isAdmin;


    public User(String name, boolean hasAdmin) {
        username = name;
        notifications = new ArrayList<Notification>();
        comments = new ArrayList<Comment>();
        isAdmin = hasAdmin;
    }

    public String getName() {
        return username;
    }



    public boolean isAdmin() {
        return isAdmin;
    }

    //Post a comment on a photograph 
    public void postComment(String commenttext, Photograph photo, SystemStorage ss) {
        Comment comment = new Comment(commenttext, username, true, ss.getGenerator());

        //Add comment to our comments list
        comments.add(comment);

        //Add comment on other user's photo
        photo.addComment(comment);


        // //Notify other user (refactor into separate method later)
         User otherUser = ss.getUserFromUserName(photo.getUser());
         String notificationText = "User" + username + "commented on your photo : " + "commenttext"; 
         Notification n = new Notification(notificationText, comment);
         otherUser = notify(otherUser, n);

         ss.addUserToSystem(otherUser);

    }

    //Post a comment/reply on a comment
    public void postComment(String replytext, Comment comment, SystemStorage ss) {
        Comment reply = new Comment(replytext, username, false, ss.getGenerator());

        //Add reply to our comments list
        comments.add(reply);

        //Add reply on other user's comment
        comment.addReply(reply);

        
        // //Notify other user (refactor into separate method later)
        User otherUser = ss.getUserFromUserName(comment.getCommenter());
        Notification n = new Notification("User" + username + "replied to your comment : "+ replytext, reply);
        otherUser = notify(otherUser, n);

        ss.addUserToSystem(otherUser);
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Notification> getNotifications() {
        //Here we assume that "get" means the user reads the notifications
        //So we can mark them as read
        //Only return notifications that are marked as unread

        ArrayList<Notification> unreadNotifications = new ArrayList<Notification>();

        for (Notification n : notifications) {
            if (!n.checkIfRead()) {
                unreadNotifications.add(n);
                n.markAsRead();
            }
            else{
            }
        }

        return unreadNotifications;
    }

    //Get notifications without marking them as read
    private ArrayList<Notification> getNotificationsSilently() {
        return notifications;
    }


    public User notify(User userToBeNotified, Notification n) {
        
        userToBeNotified.notifications.add(n);
        

        return userToBeNotified;


    }

    public String turnToJsonString() {
        return "";    
        
    }

  


}