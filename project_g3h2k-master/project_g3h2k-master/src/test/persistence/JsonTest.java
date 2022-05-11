package persistence;

import model.Shape;


import static org.junit.jupiter.api.Assertions.assertEquals;

// Abstract class for Json tests
public class JsonTest {
    protected void checkShape(int sideNumber,int sideLength, Shape shape) {
        assertEquals(sideLength, shape.getSideLength());
        assertEquals(sideNumber, shape.getSideNumber());
    }
}
