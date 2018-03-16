package org.stacspics.CommentAPI;

import java.util.ArrayList;
import com.google.gson.*;
import java.io.IOException;

//User object - keep track of user's comments and notifications

public class User {

    private String username;
    private ArrayList<Notification> notifications;
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
    public boolean postComment(String commenttext, Photograph photo, SystemStorage ss) {
        Comment comment = new Comment(commenttext, username, true, ss.getGenerator());



        //Add comment to our comments list
        comments.add(comment);


        //Add comment on other user's photo
        photo.addComment(comment);




        //Notify other user 
        User otherUser = ss.getUserFromUserName(photo.getUser());
        handleNotification(otherUser, username, commenttext, comment);



         try {
        //Write subject comment back to storage
        ss.addCommentToSystem(comment);



        //Update photo in storage
        ss.addPhotoToSystem(photo);



        //Add this user back to storage, with his/her new comments list
        ss.addUserToSystem(this);



         //Finally, update other user in storage
         ss.addUserToSystem(otherUser);



         return true;
         }

         catch (IOException e) {
             return false;
         }

    }

    //Post a comment/reply on a comment
    public boolean postComment(String replytext, Comment comment, SystemStorage ss) {
        Comment reply = new Comment(replytext, username, false, ss.getGenerator());

        //Add reply to our comments list
        comments.add(reply);

        //Add reply on other user's comment
        comment.addReply(reply);

   
        //Notify other user
        User otherUser = ss.getUserFromUserName(comment.getCommenter());
        handleNotification(otherUser, username, replytext, reply);

        try {
        //Write subject comment back to storage
        ss.addCommentToSystem(comment);

        //Add new reply comment to storage
        ss.addCommentToSystem(reply);

        //Add this user back to storage, with his/her new comments list
        ss.addUserToSystem(this);

        //Finally, write other user to system
        ss.addUserToSystem(otherUser);

        return true;
        }

        catch (IOException e) {
            return false;
        }
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Notification> getNotifications(SystemStorage ss) {
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

        try {
            ss.addUserToSystem(this);
            return unreadNotifications;
        }

        catch (IOException e) {
            return null;
        }

        
    }

    private void addToNotifications(Notification n) {
        notifications.add(n);
    }


    private void handleNotification(User otherUser, String userName, String commenttext, Comment comment) {
        
        String notificationText = "User" + userName + "commented on something you posted : " + commenttext; 
        Notification n = new Notification(notificationText, comment);
        otherUser.addToNotifications(n);
    }

    public String turnToJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);      
        
    }

  


}