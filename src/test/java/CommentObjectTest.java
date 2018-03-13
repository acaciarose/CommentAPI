
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.junit.Test;
import org.stacspics.CommentAPI.Comment;
import org.stacspics.CommentAPI.IDGenerator;
import org.stacspics.CommentAPI.SystemStorage;


public class CommentObjectTest {

    static Comment topLevelComment;
    static Comment childComment;
    static SystemStorage ss;

    @BeforeClass
    public static void setUp() {
        ss = new SystemStorage();
        ss.populateDummyStorage();
        topLevelComment = new Comment("This is a comment on a photograph!", "User1", true, ss.getGenerator());

    }

    @Test
    //Can assume timestamp creation method is already tested
    public void testCommentObjectCreation() {
        assertFalse(ss == null);

        assertEquals(topLevelComment.getUpvotes(), 0);
        assertEquals(topLevelComment.getID(), 0);
        assertEquals(topLevelComment.getCommenter(), "User1");
        assertEquals(topLevelComment.getAllReplies().size(), 0);

        

        ss.populateDummyStorage();


    }

    // @Test 
    // public void testUpvoteDownvote() {
    //     topLevelComment.upvote(ss);
    //     assertEquals(topLevelComment.getUpvotes(), 1);
    //     topLevelComment.downvote(ss);
    //     assertEquals(topLevelComment.getUpvotes(), 0);
    //     topLevelComment.downvote(ss);
    //     assertEquals(topLevelComment.getUpvotes(), -1); 
    //     topLevelComment.upvote(ss);
    //     assertEquals(topLevelComment.getUpvotes(), 0); 
        
    //     ss.populateDummyStorage();
    // }

    // @Test
    // public void testAddComment() {

    //     Comment reply = new Comment("This is a reply!", "User2", false, ss.getGenerator());
    //     topLevelComment.addReply(reply);
    //     assertEquals(topLevelComment.getAllReplies().size(), 1);
    //     assertEquals(topLevelComment.getAllReplies().get(0), reply);

    //     //Make sure we reset number of comments afterwards (make sure tests are independent)
    //     topLevelComment = new Comment("This is a comment on a photograph!", "User1", true, ss.getGenerator());

    //     ss.populateDummyStorage();
    // }




}
