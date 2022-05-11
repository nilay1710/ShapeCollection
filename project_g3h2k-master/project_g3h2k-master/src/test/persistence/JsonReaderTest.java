package persistence;

import model.Shape;
import model.ShapeCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

// Tests for JsonReader
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ShapeCollection wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyShapeCollection.json");
        try {
            ShapeCollection wr = reader.read();
            assertEquals(30, wr.getAngle());
            assertEquals(0, wr.countShapes());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralShapeCollection.json");
        try {
            ShapeCollection wr = reader.read();
            assertEquals(30, wr.getAngle());

            assertEquals(2, wr.countShapes());
            checkShape(10, 20, wr.getShape(0));
            checkShape(20,30, wr.getShape(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}