package org.stacspics.CommentAPI;

//Stores list of all users and photographs on the system
//as well as the ID Generator

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


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
        comments = new HashMap<Integer, Comment>();

    }

    //Add photo to photos list
    public void addPhotoToSystem(Photograph photo) throws IOException {
        ArrayList<Photograph> newphotos = getPhotosFromUserName(photo.getUser());

        if (newphotos == null) {
            newphotos = new ArrayList<Photograph>();
        }

        newphotos.add(photo);

        photographs.put(photo.getUser(), newphotos  );
        writeToStorage("storage.json");

    }

    //This currently will overwrite users 
    //Not a huge problem now since can't create users
    public void addUserToSystem(User user) throws IOException {
        users.put(user.getName(), user);
        writeToStorage("storage.json");
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

    public Photograph getPhotoFromID(int photoID) {
        for (ArrayList<Photograph> userPhotos : photographs.values()) {
            for (Photograph photo : userPhotos) {
            if (photo.getID() == photoID) {
                return photo;
            }
        }
        }

        return null;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public HashMap<String, ArrayList<Photograph>> getPhotos() {
        return photographs;
    }

    public HashMap<Integer, Comment> getComments() {
        return comments;
    }

    public void addCommentToSystem(Comment comment) throws IOException {
        comments.put(comment.getID(), comment);
        writeToStorage("storage.json");
    }

    public Comment getCommentByID(int ID) {
        return comments.get(ID);
    }

    //Populate system storage with dummy users and photos
    //for demonstration purposes
    public boolean populateDummyStorage() {
        //Reset - for test purposes

        try {
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

        return true;
    }
    catch (IOException e) {
        return false;
    }


    }

    //A more comprehensive dummy storage generator, with comments
    public boolean populateDummyStorageWithComments() {
        try {
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
                comment.downvote(this);
                user2.postComment("This is a reply", comment, this);
                Comment reply = user2.getComments().get(0);
                reply.upvote(this);
                reply.upvote(this);

                return true;
        }
        catch (IOException e) {
            return false;
        }
 

    }

    public String turnToJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    } 

    public void writeToStorage(String filename)  throws IOException {
        File file = new File(filename);
        FileWriter f = new FileWriter(file);
        f.write(this.turnToJsonString());
        f.close();
    }

    public SystemStorage readFromStorage(String filename) {
        System.out.println("hello im trying");
        try{
        File file = new File(filename);
        FileReader f = new FileReader(file);
        Gson gson = new Gson();
        String jsonstring = new String(Files.readAllBytes(Paths.get(filename)));
        SystemStorage read =  gson.fromJson(jsonstring, SystemStorage.class);
        return read;
        }
        catch (IOException e) {
            return null;
        }

    }



}