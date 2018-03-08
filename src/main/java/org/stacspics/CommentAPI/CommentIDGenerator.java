package org.stacspics.CommentAPI;

//Comemnt ID Generator
//Gives out unique comment IDs
//May not be needed but implemented for now

public class CommentIDGenerator {
    private int commentID;

    public CommentIDGenerator() {
        commentID = 0;
    }

    public int createCommentID() {
        //Check for overflow/maximum comments reached (unlikely but possible)
        if (commentID == Integer.MAX_VALUE) {
            return -1;
        }
        return commentID++;
    }


}

   