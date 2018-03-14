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
@Path("/photos")
//REST functions for handling user-related requests 
public class PhotoHandlers {
Gson gson = new Gson();
SystemStorage ss = new SystemStorage().readFromStorage("storage.json");


@GET
@Path("{photoID}/comments")
@Produces("text/plain")
public String getPhotosComments(@PathParam("photoID") int photoID) {
    return "";
}





}

