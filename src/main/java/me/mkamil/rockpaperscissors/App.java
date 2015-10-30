package me.mkamil.rockpaperscissors;

import me.mkamil.rockpaperscissors.client.GameClient;
import me.mkamil.rockpaperscissors.server.GameServer;

/**
 * Created by mustafaiman on 22/10/15.
 */
public class App {

    public static void main(String[] args) {
        try {
            if(args.length == 0) {
                System.out.println("Wrong number of arguments. Usage:\n"
                        + "\t[server] -- port-number\n"
                        + "\t[client] -- host-ip port-number");
            } else if ( args.length > 1) {
                GameClient client = new GameClient(args[0], Integer.parseInt(args[1]));
                client.run();
            } else {
                GameServer server = new GameServer(Integer.parseInt(args[0]));
                server.runServer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
