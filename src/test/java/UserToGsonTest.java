
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;
import com.google.gson.*;

import org.junit.Test;

import org.stacspics.CommentAPI.User;
import org.stacspics.CommentAPI.SystemStorage;


//Tests the jsonify functions for comment
public class UserToGsonTest {
    static SystemStorage ss;

    @BeforeClass
    public static void setUp(){
     ss = new SystemStorage();
    }


    @Test
    public void testUserToGsonFunction() {
        Gson gson = new Gson();
        User user = new User("User1", false);

        String jsonstring = user.turnToJsonString();

        User parseduser = gson.fromJson(jsonstring, User.class);

        assertEquals(user.getComments(), parseduser.getComments());
        assertEquals(user.getName(), parseduser.getName());
        assertEquals(user.getNotifications(), parseduser.getNotifications());
        assertEquals(user.isAdmin(), parseduser.isAdmin());


    }
 








}
