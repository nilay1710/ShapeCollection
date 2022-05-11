package model;

import org.json.JSONObject;
import persistence.Writable;

// This class represents a shape with a side number and side length
public class Shape implements Writable {
    private int sideLength;
    private int sideNumber;

    // REQUIRES: sideLength>0 and sideNumber>0
    // EFFECT: Shape is give the sideLength and sideNumber
    public Shape(int sideLength,int sideNumber) throws SideNumberNotPossible {

        if (sideNumber <= 2) {
            throw new SideNumberNotPossible();
        } else {
            this.sideNumber = sideNumber;
            this.sideLength = sideLength;
        }
    }


    // EFFECT: Returns the sum of interior angles of the shape
    public int sumOfInteriorAngles() {
        return (sideNumber - 2) * 180;
    }

    // REQUIRES: n>0
    // MODIFIES: This
    // EFFECT: adds n to the sideNumber
    public void increaseSideNumber(int n) {
        sideNumber = sideNumber + n;

    }

    // REQUIRES: m>0
    // MODIFIES: This
    // EFFECT: adds m to the sideLength
    public void increaseSideLength(int m) {
        sideLength = sideLength + m;
    }

    // EFFECT: returns the sideLength
    public int getSideLength() {
        return sideLength;
    }

    // EFFECT: returns the sideNumber
    public int getSideNumber() {
        return sideNumber;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("side number", sideNumber);
        json.put("side length", sideLength);
        return json;
    }

    

}
