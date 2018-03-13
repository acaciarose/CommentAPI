
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.IOException;

import com.google.gson.*;

import org.junit.Test;

import org.stacspics.CommentAPI.CommentClient;
import org.stacspics.CommentAPI.Server;
import org.stacspics.CommentAPI.SystemStorage;


//Tests REST server handlers for reading/making comments, etc
public class RestCommentHandlersTest {
    static CommentClient cc;
    static SystemStorage ss;
    static Server s;

    @BeforeClass
    public static void setUp(){
     cc = new CommentClient();
     ss = new SystemStorage();
     s = new Server();
     s.startServer();
     //Write some dummy/sample values to storage so we can test on them
     ss.populateDummyStorage();
    }


    @Test
    public void testReadComment() throws IOException{
        int responsecode = cc.sendGetRequestAndReturnServerResponse("/comments/0");
        assertEquals(responsecode, 200);


    }
 








}
