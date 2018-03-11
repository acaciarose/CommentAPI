package org.stacspics.CommentAPI;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import java.util.ArrayList;

//Placeholder/test GET handler, take from Example slides
@Path("/helloworld")
public class HelloREST {
  @GET
  @Path("{lang}")
  @Produces("text/plain")
  public String getMessage(@PathParam("lang") String lang) {
  switch (lang.toLowerCase()) {
  case "en": return "Hello World!";
  case "de": return "Hallo Welt!";
  default: return "language "+lang+" not supported.";
  }
  }
  

}
