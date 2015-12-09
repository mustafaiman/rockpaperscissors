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
    private final int clientNum;

    private final Random random = new Random();

    private int clientWin;
    private int tie;
    private int serverWin;

    public ClientThread(Socket socket, int clientNum)
            throws IOException {
        this.socket = socket;
        this.socket.setKeepAlive(true);

        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.writer = new DataOutputStream(socket.getOutputStream());
        this.clientNum = clientNum;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ShapeMessage shapeMessage = new ShapeMessage(readAShapeMessage());
                printWithClientNum("Client has sent " + shapeMessage.size() + " shapes...");
                printWithClientNum("Shapes are chosen...");
                printWithClientNum("Results are as follows:");
                ArrayList<Shape> clientShapes = shapeMessage.asArrayList();
                int roundClientWin = 0;
                int roundTie = 0;
                int roundServerWin = 0;
                ResultMessage resultMessage = new ResultMessage();
                for (int i = 0; i < clientShapes.size(); i++) {
                    Shape serverShape = Shape.values()[random.nextInt(Shape.values().length)];
                    GameResult result = serverShape.play(clientShapes.get(i));
                    switch (result) {
                        case TIE:
                            roundTie++;
                            break;
                        case WIN:
                            roundServerWin++;
                            break;
                        case LOSS:
                            roundClientWin++;
                            break;
                    }
                    printWithClientNum(
                            "Round-1: client: " + clientShapes.get(i) + ", server : " + serverShape + " " + explainingMessage(
                                    result));
                    resultMessage.addShape(serverShape);
                }
                clientWin += roundClientWin;
                tie += roundTie;
                serverWin += roundServerWin;
                resultMessage.setClientWin(roundClientWin);
                resultMessage.setServerWin(roundServerWin);
                resultMessage.setTie(roundTie);

                writer.write(resultMessage.getProtocolMessage());
            } catch (InvalidMessageException | IOException e) {
                printWithClientNum("Terminate the connection...");
                printWithClientNum("Client: " + clientWin + " Tie: " + tie + " Server: " + serverWin);
                System.out.println();
                closeConnection();
                return;
            } catch (Exception e) {
                printWithClientNum("Other exception");
            }
            System.out.println();
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
            if(size == -1)
                throw new IOException();
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

    private void printWithClientNum(String str) {
        System.out.println("THREAD-" + clientNum + ": " + str);
    }
}
