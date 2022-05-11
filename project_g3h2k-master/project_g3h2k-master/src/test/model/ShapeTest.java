package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
// Tests for the shapeCollection
class ShapeTest {
    ShapeCollection collection;
    Shape shape1;
    Shape shape2;
    Shape shape3;
    Shape shape4;

    @BeforeEach
    public void setup() throws SideNumberNotPossible {
        collection = new ShapeCollection(0);
        shape1 = new Shape(10,3);
        shape2 = new Shape(10,4);
        shape3 = new Shape(20,5);

    }

    @Test
    public void testConstructors() {
        try {
            Shape shape4 = new Shape(100,22);

        } catch (SideNumberNotPossible sideNumberNotPossible) {
            fail("this is unexpected");
        }

        try {
            Shape shape5 = new Shape(200,2);
            fail("this is unexpected");
        } catch (SideNumberNotPossible sideNumberNotPossible) {

        }
        assertEquals(0,collection.getAngle());
        assertEquals(10,shape1.getSideLength());
        assertEquals(3,shape1.getSideNumber());
    }

    @Test
    public void testAddAndRemoveShape() {
        assertEquals(0,collection.countShapes());
        collection.addShape(shape2);
        assertEquals(1,collection.countShapes());
        collection.addShape(shape3);
        assertEquals(2,collection.countShapes());
        collection.removeShape(shape2.getSideNumber());
        assertEquals(1,collection.countShapes());
    }

    @Test
    public void testCountShapes() {
        collection.addShape(shape2);
        assertEquals(1,collection.countShapes());
    }

    @Test
    public void testGetShape() {
        collection.addShape(shape2);
        collection.addShape(shape3);
        assertEquals(20,collection.getShape(1).getSideLength());
        assertEquals(10,collection.getShape(0).getSideLength());
        assertEquals(4,collection.getShape(0).getSideNumber());
    }

    @Test
    public void testChangeAngle() {
        assertEquals(0,collection.getAngle());
        collection.changeAngle(10);
        assertEquals(10,collection.getAngle());
    }

    @Test
    public void testInsertShape() {
        assertEquals(0,collection.countShapes());
        collection.insertShape(shape1,0);
        assertEquals(1,collection.countShapes());
        assertEquals(shape1,collection.getShape(0));
        collection.insertShape(shape2,0);
        assertEquals(2,collection.countShapes());
        assertEquals(shape2,collection.getShape(0));
        assertEquals(shape1,collection.getShape(1));
    }

    @Test
    public void testCheckShape() {
        collection.addShape(shape1);
        assertTrue(collection.checkShape(shape1.getSideNumber(),shape1.getSideLength()));
        assertFalse(collection.checkShape(shape2.getSideNumber(),shape2.getSideLength()));
        collection.addShape(shape2);
        collection.addShape(shape3);
        assertTrue(collection.checkShape(shape3.getSideNumber(),shape3.getSideLength()));
        collection.removeShape(shape2.getSideNumber());
        assertFalse(collection.checkShape(shape2.getSideNumber(),shape2.getSideLength()));
    }

    @Test
    public void testGetAngle() {
        assertEquals(0,collection.getAngle());
        collection.changeAngle(40);
        assertEquals(40,collection.getAngle());
    }









}