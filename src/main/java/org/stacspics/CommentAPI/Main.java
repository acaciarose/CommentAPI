package org.stacspics.CommentAPI;

import java.io.IOException;

//Main class, mostly unused currently except for experimental/pre-test purposes
public class Main {

    public static void main(String[] args)  {
        Server s = new Server();
        s.startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", s.BASE_URI));

        

            

    }
}

