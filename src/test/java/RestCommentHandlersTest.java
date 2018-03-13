
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import java.io.IOException;

import com.google.gson.*;

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
    public void testReadComment() throws IOException {
        String response = cc.sendGetRequestAndReturnServerResponse("/comments/0");
        Comment comment = ss.getCommentByID(0);
        Comment returned = gson.fromJson(response, Comment.class);
        assertEquals(comment.getText(), returned.getText());
        assertEquals(comment.getCommenter(), returned.getCommenter());
        


    }

    @AfterClass
    public static void tearDown() {
        s.stopServer();
    }

 








}
