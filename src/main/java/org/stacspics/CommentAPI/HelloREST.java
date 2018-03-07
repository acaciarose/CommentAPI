package org.stacspics.CommentAPI;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

//Placeholder/test GET handler, take from Example slides
@Path("/helloworldTEST")
public class HelloREST {
  @GET	
  @Produces("text/plain")
  public String getMessage() {		
    return "Hello World!";		
  }
}
