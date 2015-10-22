package me.mkamil.rockpaperscissors.message;

import me.mkamil.rockpaperscissors.entity.Shape;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by mustafaiman on 23/10/15.
 */
public class ResultMessage {
    private int clientWin;
    private int tie;
    private int serverWin;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    public ResultMessage() {

    }

    public ResultMessage(String messageString)
            throws Exception {
        String parts[] = messageString.split("\r\n");
        if( !parts[0].equals("RESULT")) {
            throw new Exception("Not a RESULT message");
        }
        for (int i = 1; i < parts.length - 3; i++) {
            try {
                shapes.add(Shape.valueOf(parts[i]));
            } catch (Exception e) {
                throw new Exception("Not a RESULT message");
            }
        }
        if(parts[parts.length-3].split("=")[0].equals("CLIENT")) {
            clientWin = Integer.parseInt(parts[parts.length-3].split("=")[1]);
        } else {
            throw new Exception("Not a RESULT message");
        }
        if(parts[parts.length-2].split("=")[0].equals("TIE")) {
            tie = Integer.parseInt(parts[parts.length-2].split("=")[1]);
        } else {
            throw new Exception("Not a RESULT message");
        }
        if(parts[parts.length-1].split("=")[0].equals("SERVER")) {
            serverWin = Integer.parseInt(parts[parts.length-1].split("=")[1]);
        } else {
            throw new Exception("Not a RESULT message");
        }
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public byte[] getProtocolMessage() {
        String messageString = "RESULT\r\n";
        for (Shape shape : shapes) {
            messageString = messageString + shape + "\r\n";
        }
        messageString = messageString
                + "CLIENT=" + clientWin + "\r\n"
                + "TIE=" + tie  + "\r\n"
                + "SERVER=" + serverWin + "\r\n"
                + "\r\n";
        try {
            return messageString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public int getClientWin() {
        return clientWin;
    }

    public void setClientWin(int clientWin) {
        this.clientWin = clientWin;
    }

    public int getTie() {
        return tie;
    }

    public void setTie(int tie) {
        this.tie = tie;
    }

    public int getServerWin() {
        return serverWin;
    }

    public void setServerWin(int serverWin) {
        this.serverWin = serverWin;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }
}
