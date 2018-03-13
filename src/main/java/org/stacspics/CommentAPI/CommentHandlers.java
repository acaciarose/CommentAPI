package org.stacspics.CommentAPI;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import java.util.ArrayList;

//REST handlers for getting/making/altering comments
@Path("/comments")
public class CommentHandlers {
SystemStorage ss = new SystemStorage().readFromStorage("storage.json");


@GET
@Path("/{commentID}")
@Produces("text/plain")
public String getCommentFromID(@PathParam("CommentID") int commentID) {
    Comment c = ss.getCommentByID(commentID);
    System.out.println(c.getCommenter());

    //Return JSON string of comment
    return c.turnToJsonString();

}

@GET
@Path("/comments/{commentID}/replies")
@Produces("text/plain")
public String getRepliesToComment(@PathParam("CommentID") int commentID) {
return "";
}

}