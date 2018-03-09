
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.junit.Test;
import org.stacspics.CommentAPI.Comment;
import org.stacspics.CommentAPI.IDGenerator;


public class CommentObjectTest {

    static Comment topLevelComment;
    static Comment childComment;
    static IDGenerator IDgen;

    @BeforeClass
    public static void setUp() {
        IDgen = new IDGenerator();
        topLevelComment = new Comment("This is a comment on a photograph!", "User1", true, IDgen);

    }

    @Test
    //Note here that timestamp is not tested - can assume timestamp creation method is already tested
    public void testCommentObjectCreation() {
        assertEquals(topLevelComment.getUpvotes(), 0);
        assertEquals(topLevelComment.getID(), 0);
        assertEquals(topLevelComment.getCommenter(), "User1");
        assertEquals(topLevelComment.getAllReplies().size(), 0);


    }

    @Test 
    public void testUpvoteDownvote() {
        topLevelComment.upvote();
        assertEquals(topLevelComment.getUpvotes(), 1);
        topLevelComment.downvote();
        assertEquals(topLevelComment.getUpvotes(), 0);
        topLevelComment.downvote();
        assertEquals(topLevelComment.getUpvotes(), -1); 
        topLevelComment.upvote();
        assertEquals(topLevelComment.getUpvotes(), 0);     
    }

    @Test
    public void testAddComment() {

        Comment reply = new Comment("This is a reply!", "User2", false, IDgen);
        topLevelComment.addReply(reply);
        assertEquals(topLevelComment.getAllReplies().size(), 1);
        assertEquals(topLevelComment.getAllReplies().get(0), reply);

        //Make sure we reset number of comments afterwards (make sure tests are independent)
        topLevelComment = new Comment("This is a comment on a photograph!", "User1", true, IDgen);
    }




}
