
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;
import com.google.gson.*;

import org.junit.Test;

import org.stacspics.CommentAPI.Photograph;
import org.stacspics.CommentAPI.SystemStorage;


//Tests the jsonify functions for photograph
public class PhotographToGsonTest {
    static SystemStorage ss;

    @BeforeClass
    public static void setUp(){
     ss = new SystemStorage();
    }


    @Test
    public void testPhotographToGsonFunction() {
        Gson gson = new Gson();
        Photograph photo = new Photograph("User1", ss.getGenerator());

        String jsonstring = photo.turnToJsonString();

        Photograph parsedphoto = gson.fromJson(jsonstring, Photograph.class);

        assertEquals(photo.getComments(), parsedphoto.getComments());
        assertEquals(photo.getUser(), parsedphoto.getUser());
        assertEquals(photo.getID(), parsedphoto.getID());


    }
 








}
