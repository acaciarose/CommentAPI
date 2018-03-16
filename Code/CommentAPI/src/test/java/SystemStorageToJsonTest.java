
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import org.stacspics.CommentAPI.SystemStorage;
import org.stacspics.CommentAPI.Photograph;


//Tests the jsonify functions for whole stored system
public class SystemStorageToJsonTest {
    static SystemStorage ss;

    @BeforeClass
    public static void setUp(){
        ss = new SystemStorage();
        ss.populateDummyStorage();
    }


    @Test
    public void testSystemStorageToGsonFunction() {
        Gson gson = new Gson();
        
        String jsonstring = ss.turnToJsonString();

        SystemStorage parsedstorage = gson.fromJson(jsonstring, SystemStorage.class);

        assertEquals(ss.getUsers().size(), parsedstorage.getUsers().size());
        assertEquals(ss.getPhotosFromUserName("User1").size(), parsedstorage.getPhotosFromUserName("User1").size());
        assertEquals(ss.getPhotos().size(), parsedstorage.getPhotos().size());
        assertEquals(parsedstorage.getPhotos().get("User1").get(0).getClass(), Photograph.class);



    }

    @Test
    public void testWriteSystemStorageToFile() throws IOException, FileNotFoundException{

        Gson gson = new Gson();
        
        ss.writeToStorage("storage.json");

        SystemStorage newss = gson.fromJson(new FileReader("storage.json"), SystemStorage.class);

        assertEquals(newss.getClass(), SystemStorage.class);
        assertEquals(ss.getUsers().size(), newss.getUsers().size());
        assertEquals(ss.getPhotosFromUserName("User1").size(), newss.getPhotosFromUserName("User1").size());
        assertEquals(ss.getPhotos().size(), newss.getPhotos().size());
        assertEquals(newss.getPhotos().get("User1").get(0).getClass(), Photograph.class);



    }
 








}
