import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mustafaiman on 22/10/15.
 */
public class ClientThread extends Thread {
    private static final int MAX_MESSAGE_LENGTH = 1000;

    private final Socket socket;
    private final BufferedReader reader;
    private final DataOutputStream writer;

    private final Random random = new Random();

    public ClientThread(Socket socket)
            throws IOException {
        this.socket = socket;
        this.socket.setKeepAlive(true);

        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.writer = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                ShapeMessage shapeMessage = new ShapeMessage(readAShapeMessage());
                System.out.println("Client has sent " + shapeMessage.size() + " shapes...");
                System.out.println("Shapes are chosen...");
                System.out.println("Results are as follows:");
                ArrayList<Shape> clientShapes = shapeMessage.asArrayList();
                int clientWin = 0;
                int tie = 0;
                int serverWin = 0;
                ResultMessage resultMessage = new ResultMessage();
                for (int i = 0; i < clientShapes.size(); i++) {
                    Shape serverShape = Shape.values()[random.nextInt(Shape.values().length)];
                    GameResult result = serverShape.play(clientShapes.get(i));
                    switch (result) {
                        case TIE:
                            tie++;
                            break;
                        case WIN:
                            serverWin++;
                            break;
                        case LOSS:
                            clientWin++;
                            break;
                    }
                    System.out.println("Round-1: client: " + clientShapes.get(i)
                            + ", server : " + serverShape
                            + " " + explainingMessage(result));
                    resultMessage.addShape(serverShape);
                }
                resultMessage.setClientWin(clientWin);
                resultMessage.setServerWin(serverWin);
                resultMessage.setTie(tie);

                writer.write(resultMessage.getProtocolMessage());
            } catch (InvalidMessageException | IOException e) {
                e.printStackTrace();
                System.out.println("Terminate the connection...");
                closeConnection();
                return;
            }
        }
    }

    private String explainingMessage(GameResult gameResult) {
        if (gameResult.equals(GameResult.LOSS)) {
            return "(client wins)";
        } else if(gameResult.equals(GameResult.TIE)) {
            return "(tie)";
        } else {
            return "(server wins)";
        }
    }

    private String readAShapeMessage()
            throws IOException {
        char buffer[] = new char[MAX_MESSAGE_LENGTH];
        String messageString = "";
        boolean end = false;
        while (!end) {
            int size = reader.read(buffer);
            for (int i = 0; i < size; i++) {
                messageString = messageString + buffer[i];
            }
            if(messageString.length() > 3 && messageString.endsWith("\r\n\r\n")) {
                return messageString;
            }
        }
        return "";
    }

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
