import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mustafaiman on 22/10/15.
 */
public class GameServer {

    private final int hostPort;
    private final ServerSocket serverSocket;

    private int threadCount;

    public GameServer(int hostPort) throws IOException {
        this.hostPort = hostPort;

        this.serverSocket = new ServerSocket(hostPort);
    }

    public void runServer() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                threadCount++;
                new ClientThread(client, threadCount).start();
            } catch (IOException e) {
                System.out.println("Connection cannot be established.");
            }
        }

    }
}