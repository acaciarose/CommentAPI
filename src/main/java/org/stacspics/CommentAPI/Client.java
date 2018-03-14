package org.stacspics.CommentAPI;

import java.util.Scanner;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.List;

//IO Manager. Prompts for login and makes calls to the client.

public class Client {
    public void runCommandLineReader() throws IOException {
        Scanner command = new Scanner(System.in);
        String responsestring = "";
        Response response = null;
        String path = "";
        String name = "";
        SystemStorage ss = new SystemStorage();
        ss.populateDummyStorageWithComments();
        ss.writeToStorage("storage.json");
        ss = ss.readFromStorage("storage.json");


        System.out.println("Welcome to CommentAPI!");
        System.out.println("Please login. Enter username: ");
        boolean loggedIn = false;

        CommentClient client = new CommentClient();
        

        

        while (!loggedIn) {

            name = command.nextLine();

            if (ss.getUserFromUserName(name) != null) {
                System.out.println("Logged in as " + name);
                loggedIn = true;
            }

            else {
                System.out.println("Sorry, not recognised. Try again.");
            }

        }

        System.out.println("Enter your commands below!");

        boolean running = true;
    
        while (running){

            //split by spaces
            //regex from https://stackoverflow.com/a/7804472
            List<String> list = new ArrayList<String>();
            Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command.nextLine());
            while (m.find())
                list.add(m.group(1));
    
            switch(list.get(0).toLowerCase()) {

            case "postcommentonphoto":
                String commentText = list.get(1);
                int commentPhotoID = Integer.parseInt(list.get(2));

                path = "/users/" + name + "/comments/photos/" + commentPhotoID;

                response = client.sendPostTextRequestAndGetResponse(path, commentText);

                System.out.println("Response code: " + response.getStatus());

                System.out.println("Created comment id: " + response.readEntity(String.class));
                
                break;

    
            case "postreplytocomment":
                String replyText = list.get(2);
                int subjectCommentID = Integer.parseInt(list.get(1));              
                
                path = "/users/" + name + "/comments/replies/" + subjectCommentID;

                response = client.sendPostTextRequestAndGetResponse(path, replyText);

                System.out.println("Response code: " + response.getStatus());

                System.out.println("Created comment id: " + response.readEntity(String.class));

                break;
    

    
            case "getcommentfromid":
                String commentID = list.get(1);


                path = "/comments/" + commentID;

                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                System.out.print(responsestring);
                break;

            case "getrepliesfromcommentid":
                String parentCommentID = list.get(1);
                path = "/comments/" + parentCommentID + "/replies";

                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                System.out.print(responsestring);

                break;


   
            case "getusercomments":
                String username = list.get(1);

                path = "/users/" + username + "/comments";

                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                System.out.print(responsestring);
                break;
    
            case "getphotocomments":
                int photoID = Integer.parseInt(list.get(1));

                path = "/photos/" + photoID + "/comments";

                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                System.out.print(responsestring);

                break;
    
            case "getnotifications":

                path = "/users/" + name + "/notifications";
                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                System.out.print(responsestring);
                               
                break;

            case "deletecomment":
                int deleteCommentID = Integer.parseInt(list.get(1));

                path = "/comments/" + deleteCommentID + "/remove";

                response = client.sendPostTextRequestAndGetResponse(path, name);

                System.out.println("Response code: " + response.getStatus());

                System.out.println(response.readEntity(String.class));

                break;
            case "upvote":
                int upvoteCommentID = Integer.parseInt(list.get(1));

                path = "/comments/" + upvoteCommentID + "/upvote";

                response = client.sendPostTextRequestAndGetResponse(path, "");

                System.out.println("Response code: " + response.getStatus());

                System.out.println(response.readEntity(String.class));

                break;

            case "downvote":
                int downvoteCommentID = Integer.parseInt(list.get(1));

                path = "/comments/" + downvoteCommentID + "/downvote";

                response = client.sendPostTextRequestAndGetResponse(path, "");

                System.out.println("Response code: " + response.getStatus());

                System.out.println(response.readEntity(String.class));

                break;

            case "quit" :
                System.out.println("Quitting....");
                running = false;
                break;


            default:
                System.out.println("Command not recognized!");
                break;
            }
        }
        command.close();

    }

    public static void main(String[] args) {
        Client client = new Client();
        try {
        client.runCommandLineReader();
        } 
        catch (IOException e) {
            System.out.println("Error occurred. Shutting down....");
            e.printStackTrace();
            System.exit(0);
        }
    }

  


}