
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;


import org.junit.Test;
import org.stacspics.CommentAPI.Notification;
import org.stacspics.CommentAPI.Comment;


public class NotificationObjectTest {

    static Notification n;
    static Comment comment;

    @BeforeClass
    public static void setUp() {
        n = new Notification("User1 has commented on your photograph!", comment);
    }

    @Test
    public void testNotificationObjectCreation() {
        assertFalse(n.checkIfRead());
        assertEquals(n.getNotificationText(), "User1 has commented on your photograph!");
        assertEquals(n.getComment(), comment);

    }

    @Test
    public void testNotificationMarkAsRead() {
        n.markAsRead();
        assertTrue(n.checkIfRead());
    }


}
