package org.stacspics.CommentAPI;

import java.util.List;
import java.util.Map;

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

    //GET request
    public String sendGetRequestAndReturnServerResponse(String path) throws IOException {
                //Manually set URL for now
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
                StringBuffer response = new StringBuffer();
        
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                System.out.println(response.toString());
                //This currently just returns the whole response.
                return response.toString();
        
                } else {
                throw new RuntimeException("Got URLConnection of type " + urlconn.getClass());
                }
                } else {
                throw new IllegalArgumentException("URL needs to use the HTTP or HTTPS protocols.");
                }
    }


    //Test client, just to see if we can make a request/response
    //modified from lecture slides
    //Will be deleted once proper functionality is imlemented
    public void makeSampleGETRequest() throws IOException {

        //Manually set URL for now
        URL url = new URL("http", "localhost", 8080, "/myapp/helloworld/de");

        System.out.println( "Connecting using URL : " + url.toString());

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
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        in.close();
        
        System.out.println(response.toString());

        } else {
        throw new RuntimeException("Got URLConnection of type " + urlconn.getClass());
        }
        } else {
        throw new IllegalArgumentException("URL needs to use the HTTP or HTTPS protocols.");
        }
        }


        

    
    
}

