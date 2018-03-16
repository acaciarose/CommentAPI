package org.stacspics.CommentAPI;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.google.gson.*;

//Comment object, containing text and information about comment


public class Comment {
    private String commentText;
    private String userName;
    private int commentID;
    private int numberOfUpvotes;
    private Timestamp timestamp;
    private ArrayList < Comment > children;

    public Comment(String text, String username, boolean topLevel, IDGenerator IDgen) {
        commentText = text;
        userName = username;
        numberOfUpvotes = 0;
        commentID = IDgen.createCommentID();
        timestamp = new Timestamp(System.currentTimeMillis());
        children = new ArrayList < Comment > ();
    }


    public void addReply(Comment reply) {
        children.add(reply);
    }

    public ArrayList < Comment > getAllReplies() {
        return children;
    }

    public boolean upvote(SystemStorage ss) {
        numberOfUpvotes++;

        try {
            //Update comment in storage
            ss.addCommentToSystem(this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //Number of upvotes can be negative!
    public boolean downvote(SystemStorage ss) {
        numberOfUpvotes--;

        try {
            //Update comment in storage
            ss.addCommentToSystem(this);
            return true;
        } catch (IOException e) {
            return false;
        }
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

    public boolean remove(User user, SystemStorage ss) {
        if (user.isAdmin()) {
            commentText = "This comment has been removed by an administrator.";

            try {
                //Update comment in storage
                ss.addCommentToSystem(this);
                return true;
            } catch (IOException e) {
                return false;
            }


        }

        return false;
    }

    public String turnToJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);


    }

}