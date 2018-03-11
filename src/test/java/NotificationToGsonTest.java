
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;
import com.google.gson.*;

import org.junit.Test;

import org.stacspics.CommentAPI.Notification;
import org.stacspics.CommentAPI.Comment;
import org.stacspics.CommentAPI.SystemStorage;


//Tests the jsonify functions for comment
public class NotificationToGsonTest {
    static SystemStorage ss;

    @BeforeClass
    public static void setUp(){
     ss = new SystemStorage();
    }


    @Test
    public void testNotificationToGsonFunction() {
        Gson gson = new Gson();
        Comment comment = new Comment("Comment", "User1", false, ss.getGenerator() );

        Notification note = new Notification("A notification", comment);

        String jsonstring = note.turnToJsonString();

        Notification parsednote = gson.fromJson(jsonstring, Notification.class);

        assertEquals(note.getComment().getText(), parsednote.getComment().getText());
        assertEquals(note.getNotificationText(), parsednote.getNotificationText());
        assertEquals(note.checkIfRead(), parsednote.checkIfRead());


    }
 








}
