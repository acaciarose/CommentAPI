package org.stacspics.CommentAPI;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpHandler;


import java.io.IOException;
import java.net.URI;

import java.net.URLConnection;
import java.net.URL;


//Placeholder main class taken from examples on studres, minor alterations
public class Server {
    // Base URI the Grizzly HTTP server will listen on
    public  final String BASE_URI = "http://localhost:8080/myapp/";
    public HttpServer hserver;

    public HttpServer startServer() {
        System.setProperty("https.protocols", "TLSv1.1");


        // create a resource config that scans for JAX-RS resources and providers
        // in org.stacscal.rest package
        final ResourceConfig rc = new ResourceConfig().packages("org.stacspics.CommentAPI");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public void runServer(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
            

        System.in.read();



    }
}

