package org.stacspics.CommentAPI;

//Stores list of all users and photographs on the system
//as well as the ID Generator

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.*;


public class SystemStorage {

    //Map of user objects to username
    private HashMap<String, User> users;
    private HashMap<String, ArrayList<Photograph>> photographs;
    private IDGenerator IDgen;
    private HashMap<Integer, Comment> comments;

    public SystemStorage() {
        users = new HashMap<String, User>();
        photographs = new HashMap<String, ArrayList<Photograph>>();
        IDgen = new IDGenerator();

        populateDummyStorage();
    }

    //Add photo to photos list
    public void addPhotoToSystem(Photograph photo) {
        ArrayList<Photograph> newphotos = getPhotosFromUserName(photo.getUser());

        if (newphotos == null) {
            newphotos = new ArrayList<Photograph>();
        }

        newphotos.add(photo);

        photographs.put(photo.getUser(), newphotos  );

    }

    //This currently will overwrite users 
    //Not a huge problem now since method only used for adding dummies
    public void addUserToSystem(User user) {
        users.put(user.getName(), user);
    }

    public IDGenerator getGenerator() {
        return IDgen;
    }

    public User getUserFromUserName(String name) {
        return users.get(name);
    }

    public ArrayList<Photograph> getPhotosFromUserName(String name) {
        return photographs.get(name);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public HashMap<String, ArrayList<Photograph>> getPhotos() {
        return photographs;
    }

    public void addCommentToSystem(Comment comment) {
        comments.put(comment.getID(), comment);
    }

    public Comment getCommentByID(int ID) {
        return comments.get(ID);
    }

    //Populate system storage with dummy users and photos
    //for demonstration purposes
    public void populateDummyStorage() {
        //Reset - for test purposes
        users = new HashMap<String, User>();
        photographs = new HashMap<String, ArrayList<Photograph>>();
        IDgen = new IDGenerator();

        User user1 = new User("User1", false);
        User user2 = new User("User2", false);
        User user3 = new User("User3", false);
        User adminUser = new User("Admin", true);

        addUserToSystem(user1);
        addUserToSystem(user2);
        addUserToSystem(user3);
        addUserToSystem(adminUser);

        Photograph photo1 = new Photograph("User1", IDgen);
        Photograph photo2 = new Photograph("User2", IDgen);

        addPhotoToSystem(photo1);
        addPhotoToSystem(photo2);



    }

    //A more comprehensive dummy storage generator, with comments
    public void populateDummyStorageWithComments() {
                //Reset - for test purposes
                users = new HashMap<String, User>();
                photographs = new HashMap<String, ArrayList<Photograph>>();
                IDgen = new IDGenerator();
        
                User user1 = new User("User1", false);
                User user2 = new User("User2", false);
                User user3 = new User("User3", false);
                User adminUser = new User("Admin", true);
        
                addUserToSystem(user1);
                addUserToSystem(user2);
                addUserToSystem(user3);
                addUserToSystem(adminUser);
        
                Photograph photo1 = new Photograph("User1", IDgen);
                Photograph photo2 = new Photograph("User2", IDgen);
        
                addPhotoToSystem(photo1);
                addPhotoToSystem(photo2);
        
                user1.postComment("Hello", photo1,this);
                Comment comment = user1.getComments().get(0);
                comment.downvote();
                user2.postComment("This is a reply", comment, this);
                Comment reply = user2.getComments().get(0);
                reply.upvote();
                reply.upvote();
 

    }

    public String turnToJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    } 



}