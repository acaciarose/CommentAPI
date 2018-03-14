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
    //Run main input reader
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
        System.out.println("Please login. Enter username, from choice of: ");
        System.out.println("User1 User2 User3 Admin");
        boolean loggedIn = false;

        CommentClient client = new CommentClient();
        

        //Very basic and not secure login functionality
        //Could be easily replaces/improved if necessary
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
    
        //Main command loop
        while (running) {

            //Split by spaces, except when in speech marks!
            //regex from https://stackoverflow.com/a/7804472
            List<String> parametersList = new ArrayList<String>();
            Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command.nextLine());
            while (m.find())
                parametersList.add(m.group(1));
    
            
            String firstPartOfCommand = parametersList.get(0).toLowerCase();
            switch(firstPartOfCommand) {
            //Names of below cases should be fairly self explanatory

            case "postcommentonphoto":
                String commentText = parametersList.get(1);
                int commentPhotoID = Integer.parseInt(parametersList.get(2));

                path = "/users/" + name + "/comments/photos/" + commentPhotoID;

                response = client.sendPostTextRequestAndGetResponse(path, commentText);

                System.out.println("Response code: " + response.getStatus());
                System.out.println("Created comment id: " + response.readEntity(String.class));
                
                break;

    
            case "postreplytocomment":
                String replyText = parametersList.get(2);
                int subjectCommentID = Integer.parseInt(parametersList.get(1));              
                
                path = "/users/" + name + "/comments/replies/" + subjectCommentID;

                response = client.sendPostTextRequestAndGetResponse(path, replyText);

                prettyPrintResponse(response);

                break;
    

    
            case "getcommentfromid":
                String commentID = parametersList.get(1);

                path = "/comments/" + commentID;

                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                printJSONResponse(responsestring);
                break;

            case "getrepliesfromcommentid":
                String parentCommentID = parametersList.get(1);
                path = "/comments/" + parentCommentID + "/replies";

                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                printJSONResponse(responsestring);

                break;


   
            case "getusercomments":
                String username = parametersList.get(1);

                path = "/users/" + username + "/comments";

                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                printJSONResponse(responsestring);

                break;
    
            case "getphotocomments":
                int photoID = Integer.parseInt(parametersList.get(1));

                path = "/photos/" + photoID + "/comments";

                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                printJSONResponse(responsestring);

                break;
    
            case "getnotifications":

                path = "/users/" + name + "/notifications";
                responsestring = client.sendGetRequestAndReturnServerResponse(path);

                printJSONResponse(responsestring);
                               
                break;

            case "deletecomment":
                int deleteCommentID = Integer.parseInt(parametersList.get(1));

                path = "/comments/" + deleteCommentID + "/remove";

                response = client.sendPostTextRequestAndGetResponse(path, name);

                prettyPrintResponse(response);

                break;
            case "upvote":
                int upvoteCommentID = Integer.parseInt(parametersList.get(1));

                path = "/comments/" + upvoteCommentID + "/upvote";

                response = client.sendPostTextRequestAndGetResponse(path, "");

                prettyPrintResponse(response);


                break;

            case "downvote":
                int downvoteCommentID = Integer.parseInt(parametersList.get(1));

                path = "/comments/" + downvoteCommentID + "/downvote";

                response = client.sendPostTextRequestAndGetResponse(path, "");

                prettyPrintResponse(response);

 

                break;

            case "quit" :
                System.out.println("Quitting....");
                running = false;
                break;

            //Catches mistyped/wrong commands
            default:
                System.out.println("Command not recognized!");
                break;
            }
        }
        command.close();

    }

    private void prettyPrintResponse(Response response) {
        System.out.println("Response code: " + response.getStatus());

        System.out.println(response.readEntity(String.class));
    }

    private void printJSONResponse(String response) {
        System.out.println("Server response :");
        System.out.print(response);

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