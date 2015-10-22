package me.mkamil.rockpaperscissors;

import me.mkamil.rockpaperscissors.client.GameClient;
import me.mkamil.rockpaperscissors.server.GameServer;

/**
 * Created by mustafaiman on 22/10/15.
 */
public class App {

    public static void main(String[] args) {
        try {
            if (args[0].contains(".") || args[0].equals("localhost")) {
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
