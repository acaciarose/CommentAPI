
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.After;
import static org.junit.Assert.*;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import org.stacspics.CommentAPI.*;


//Tests REST server handlers for reading/making comments, etc
public class RestUserHandlersTest {
    static CommentClient cc;
    static SystemStorage ss;
    static Server s;
    static Gson gson;

    @BeforeClass
    public static void setUp() throws IOException {
     cc = new CommentClient();
     ss = new SystemStorage();
     s = new Server();
     s.startServer();
     gson = new Gson();
     ss.populateDummyStorageWithComments();
     //Write known/dummy values to storage
     ss.writeToStorage("storage.json");
     //Read back from storage (just in case)
     ss.readFromStorage("storage.json");
    }




    @Test
    public void testGetUserComments() throws IOException {
        String response = cc.sendGetRequestAndReturnServerResponse("/users/User1/comments");
        ArrayList<Comment> returned = gson.fromJson(response, new TypeToken<List<Comment>>(){}.getType());
        ArrayList<Comment> comments = ss.getUserFromUserName("User1").getComments();
        assertEquals(returned.size(), comments.size());
 
        assertEquals(returned.get(0).getCommenter(), comments.get(0).getCommenter());
        assertEquals(returned.get(0).getText(), comments.get(0).getText());
        
        ss = new SystemStorage();
        ss.populateDummyStorageWithComments();
        //Write known/dummy values to storage
        ss.writeToStorage("storage.json");
    }

    @Test
    //Should return the ID of the created comment
    public void testPostUserCommentOnPhoto() throws IOException {
        Response response = cc.sendPostTextRequestAndGetResponse("/users/User1/comments/photos/0", "A created comment");
        assertEquals(response.getStatus(), 201);

        assertEquals(response.readEntity(String.class), "2");
        
        ss = ss.readFromStorage("storage.json");
        Comment created = ss.getCommentByID(2);
        assertEquals(created.getText(), "A created comment");

        ss = new SystemStorage();
        ss.populateDummyStorageWithComments();
        //Write known/dummy values to storage
        ss.writeToStorage("storage.json");


    }

    @Test
    //Should return the ID of the created comment
    public void testPostUserReplyOnComment() throws IOException {
        Response response = cc.sendPostTextRequestAndGetResponse("/users/User1/comments/replies/1", "A reply to a comment");
        assertEquals(response.getStatus(), 201);

        assertEquals(response.readEntity(String.class), "2");
        
        ss = ss.readFromStorage("storage.json");
        Comment created = ss.getCommentByID(2);
        assertEquals(created.getText(), "A reply to a comment");

        ss = new SystemStorage();
        ss.populateDummyStorageWithComments();
        //Write known/dummy values to storage
        ss.writeToStorage("storage.json");

    }

    @Test
    public void testGetNotifications() throws IOException {
        User user = ss.getUserFromUserName("User1");
        String response = cc.sendGetRequestAndReturnServerResponse("/users/User1/notifications");
        ArrayList<Notification> notifications = user.getNotifications(ss);
        ArrayList<Notification> recieved = gson.fromJson(response, new TypeToken<List<Notification>>(){}.getType());
        assertEquals(notifications.size(), recieved.size());
        assertEquals(notifications.get(0).getNotificationText(), recieved.get(0).getNotificationText());

        //make sure notifications were read
        ss = ss.readFromStorage("storage.json");
        user = ss.getUserFromUserName("User1");
        notifications = user.getNotifications(ss);
        assertEquals(notifications.size(), 0);


    
    }




    @AfterClass
    public static void tearDownClass() throws IOException {
        s.stopServer();

    }

 








}
