package persistence;

import model.Shape;

import model.ShapeCollection;
import model.SideNumberNotPossible;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

// Tests for JsonWriter
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ShapeCollection wr = new ShapeCollection(30);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ShapeCollection wr = new ShapeCollection(30);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyShapeCollection.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyShapeCollection.json");
            wr = reader.read();
            assertEquals(30,wr.getAngle());
            assertEquals(0, wr.countShapes());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ShapeCollection wr = new ShapeCollection(30);
            wr.addShape(new Shape(10, 20));
            wr.addShape(new Shape(20,30));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralShapeCollection.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralShapeCollection.json");
            wr = reader.read();
            assertEquals(30, wr.getAngle());

            assertEquals(2, wr.countShapes());
            checkShape(20, 10, wr.getShape(0));
            checkShape(30,20, wr.getShape(1));

        } catch (IOException | SideNumberNotPossible e) {
            fail("Exception should not have been thrown");
        }
    }
}