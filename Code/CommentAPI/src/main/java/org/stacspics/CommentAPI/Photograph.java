package org.stacspics.CommentAPI;

import org.stacspics.CommentAPI.Comment;
import java.util.ArrayList;
import com.google.gson.*;

//Represents one photograph object
//Doesn't currently contain any actual images but could easily be extended
public class Photograph {
    //This field might be changed to be an actual user object in the future
    private String ownerUserName;
    private ArrayList < Comment > comments;
    private int photographID;

    public Photograph(String user, IDGenerator IDgen) {
        ownerUserName = user;
        comments = new ArrayList < Comment > ();
        photographID = IDgen.createPhotographID();
    }

    public String getUser() {
        return ownerUserName;
    }

    public int getID() {
        return photographID;
    }

    public ArrayList < Comment > getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String turnToJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);


    }

}