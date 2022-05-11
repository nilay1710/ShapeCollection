package persistence;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Shape;
import model.ShapeCollection;
import model.SideNumberNotPossible;
import org.json.*;

// Citation: Sampled from JsonSerializationDemo
// Represents a reader that reads shape collection from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads shape collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ShapeCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseShapeCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses shape collection from JSON object and returns it
    private ShapeCollection parseShapeCollection(JSONObject jsonObject) {
        int angle = jsonObject.getInt("Angle");
        ShapeCollection wr = new ShapeCollection(angle);
        addShapes(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses shapes from JSON object and adds them to shape collection
    private void addShapes(ShapeCollection wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Shape Collection");
        for (Object json : jsonArray) {
            JSONObject nextShape = (JSONObject) json;
            addShape(wr, nextShape);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses shape from JSON object and adds it to shape collection
    private void addShape(ShapeCollection wr, JSONObject jsonObject) {
        int sideNumber = jsonObject.getInt("side number");
        int sideLength = jsonObject.getInt("side length");
        Shape shape = null;
        try {
            shape = new Shape(sideLength,sideNumber);
        } catch (SideNumberNotPossible sideNumberNotPossible) {
            sideNumberNotPossible.printStackTrace();
        }

        wr.addShape(shape);
    }
}
