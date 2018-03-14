package org.stacspics.CommentAPI;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

import com.google.gson.*;


import java.util.ArrayList;

//REST handlers for getting/making/altering comments
@Path("/comments")
public class CommentHandlers {
SystemStorage ss = new SystemStorage().readFromStorage("storage.json");
Gson gson = new Gson();


@GET
@Path("/{commentID}")
@Produces("text/plain")
public String getCommentFromID(@PathParam("CommentID") int commentID) {
    Comment c = ss.getCommentByID(commentID);

    //Return JSON string of comment
    return c.turnToJsonString();

}

@GET
@Path("/{commentID}/replies")
@Produces("text/plain")
public String getRepliesToComment(@PathParam("CommentID") int commentID) {
//Needs to be updated, but returns text for now
Comment c = ss.getCommentByID(commentID);
ArrayList<Comment> replies = c.getAllReplies();

return gson.toJson(replies);
}

@POST
@Path("/{commentID}/remove")
@Consumes("text/plain")
@Produces("text/plain")
public Response deleteComment(@PathParam("commentID") int commentID, String data) {

    Comment comment = ss.getCommentByID(commentID);
    User deleter = ss.getUserFromUserName(data);
    if (comment.remove(deleter, ss)) {
        return Response.ok().entity("Successfully deleted.").build();
    }

    else {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



}



}