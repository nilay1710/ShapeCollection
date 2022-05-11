package ui;


import model.Shape;
import model.ShapeCollection;
import model.SideNumberNotPossible;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Used the tellerApp interface as source
// It is the user interface of Shape collection application
public class ShapeCollectionApp {
    private static final String JSON_STORE = "./data/ShapeCollection3.json";
    private ShapeCollection shapeCollection;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;



    // EFFECTS: runs the Shape Collection application
    public ShapeCollectionApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runShapeCollectionApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runShapeCollectionApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            doAddShape();
        } else if (command.equals("w")) {
            doRemoveShape();
        } else if (command.equals("t")) {
            doCountShape();
        } else if (command.equals("c")) {
            doCheckShape();
        } else if (command.equals("a")) {
            doChangeAngle();
        } else if (command.equals("x")) {
            doDisplayShapes();
        } else if (command.equals("l")) {
            loadWorkRoom();
        } else if (command.equals("s")) {
            saveShapeCollection();
        } else if (command.equals("g")) {
            ShapeGUI.createAndShowGUI(shapeCollection);
        }  else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the Shape Collection
    private void init() {
        shapeCollection = new ShapeCollection(0);
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> Add Shape");
        System.out.println("\tw -> Remove Shape");
        System.out.println("\tt -> Count Shapes");
        System.out.println("\tc -> Check Shape");
        System.out.println("\ta -> Change Angle");
        System.out.println("\tx -> Display Shapes");
        System.out.println("\tl -> load shape collection from file");
        System.out.println("\ts -> Save shape collection to file");
        System.out.println("\tg -> GUI");
        System.out.println("\tq -> quit");
    }



    // EFFECTS: counts the shapes in the shape collection
    private void doCountShape() {
        System.out.print("the number of shapes are " + shapeCollection.countShapes());

    }

    // EFFECTS: checks if the shape with the specified side number is there in the collection
    //          if there produces true, otherwise false
    private void doCheckShape() {
        System.out.print("Enter the sideNumber of the shape you want to check");
        int sideNumber = input.nextInt();
        System.out.print("Enter the sideLength of the shape you want to check");
        int sideLength = input.nextInt();
        System.out.print("the shape is: " + shapeCollection.checkShape(sideNumber,sideLength));
    }


    // MODIFIES: this
    // EFFECTS: changes the angle of the shape collection
    private void doChangeAngle() {
        System.out.print("the current angle of the shapes is " + shapeCollection.getAngle());
        System.out.print(" enter the angle you want to change it to");
        int angle = input.nextInt();
        shapeCollection.changeAngle(angle);

    }

    // MODIFIES: this
    // EFFECTS: adds a new shape to the collection
    private void doAddShape() {

        System.out.print("Enter the side number");
        int sideNumber = input.nextInt();

        System.out.print("Enter the side length");

        int sideLength = input.nextInt();
        try {
            Shape s1 = new Shape(sideLength,sideNumber);
            if (!shapeCollection.checkShape(sideNumber,sideLength)) {
                shapeCollection.addShape(s1);
                System.out.println("the shape has been added \n");
            } else {
                System.out.println("this shape already exists");

            }
        } catch (SideNumberNotPossible sideNumberNotPossible) {
            System.out.println("TRY AGAIN: SHAPE WITH 1 OR 2 SIDE NUMBER NOT ALLOWED");
        }




    }

    // MODIFIES: this
    // EFFECTS: removes shape with the specified side number
    private void doRemoveShape() {

        System.out.print("Enter number of sides of shape you want to remove");

        int sideNumber = input.nextInt();
        shapeCollection.removeShape(sideNumber);

        System.out.println("Removed all shapes with that side number \n");

    }

    // EFFECTS: displays the shape number and shape length of all the shapes
    public void doDisplayShapes() {
        System.out.print("here are all the shapes: \n");
        for (int i = 0; i < shapeCollection.countShapes(); i++) {
            System.out.print("\n Shape with shape number: " + shapeCollection.getShape(i).getSideNumber()
                    +
                    " and shape length: " + shapeCollection.getShape(i).getSideLength());
        }
    }

    // EFFECTS: saves the shape collection to file
    private void saveShapeCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(shapeCollection);
            jsonWriter.close();
            System.out.println("Saved " + "shape collection" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: loads shape collection from file
    private void loadWorkRoom() {
        try {
            shapeCollection = jsonReader.read();
            System.out.println("Loaded Shape collection" + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }









}


