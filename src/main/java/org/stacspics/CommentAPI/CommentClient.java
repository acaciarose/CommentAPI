package org.stacspics.CommentAPI;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;


import java.net.HttpURLConnection;
import java.net.URLConnection;


import java.io.IOException;
import java.net.URI;

import java.lang.StringBuffer;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;

//Client class. Designed to take requests from user (command line), relay them to the server, and display the response (if any)
public class CommentClient {

    //GET request with given path
    public String sendGetRequestAndReturnServerResponse(String path) throws IOException {
 
                URL url = new URL("http", "localhost", 8080, "/myapp" + path);
     
                final String protocol = url.getProtocol();
                if (protocol.equalsIgnoreCase("http")) {
                final URLConnection urlconn = url.openConnection();
                if (urlconn instanceof HttpURLConnection) {
                final HttpURLConnection conn = (HttpURLConnection) urlconn;
                conn.connect();

                
                System.err.println(conn.getResponseCode() + ": " + conn.getResponseMessage());


        
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    String response = "";
            
                    //The last line of the HTTP response is the body/text we want
                    while ((inputLine = in.readLine()) != null) {
                        response = inputLine;
                    }

                in.close();
                

                //Return server's response (a JSON string containing the information we asked for)
                return response;

            
        
                } else {
                throw new RuntimeException("Got URLConnection of type " + urlconn.getClass());
                }
                } else {
                throw new IllegalArgumentException("URL needs to use the HTTP or HTTPS protocols.");
                }
    }


    public Response sendPostTextRequestAndGetResponse(String path, String message) {
     
        try {

            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target("http://localhost:8080/myapp" + path);
            Response response = webTarget
                                .request("text/plain")
                                .post(
                                    Entity.entity(message,
                                    "text/plain"));
                                
            return response;
    
            
    


    

        }   catch (Exception e) {

            e.printStackTrace();
            return null;
    
          }

    }





        

    
    
}

