
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;

import org.junit.Test;
import org.stacspics.CommentAPI.Notification;
import org.stacspics.CommentAPI.Photograph;
import org.stacspics.CommentAPI.Comment;
import org.stacspics.CommentAPI.IDGenerator;
import org.stacspics.CommentAPI.SystemStorage;
import org.stacspics.CommentAPI.User;

//Tests the user object
//This test class relies on the System Storage class, unlike the rest
//since it needs to find users from usernames
public class UserObjectTest {

    static SystemStorage ss;


    @BeforeClass
    public static void setUp() {

        ss = new SystemStorage();
        ss.populateDummyStorage();
    }



    @Test
    public void testOriginalUsers() {
        User user1 = ss.getUserFromUserName("User1");
        assertEquals(user1.getName(), "User1");
        assertEquals(user1.getComments().size(), 0);
        assertEquals(user1.getNotifications().size(), 0);
        assertFalse(user1.isAdmin());


    }

    @Test 
    public void testOriginalAdminUser() {
        User adminUser = ss.getUserFromUserName("Admin");
        assertTrue(adminUser.isAdmin());

    }

    @Test
    public void testAddCommentOnPhoto() {
        User user1 = ss.getUserFromUserName("User1");
        Photograph photoOwnedByUser2 = ss.getPhotosFromUserName("User1").get(0);
        assertEquals(photoOwnedByUser2.getComments().size(), 0);
         user1.postComment("This is a comment on a photo", photoOwnedByUser2, ss);

         assertEquals(photoOwnedByUser2.getComments().size(), 1);

         assertEquals(ss.getPhotosFromUserName("User1").get(0).getComments().size(), 1);
         assertEquals(user1.getComments().size(), 1);
         ss.populateDummyStorage();

    }

    @Test
    public void testAddReplyOnComment() {
        User user1 = ss.getUserFromUserName("User1");
        Photograph photoOwnedByUser2 = ss.getPhotosFromUserName("User1").get(0);
         user1.postComment("This is a comment on a photo", photoOwnedByUser2, ss);
         Comment comment = photoOwnedByUser2.getComments().get(0);
         assertEquals(comment.getAllReplies().size(), 0);

         user1.postComment("This is a reply to the comment!", comment, ss);

         assertEquals(comment.getAllReplies().size(), 1);

         assertEquals(user1.getComments().size(), 2);   
         ss.populateDummyStorage();
        
    }

    @Test
    public void testNotificationAppearingWhenCommentOnPhoto() {
        
    }

    @Test
    public void testNotificationAppearingWhenReplyToComment() {

    }

    @Test
    public void testAdminCanRemoveComment() {
        User user1 = ss.getUserFromUserName("User1");
        User admin = ss.getUserFromUserName("Admin");
        Photograph photoOwnedByUser2 = ss.getPhotosFromUserName("User2").get(0);
         user1.postComment("This is a comment on a photo", photoOwnedByUser2, ss);
         Comment toBeRemoved = user1.getComments().get(0);
         assertTrue(toBeRemoved.remove(admin));
         //Comment shouldn't have been removed, just the text changed
         assertEquals(user1.getComments().size(), 1); 
         assertEquals(user1.getComments().get(0).getText(), "This comment has been removed by an administrator.");

         ss.populateDummyStorage();




    }

    @Test
    public void testNonAdminCantRemoveComment() {
        User user1 = ss.getUserFromUserName("User1");
        User notadmin = ss.getUserFromUserName("User2");
        Photograph photoOwnedByUser2 = ss.getPhotosFromUserName("User2").get(0);
         user1.postComment("This is a comment on a photo", photoOwnedByUser2, ss);
         Comment toBeRemoved = user1.getComments().get(0);
         assertFalse(toBeRemoved.remove(notadmin));

         assertNotEquals(user1.getComments().get(0).getText(), "This comment has been removed by an administrator.");

         ss.populateDummyStorage();

    }

    @Test
    public void testNotificationsAreRead() {
        User user1 = ss.getUserFromUserName("User1");
        User admin = ss.getUserFromUserName("Admin");
        Photograph photoOwnedByUser1 = ss.getPhotosFromUserName("User1").get(0);
        assertEquals(user1.getNotifications().size(), 0);


         admin.postComment("This is a comment on a photo", photoOwnedByUser1, ss);

         assertEquals(photoOwnedByUser1.getComments().size(), 1);
         assertEquals(admin.getComments().size(), 1);

         user1 = ss.getUserFromUserName("User1");
         assertEquals(user1.getNotifications().size(), 1);

         //Should now be read
         assertEquals(user1.getNotifications().size(), 0);  

         ss.populateDummyStorage();

    }

    @Test
    public void testOnlyUnreadNotificationsAreReturned() {
        User user1 = ss.getUserFromUserName("User1");
        User admin = ss.getUserFromUserName("Admin");
        Photograph photoOwnedByUser1 = ss.getPhotosFromUserName("User1").get(0);
        assertEquals(user1.getNotifications().size(), 0);


         admin.postComment("This is a comment on a photo", photoOwnedByUser1, ss);

         user1 = ss.getUserFromUserName("User1");
         assertEquals(user1.getNotifications().size(), 1);

         //Should now be read
         assertEquals(user1.getNotifications().size(), 0);  

         admin.postComment("This is a comment on a photo", photoOwnedByUser1, ss);
         admin.postComment("This is a comment on a photo", photoOwnedByUser1, ss);


         assertEquals(user1.getNotifications().size(), 2);

         //Should now be read
         assertEquals(user1.getNotifications().size(), 0); 



         ss.populateDummyStorage();

    }

    @Test
    public void testUpvote() {
        User user1 = ss.getUserFromUserName("User1");
        User notadmin = ss.getUserFromUserName("User2");
        Photograph photoOwnedByUser2 = ss.getPhotosFromUserName("User2").get(0);
         user1.postComment("This is a comment on a photo", photoOwnedByUser2, ss);
         Comment comment = user1.getComments().get(0);
         comment.upvote();
         assertEquals(comment.getUpvotes(), 1);

         assertEquals(user1.getComments().get(0).getUpvotes(), 1);
         

         ss.populateDummyStorage();

    }

    @Test
    public void testDownvote() {
        User user1 = ss.getUserFromUserName("User1");
        User notadmin = ss.getUserFromUserName("User2");
        Photograph photoOwnedByUser2 = ss.getPhotosFromUserName("User2").get(0);
         user1.postComment("This is a comment on a photo", photoOwnedByUser2, ss);
         Comment comment = user1.getComments().get(0);
         comment.downvote();
         comment.downvote();
         comment.downvote();

         assertEquals(comment.getUpvotes(), -3);

         assertEquals(user1.getComments().get(0).getUpvotes(), -3);
         

         ss.populateDummyStorage();

    }



}
