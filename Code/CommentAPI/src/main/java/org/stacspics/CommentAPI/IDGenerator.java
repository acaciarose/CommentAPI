package org.stacspics.CommentAPI;

//Comemnt ID Generator
//Gives out unique comment IDs

public class IDGenerator {
    private int commentID;
    private int photographID;

    public IDGenerator() {
        commentID = 0;
        photographID = 0;
    }

    public int createCommentID() {
        //Check for overflow/maximum comments reached (unlikely but possible)
        if (commentID == Integer.MAX_VALUE) {
            return -1;
        }
        return commentID++;
    }

    public int createPhotographID() {
        //Check for overflow/maximum comments reached (unlikely but possible)
        if (photographID == Integer.MAX_VALUE) {
            return -1;
        }
        return photographID++;
    }


}