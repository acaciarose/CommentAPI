package org.stacspics.CommentAPI;

import java.util.List;
import java.util.Map;

import java.net.HttpURLConnection;
import java.net.URLConnection;


import java.io.IOException;
import java.net.URI;

import java.net.URL;

//Client class. Designed to take requests from user (command line), relay them to the server, and display the response (if any)
public class CommentClient {


    //Test client, just to see if we can make a request/response
    //modified from lecture slides
    public void makeSampleGETRequest() throws IOException {
        System.out.println("Client doing stuff!!!!");
        System.setProperty("https.protocols", "TLSv1.1");

        //Manually set URL for now
        URL url = new URL("http", "localhost", 8080, "/myapp/helloworldTEST");

        System.out.println( "Connecting using URL : " + url.toString());

        final String protocol = url.getProtocol();
        if (protocol.equalsIgnoreCase("http")) {
        final URLConnection urlconn = url.openConnection();
        if (urlconn instanceof HttpURLConnection) {
        final HttpURLConnection conn = (HttpURLConnection) urlconn;
        conn.connect();
        System.err.println(conn.getResponseCode() + ": " + conn.getResponseMessage());
        final Map<String, List<String>> headers = conn.getHeaderFields();
        for (String headerName : headers.keySet()) {
        System.out.println(headerName + ":");
        for (String headerValue : headers.get(headerName)) {
        System.out.println(" " + headerValue);
        }
        }
        } else {
        throw new RuntimeException("Got URLConnection of type " + urlconn.getClass());
        }
        } else {
        throw new IllegalArgumentException("URL needs to use the HTTP or HTTPS protocols.");
        }
        }


        

    
    
}

