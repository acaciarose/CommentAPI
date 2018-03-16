
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import org.stacspics.CommentAPI.CommentClient;
import org.stacspics.CommentAPI.Photograph;
import org.stacspics.CommentAPI.Server;
import org.stacspics.CommentAPI.Comment;
import org.stacspics.CommentAPI.SystemStorage;


//Tests REST server handlers for reading/making comments, etc
public class RestPhotoHandlersTest {
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
    public void testGetPhotoComments() throws IOException {
        String response = cc.sendGetRequestAndReturnServerResponse("/photos/0/comments");
        Photograph photo = ss.getPhotoFromID(0);
        ArrayList<Comment> comments = photo.getComments();
        ArrayList<Comment> returned = gson.fromJson(response, new TypeToken<List<Comment>>(){}.getType());
        assertEquals(comments.size(), returned.size());
        assertEquals(comments.get(0).getCommenter(), returned.get(0).getCommenter());

    
    }



    @AfterClass
    public static void tearDown() {
        s.stopServer();
    }

 








}
