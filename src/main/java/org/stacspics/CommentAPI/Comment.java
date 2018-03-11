package org.stacspics.CommentAPI;

import java.sql.Timestamp;
import java.util.ArrayList;
import com.google.gson.*;

//Comment object, containing text and information about comment


public class Comment {
    private String commentText;
    private String userName;
    //CommentID may not be necessary: adding just in case, will review later
    private int commentID;
    private int numberOfUpvotes;
    private Timestamp timestamp;
    private ArrayList<Comment> children;
    private boolean isTopLevel;

    public Comment(String text, String username, boolean topLevel, IDGenerator IDgen) {
        commentText = text;
        userName = username;
        numberOfUpvotes = 0;
        commentID = IDgen.createCommentID();
        timestamp = new Timestamp(System.currentTimeMillis());
        children = new ArrayList<Comment>();
        isTopLevel = topLevel;
    }


    public void addReply(Comment reply) {
        children.add(reply);
    }

    public ArrayList<Comment> getAllReplies() {
        return children;
    }

    public void upvote() {
        numberOfUpvotes++;
    }

    //Number of upvotes can be negative!
    public void downvote() {
        numberOfUpvotes--;
    }

    public int getUpvotes() {
        return numberOfUpvotes;
    }

    public int getID() {
        return commentID;
    }

    public String getText() {
        return commentText;
    }

    public String getCommenter() {
        return userName;
    }

    public boolean remove(User user) {
        if (user.isAdmin()) {
            commentText = "This comment has been removed by an administrator.";
            return true;
        }

        return false;
    }

    public String turnToJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
        
        
    }

}