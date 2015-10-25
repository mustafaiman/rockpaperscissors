package me.mkamil.rockpaperscissors.message;

import me.mkamil.rockpaperscissors.entity.Shape;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by mustafaiman on 22/10/15.
 */
public class ShapeMessage {

    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    public ShapeMessage() {
    }

    public ShapeMessage(String message) throws InvalidMessageException{
        String parts[] = message.split("\r\n");
        if( !parts[0].equals("SHAPE") ) {
            throw new InvalidMessageException("Expected SHAPE");
        }
        for (int i = 1; i < parts.length; i++) {
            try {
                shapes.add(Shape.valueOf(parts[i]));
            } catch (Exception e) {
                throw new InvalidMessageException("Expected SHAPE");
            }
        }
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public byte[] getProtocolMessage() {
        String s = "SHAPE\r\n";
        for (Shape shape : shapes) {
            s = s + shape + "\r\n";
        }
        s = s + "\r\n";
        try {
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public int size() {
        return shapes.size();
    }

    public ArrayList<Shape> asArrayList() {
        return shapes;
    }
}
