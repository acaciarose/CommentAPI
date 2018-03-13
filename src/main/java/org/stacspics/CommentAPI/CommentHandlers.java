package org.stacspics.CommentAPI;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import java.util.ArrayList;

//REST handlers for getting/making/altering comments
@Path("/comments")
public class CommentHandlers {
SystemStorage ss = new SystemStorage();


@GET
@Path("/{commentID}")
@Produces("text/plain")
public String getCommentFromID(@PathParam("CommentID") int commentID) {
//Needs to be updated, but returns text for now
return "Comment";
}

}













