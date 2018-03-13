
import org.junit.BeforeClass;
import org.junit.After;
import static org.junit.Assert.*;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import org.stacspics.CommentAPI.SystemStorage;
import org.stacspics.CommentAPI.User;
import org.stacspics.CommentAPI.Photograph;
import org.stacspics.CommentAPI.Comment;

//Tests System Storage
public class SystemStorageTest {
    static SystemStorage ss;

    @BeforeClass
    public static void setUp(){
        ss = new SystemStorage();
    }


    @Test
    public void testSystemStorageCreation() {
        assertEquals(ss.getPhotos().size(), 0);
        assertEquals(ss.getUsers().size(), 0);
        assertEquals(ss.getComments().size(), 0);

    }

    @Test
    public void testPhotoAddition() throws IOException {
        Photograph photo = new Photograph("User1", ss.getGenerator());
        ss.addPhotoToSystem(photo);
        assertEquals(ss.getPhotos().size(), 1);
        assertEquals(ss.getPhotoFromID(0).getUser(), photo.getUser());
    }

    @Test
    public void testUserAddition() throws IOException {
        User user1 = new User("User1", false);
        User user2 = new User("Admin", true);
        ss.addUserToSystem(user1);
        ss.addUserToSystem(user2);
        assertEquals(ss.getUsers().size(), 2);
        assertEquals(ss.getUserFromUserName("User1").getName(), "User1");
        assertNull(ss.getUserFromUserName("NonExistentUser"));
        assertTrue(ss.getUserFromUserName("Admin").isAdmin());
    }

    @Test
    public void testPhotoCommentAddition() throws IOException {
        User user1 = new User("User1", false);
        User user2 = new User("User2", true);

        ss.addUserToSystem(user1);
        ss.addUserToSystem(user2);

        Photograph photo = new Photograph("User1", ss.getGenerator());

        ss.addPhotoToSystem(photo);

        assertEquals(ss.getPhotos().size(), 1);

        assertTrue(user2.postComment("A comment", photo, ss));

        assertEquals(user2.getComments().size(), 1);

        assertEquals(ss.getPhotoFromID(0).getComments().size(), 1);

        assertEquals(user1.getNotifications().size(), 1);

        //Check that they were read
        assertEquals(user1.getNotifications().size(), 0);

    }

    @Test
    public void testCommentReplyAddition() throws IOException {
        User user1 = new User("User1", false);
        User user2 = new User("User2", true);

        ss.addUserToSystem(user1);
        ss.addUserToSystem(user2);

        Photograph photo = new Photograph("User1", ss.getGenerator());

        ss.addPhotoToSystem(photo);

        assertTrue(user2.postComment("A comment", photo, ss));
        Comment comment = ss.getCommentByID(0);
        assertTrue(user1.postComment("A reply", comment, ss));



        assertEquals(user2.getComments().size(), 1);
        assertEquals(user1.getComments().size(), 1);

        //getComments only returns top level comments
        assertEquals(ss.getPhotoFromID(0).getComments().size(), 1);

        assertEquals(ss.getComments().size(), 2);

        assertEquals(user1.getNotifications().size(), 1);

        //Check that they were read
        assertEquals(user2.getNotifications().size(), 1);

    }

    @Test
    public void testUpvoteDownvote() throws IOException {
        User user1 = new User("User1", false);
        User user2 = new User("User2", true);

        ss.addUserToSystem(user1);
        ss.addUserToSystem(user2);

        Photograph photo = new Photograph("User1", ss.getGenerator());

        ss.addPhotoToSystem(photo);

        assertTrue(user2.postComment("A comment", photo, ss));

        Comment comment = ss.getCommentByID(0);

        assertTrue(comment.upvote(ss));

        comment = ss.getCommentByID(0);

        assertEquals(comment.getUpvotes(), 1);
        assertTrue(comment.downvote(ss));
        assertEquals(comment.getUpvotes(), 0);
        


    }


    @After
    public void resetSS() {
        ss = new SystemStorage();
    }

 








}
