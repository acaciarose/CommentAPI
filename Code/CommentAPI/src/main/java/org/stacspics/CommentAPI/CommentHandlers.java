package org.stacspics.CommentAPI;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import java.lang.Integer;

import com.google.gson.*;


import java.util.ArrayList;

//REST handlers for getting/making/altering comments
@Path("/comments")
public class CommentHandlers {
SystemStorage ss = new SystemStorage().readFromStorage("storage.json");
Gson gson = new Gson();

@GET
@Path("{commentID}/replies")
@Produces("text/plain")
public String getRepliesToComment(@PathParam("commentID") String commentID) {

Comment c = ss.getCommentByID(Integer.parseInt(commentID));
ArrayList<Comment> replies = c.getAllReplies();

return gson.toJson(replies);
}

@GET
@Path("{commentID}")
@Produces("text/plain")
public String getCommentFromID(@PathParam("commentID") String commentID) {

    Comment c = ss.getCommentByID(Integer.parseInt(commentID));

    //Return JSON string of comment
    return c.turnToJsonString();

}



@POST
@Consumes("text/plain")
@Produces("text/plain")
@Path("/{commentID}/remove")
//This has to check if the user (username given in data) has admin privileges first
public Response deleteComment(@PathParam("commentID") String commentID, String data) {
    Comment comment = ss.getCommentByID(Integer.parseInt(commentID));
    User deleter = ss.getUserFromUserName(data);

    if (comment.remove(deleter, ss)) {
        //Admin privileges present
        return Response.status(Response.Status.CREATED).entity("Successfully deleted.").build();
    }

    else {
        //No admin, reject request
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



}

@POST
@Produces("text/plain")
@Consumes("text/plain")
@Path("/{commentID}/upvote")
public Response upvoteComment(@PathParam("commentID") String commentID, String data) {
    Comment comment = ss.getCommentByID(Integer.parseInt(commentID));

    if (comment.upvote(ss)) {
        return Response.status(Response.Status.CREATED).entity("Successfully upvoted.").build();
    }
    return Response.status(Response.Status.BAD_REQUEST).build();

}


@POST
@Produces("text/plain")
@Consumes("text/plain")
@Path("/{commentID}/downvote")
public Response downvoteComment(@PathParam("commentID") String commentID, String data) {

    Comment comment = ss.getCommentByID(Integer.parseInt(commentID));
    if (comment.downvote(ss)) {
        return Response.status(Response.Status.CREATED).entity("Successfully downvoted.").build();
    }
    return Response.status(Response.Status.BAD_REQUEST).build();

    
    
}



}