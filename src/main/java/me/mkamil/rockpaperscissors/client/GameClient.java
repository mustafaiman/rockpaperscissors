package me.mkamil.rockpaperscissors.client;

import me.mkamil.rockpaperscissors.message.ResultMessage;
import me.mkamil.rockpaperscissors.entity.Shape;
import me.mkamil.rockpaperscissors.message.ShapeMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by mustafaiman on 22/10/15.
 */
public class GameClient {

    private static final int MAX_MESSAGE_LENGTH = 1000;
    private final String hostAddress;
    private final int hostPort;

    private final Socket socket;
    private final BufferedReader reader;
    private final DataOutputStream writer;

    private final Scanner scanner;

    private int waitinNumberOfRounds;

    GameClientState state = GameClientState.START;

    public GameClient(String hostAddress, int hostPort)
            throws IOException {
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;

        this.socket = new Socket(hostAddress, hostPort);
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.writer = new DataOutputStream(socket.getOutputStream());

        scanner = new Scanner(System.in);
    }

    public void run() {
        while(state != GameClientState.EXIT) {
            if(state == GameClientState.START) {
                handleStateStart();
            } else if(state == GameClientState.FINISH) {
                handleStateFinish();
            } else if(state == GameClientState.INPUT) {
                handleStateInput();
            }
        }
    }

    private void handleStateStart() {
        System.out.println("Enter the number of rounds (Press Q to quit): ");
        String inp = scanner.next();
        if("Q".equals(inp) || "q".equals(inp)) {
            state = GameClientState.FINISH;
        } else {
            waitinNumberOfRounds = Integer.parseInt(inp);
            state = GameClientState.INPUT;
        }
    }

    private void handleStateFinish() {
        try {
            writer.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        state = GameClientState.EXIT;
    }

    private void handleStateInput() {
        ShapeMessage message = new ShapeMessage();
        for(int i=0; i<waitinNumberOfRounds; i++) {
            String s;
            Shape shape = null;
            do {
                System.out.println("Round-" + (i+1) + ":");
                s = scanner.next();
                try {
                    shape = Shape.valueOf(s);
                } catch (Exception e) {
                    System.out.println("Invalid shape. Please try again.");
                }
            } while (shape == null);
            message.addShape(shape);
        }
        try {
            writer.write(message.getProtocolMessage());
            ResultMessage resultMessage = new ResultMessage(readAResultMessage());
            ArrayList<Shape> clientShapes = message.asArrayList();
            ArrayList<Shape> serverShapes = resultMessage.getShapes();
            System.out.println("Result:");
            for (int i=0; i<clientShapes.size(); i++) {
                int res = clientShapes.get(i).play(serverShapes.get(i));
                String resultString = "";
                if(res == 0)
                    resultString = "(tie)";
                else if(res == 1)
                    resultString = "(client wins)";
                else
                    resultString = "(server wins)";
                System.out.println("Round-" + (i+1) + ": server chooses " + serverShapes.get(i) + " " + resultString);
            }
            System.out.println("Client: " + resultMessage.getClientWin());
            System.out.println("Tie: " + resultMessage.getTie());
            System.out.println("Server: " + resultMessage.getServerWin());

        } catch (IOException e) {
            e.printStackTrace();
            state = GameClientState.FINISH;
        } catch (Exception e) {
            e.printStackTrace();
        }
        state = GameClientState.START;
    }

    private String readAResultMessage()
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
}
