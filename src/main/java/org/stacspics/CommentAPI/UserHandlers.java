package org.stacspics.CommentAPI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import com.google.gson.*;
import java.io.InputStream;


import java.util.ArrayList;
@Path("/users")
//REST functions for handling user-related requests 
public class UserHandlers {
Gson gson = new Gson();
SystemStorage ss = new SystemStorage().readFromStorage("storage.json");


@GET
@Path("/{username}/comments")
@Produces("text/plain")
public String getUsersComments(@PathParam("username") String username) {
    User user = ss.getUserFromUserName(username);
    ArrayList<Comment> allUserComments = user.getComments();
    return gson.toJson(allUserComments);
}

@POST
@Consumes("text/plain")
//This posts a comment to a Photo, specified by photoID
@Path("/{commentername}/comments/photos/{photoid}")
public Response postReplyOnPhoto(@PathParam("commentername") String replier, @PathParam("photoID") int photoID, InputStream is) {
    return Response.status(Response.Status.BAD_REQUEST).build();

}

@POST
//This posts a reply to a comment, specified by CommentID
@Consumes("text/plain")
@Path("/{repliername}/comments/{commentID}/replies")
public Response postReplyOnComment(@PathParam("repliername") String replier, @PathParam("CommentID") int commentID, InputStream is) {
    return Response.status(Response.Status.BAD_REQUEST).build();

}

}
