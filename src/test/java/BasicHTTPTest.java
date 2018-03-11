
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.stacspics.CommentAPI.Server;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;


//These tests mainly assert that the server is running correctly
//and don't test the client functionality.
//Paths may have to be changed later when "helloworldTEST" is removed!
public class BasicHTTPTest {

    static Server s = new Server();

    @BeforeClass
    public static void setUp() throws Exception {        
        s.startServer();
    }

    @Test
    public void testStatusCodeWhenPathPresent() throws IOException, MalformedURLException{
        URL url = new URL("http", "localhost", 8080, "/myapp/helloworld/de");
        final URLConnection urlconn = url.openConnection();
        final HttpURLConnection conn = (HttpURLConnection) urlconn;
        conn.connect();

        assertEquals(conn.getResponseCode(), 200);

    }

    @Test
    public void testStatusCodeWhenPathNotPresent() throws IOException, MalformedURLException{
        URL url = new URL("http", "localhost", 8080, "/myapp/nonexistent");
        final URLConnection urlconn = url.openConnection();
        final HttpURLConnection conn = (HttpURLConnection) urlconn;
        conn.connect();

        assertEquals(conn.getResponseCode(), 404);

    }

    @AfterClass 
    public static void tearDown() {
       s.stopServer();

    }

}
