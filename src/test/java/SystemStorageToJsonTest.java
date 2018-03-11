
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;
import com.google.gson.*;

import org.junit.Test;

import org.stacspics.CommentAPI.SystemStorage;
import org.stacspics.CommentAPI.Photograph;


//Tests the jsonify functions for whole stored system
public class SystemStorageToJsonTest {
    static SystemStorage ss;

    @BeforeClass
    public static void setUp(){
     ss = new SystemStorage();
    }


    @Test
    public void testSystemStorageToGsonFunction() {
        Gson gson = new Gson();
        
        String jsonstring = ss.turnToJsonString();

        SystemStorage parsedstorage = gson.fromJson(jsonstring, SystemStorage.class);

        assertEquals(ss.getUsers(), parsedstorage.getUsers());
        assertEquals(ss.getPhotosFromUserName("User1").size(), parsedstorage.getPhotosFromUserName("User1").size());
        assertEquals(ss.getPhotos().size(), parsedstorage.getPhotos().size());
        assertEquals(parsedstorage.getPhotos().get(0).getClass(), Photograph.class);



    }
 








}
