package model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// This is a collection (array list) of shapes
public class ShapeCollection implements Writable {
    private int angle;
    private ArrayList<Shape> shapeCollection;

    // EFFECT: creates a space collection with the specified angle
    public ShapeCollection(int angle) {
        this.angle = angle;
        shapeCollection = new ArrayList<>();

    }

    // MODIFIES: This
    // EFFECT: adds a shape to the collection
    public void addShape(Shape s) {
        shapeCollection.add(s);
    }


    // MODIFIES: This
    // EFFECT: inserts the shape at index i
    public void insertShape(Shape s,int i) {
        shapeCollection.add(i,s);
    }

    // EFFECT: returns the size of shape collection
    public int getSize() {
        return shapeCollection.size();
    }

    // MODIFIES: this
    // EFFECT: removes a shape from the collection
    public void removeShape(int i) {
        shapeCollection.removeIf(sh -> i == sh.getSideNumber());
    }



    // EFFECT: counts the number of shapes in the collection
    public int countShapes() {
        return shapeCollection.size();
    }

    // EFFECT: returns the shape with index i
    public Shape getShape(int i) {
        return shapeCollection.get(i);
    }


    // MODIFIES: this
    // EFFECT: changes the angle to n
    public void changeAngle(int n) {
        angle = n;
    }

    // EFFECT: produces true if the shape is found. otherwise false
    public boolean checkShape(int i, int j) {
        for (Shape sh: shapeCollection) {
            if ((i == sh.getSideNumber()) && (j == sh.getSideLength())) {
                return true;
            }
        }
        return false;
    }

    // EFFECT: returns the angle of the shape collection
    public int getAngle() {
        return angle;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Angle", angle);
        json.put("Shape Collection", shapesToJson());
        return json;
    }

    // EFFECTS: returns things in this shape collection as a JSON array
    private JSONArray shapesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Shape s : shapeCollection) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}





