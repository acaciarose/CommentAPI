package org.stacspics.CommentAPI;

import java.sql.Timestamp;
import java.util.ArrayList;

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

    public Comment(String text, String username, boolean topLevel, CommentIDGenerator cigen) {
        commentText = text;
        userName = username;
        numberOfUpvotes = 0;
        commentID = cigen.createCommentID();
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

    public String getCommenter() {
        return userName;
    }




}