
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.junit.Test;
import org.stacspics.CommentAPI.Notification;
import org.stacspics.CommentAPI.Photograph;
import org.stacspics.CommentAPI.SystemStorage;
import org.stacspics.CommentAPI.Comment;
import org.stacspics.CommentAPI.IDGenerator;


public class PhotographObjectTest {

    static Photograph p;
    static Photograph p2;
    static IDGenerator IDgen;

    @BeforeClass
    public static void setUp() {
    IDgen = new IDGenerator();
    p = new Photograph("User1", IDgen);
    p2 = new Photograph("User2", IDgen);

    }

    @Test
    public void testPhotographObjectCreation() {
        assertEquals(p.getComments().size(), 0);
        assertEquals(p.getID(), 0);
        assertEquals(p2.getID(), 1);
        assertEquals(p.getUser(), "User1");
        assertEquals(p2.getUser(), "User2");
    }

    @Test
    public void testAddComment() {
        Comment comment = new Comment("A comment", "User4", true, IDgen);
        p.addComment(comment);
        assertEquals(p.getComments().size(), 1);
        assertEquals(p.getComments().get(0), comment);
        assertEquals(p.getComments().get(0).getCommenter(), "User4");


        //Reset p (make sure tests can run independently)
        p  = new Photograph("User1", IDgen); 
    }



}
