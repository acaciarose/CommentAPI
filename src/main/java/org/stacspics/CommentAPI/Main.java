package org.stacspics.CommentAPI;

import java.io.IOException;

public class Main {

    public static void main(String[] args)  {
        Server s = new Server();
        s.hserver = s.startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", s.BASE_URI));

        
        CommentClient c = new CommentClient();
        try {
            c.makeSampleGETRequest();

        }
        catch (IOException e){
            e.printStackTrace();
            

        }
            

    }
}

