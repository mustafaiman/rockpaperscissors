package me.mkamil.rockpaperscissors.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mustafaiman on 22/10/15.
 */
public class GameServer {

    private final int hostPort;
    private final ServerSocket serverSocket;

    public GameServer(int hostPort) throws IOException {
        this.hostPort = hostPort;

        this.serverSocket = new ServerSocket(hostPort);
    }

    public void runServer() {
        try {
            Socket client = serverSocket.accept();
            new ClientThread(client).start();
        } catch (IOException e) {
            System.out.println("Lost connection to client");
        }

    }
}