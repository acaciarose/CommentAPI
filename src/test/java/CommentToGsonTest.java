
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;
import com.google.gson.*;

import org.junit.Test;

import org.stacspics.CommentAPI.Comment;
import org.stacspics.CommentAPI.SystemStorage;


//Tests the jsonify functions for various objects
public class CommentToGsonTest {
    static SystemStorage ss;

    @BeforeClass
    public static void setUp(){
     ss = new SystemStorage();
    }


    @Test
    public void testCommentToGsonFunction() {
        Gson gson = new Gson();
        Comment c = new Comment("Comment", "User1", false, ss.getGenerator() );

        String jsonstring = c.turnToJsonString();

        System.out.println(jsonstring);

        Comment parsedc = gson.fromJson(jsonstring, Comment.class);

        assertEquals(parsedc.getAllReplies(), c.getAllReplies());
        assertEquals(parsedc.getCommenter(), c.getCommenter());
        assertEquals(parsedc.getText(), c.getText());
        assertEquals(parsedc.getUpvotes(), c.getUpvotes());


    }
 








}
