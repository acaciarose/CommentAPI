
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javax.ws.rs.core.Response;

import org.junit.Test;

import org.stacspics.CommentAPI.CommentClient;
import org.stacspics.CommentAPI.Server;
import org.stacspics.CommentAPI.Comment;
import org.stacspics.CommentAPI.SystemStorage;


//Tests REST server handlers for reading/making comments, etc
public class RestCommentHandlersTest {
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
    public void testGetCommentByIDComment() throws IOException {
        String response = cc.sendGetRequestAndReturnServerResponse("/comments/0");
        Comment comment = ss.getCommentByID(0);
        Comment returned = gson.fromJson(response, Comment.class);
        assertEquals(comment.getText(), returned.getText());
        assertEquals(comment.getCommenter(), returned.getCommenter());
    
    }


    @Test
    public void testGetRepliesToComment() throws IOException {
        String response = cc.sendGetRequestAndReturnServerResponse("/comments/0/replies");
        ArrayList<Comment> replies = ss.getCommentByID(0).getAllReplies();
        ArrayList<Comment> returned = gson.fromJson(response, new TypeToken<List<Comment>>(){}.getType());
        assertEquals(replies.size(), returned.size());
        assertEquals(replies.get(0).getCommenter(), returned.get(0).getCommenter());
        
    
    }

    @Test
    public void testDeleteComment() throws IOException {
        Response response = cc.sendPostTextRequestAndGetResponse("/comments/0/remove", "Admin");
        assertEquals(response.getStatus(), 201);

        ss = ss.readFromStorage("storage.json");

        Comment deleted = ss.getCommentByID(0);

        assertEquals(deleted.getText(), "This comment has been removed by an administrator.");

        ss.populateDummyStorageWithComments();
     //Write known/dummy values to storage
     ss.writeToStorage("storage.json");
     //Read back from storage (just in case)
     ss.readFromStorage("storage.json");
    }

    @Test
    public void testUpvoteComment() throws IOException {
        Response response = cc.sendPostTextRequestAndGetResponse("/comments/0/upvote", "");

        assertEquals(response.getStatus(), 201);

        ss = ss.readFromStorage("storage.json");

        Comment upvoted = ss.getCommentByID(0);

        //In dummy data, this comment already starts with -1 upvotes
        assertEquals(upvoted.getUpvotes(), 0);

        ss.populateDummyStorageWithComments();
     //Write known/dummy values to storage
     ss.writeToStorage("storage.json");
     //Read back from storage (just in case)
     ss.readFromStorage("storage.json");
    }

    @Test
    public void testDownvoteComment() throws IOException {
        Response response = cc.sendPostTextRequestAndGetResponse("/comments/0/downvote", "");

        assertEquals(response.getStatus(), 201);

        ss = ss.readFromStorage("storage.json");

        Comment downvoted = ss.getCommentByID(0);

        //In dummy data, this comment already starts with -1 upvotes
        assertEquals(downvoted.getUpvotes(), -2);
        
        ss.populateDummyStorageWithComments();
     //Write known/dummy values to storage
     ss.writeToStorage("storage.json");
     //Read back from storage (just in case)
     ss.readFromStorage("storage.json");
    }


    @AfterClass
    public static void tearDown() {
        s.stopServer();
    }

 








}
