package ui;


import model.Shape;
import model.ShapeCollection;
import model.SideNumberNotPossible;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.io.FileNotFoundException;
import java.io.IOException;

// Creates GUI for Shape collection
public class ShapeGUI extends JPanel
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private ShapeCollection shapeCollection;

    private String addString = "Add shape";
    private String deleteString = "Delete Shape";
    private String displayString = "Display Shape";
    private String loadString = "load shapes";
    private String saveString = "save shapes";
    private JLabel shapeNumberLabel;
    private JLabel shapeLengthLabel;
    private JButton deleteButton;
    private JButton loadButton;
    private JButton saveButton;
    private JTextField shapeNumber;
    private JTextField shapeLength;
    private JButton displayShape;

    // EFFECT: Constructor for ShapeGUI
    public ShapeGUI(ShapeCollection shapeCollection) {
        super(new BorderLayout());
        this.shapeCollection = shapeCollection;

        listModel = new DefaultListModel();

        //Create the list and put it in a scroll pane.
        JScrollPane listScrollPane = makeList();

        JButton addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        makeDeleteButton();
        makeLoadButton();
        makeSaveButton();
        makeDisplayShape();
        makeShapeNumber(addListener);

        shapeNumberLabel = new JLabel();
        shapeNumberLabel.setText("Shape number: ");

        makeShapeLength(addListener);

        shapeLengthLabel = new JLabel();
        shapeLengthLabel.setText("Shape length: ");

        makeJPanel(listScrollPane, addButton);
        addInitialElements(shapeCollection);


    }

    // MODIFIES: list model
    // EFFECTS: adds the initial elements in the shape collection to the list model
    private void addInitialElements(ShapeCollection shapeCollection) {
        for (int i = 0; i < shapeCollection.getSize(); i++) {
            Shape shape = shapeCollection.getShape(i);
            listModel.insertElementAt("Shape with shape number " + shape.getSideNumber() + " shape length "
                    + shape.getSideLength(), i);
        }
    }

    //EFFECTS: makes a "save button" which uses SaveListener
    private void makeSaveButton() {
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }

    // EFFECTS: makes a "display shape" button which uses PolygonListener
    private void makeDisplayShape() {
        displayShape = new JButton(displayString);
        displayShape.setActionCommand(displayString);
        displayShape.addActionListener(new PolygonListener());
    }

    // EFFECTS: makes a JList that uses listModel
    private JScrollPane makeList() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        return listScrollPane;
    }

    // EFFECTS: makes a JPanel that adds all the buttons and text fields
    private void makeJPanel(JScrollPane listScrollPane, JButton addButton) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(displayShape);
        buttonPane.add(loadButton);
        buttonPane.add(saveButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));

        buttonPane.add(shapeNumberLabel);
        buttonPane.add(shapeNumber);

        buttonPane.add(shapeLengthLabel);
        buttonPane.add(shapeLength);

        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // EFFECTS: makes a "delete Shape" button which uses DeleteListener
    private void makeDeleteButton() {
        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());
    }

    // EFFECTS: makes a "Load shape" button which uses LoadListener
    private void makeLoadButton() {
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
    }

    // EFFECTS: makes a text field for shape length to be entered
    private void makeShapeLength(AddListener addListener) {
        shapeLength = new JTextField(10);
        shapeLength.addActionListener(addListener);
        shapeLength.getDocument().addDocumentListener(addListener);
    }

    // EFFECTS: makes a text field for shape number to be entered
    private void makeShapeNumber(AddListener addListener) {
        shapeNumber = new JTextField(10);
        shapeNumber.addActionListener(addListener);
        shapeNumber.getDocument().addDocumentListener(addListener);
    }

    // loads the contents of the json file into the gui
    class LoadListener implements ActionListener {
        // EFFECTS: loads the shapes from the file onto the shape collection and displays them
        public void actionPerformed(ActionEvent e) {

            try {
                JsonReader jsonReader = new JsonReader("./data/ShapeCollection3.json");
                shapeCollection = jsonReader.read();
                listModel.removeAllElements();
                for (int i = 0; i < shapeCollection.getSize(); i++) {
                    Shape shape = shapeCollection.getShape(i);

                    listModel.insertElementAt("Shape with shape number " + shape.getSideNumber() + " shape length "
                            + shape.getSideLength(), i);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    // EFFECTS: Saves the contents of the shape collection to the json file
    class SaveListener implements ActionListener {

        // EFFECTS: Saves the shapes from the shape collection to the file
        public void actionPerformed(ActionEvent e) {

            JsonWriter jsonWriter = new JsonWriter("./data/ShapeCollection3.json");
            try {
                jsonWriter.open();
                jsonWriter.write(shapeCollection);
                jsonWriter.close();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }


        }
    }

    // MODIFIES: shape collection
    // EFFECTS: Deletes the selected shape from the shape collection
    class DeleteListener implements ActionListener {

        // EFFECTS: Deletes the shape that is chosen from the shape collection.
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);


            shapeCollection.removeShape(shapeCollection.getShape(index).getSideNumber());

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }


    // EFFECTS: Creates a polygon with the specified dimensions
    static class Polygon extends JPanel {
        private int sideLength;
        private int side;
        private int angle;

        // EFFECTS: Constructs a polygon with sideNumber, sideLength and angle
        public Polygon(int sideNumber, int sideLength, int angle) {
            this.sideLength = sideLength;
            this.side = sideNumber;
            this.angle = angle;

        }

        // EFFECTS: Draws a polygon with the sideNumber and sideLength
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            java.awt.Polygon p = new java.awt.Polygon();
            for (int i = 0; i < side; i++) {
                p.addPoint((int) (
                        sideLength + (sideLength) * Math.cos(i * 2 * Math.PI / side)), (int) (sideLength
                        + (sideLength) * Math.sin(
                        i * 2 * Math.PI / side)));
            }

            g.drawPolygon(p);

        }
    }

    // Displays the polygon in a new window
    class PolygonListener implements ActionListener {

        // EFFECTS: Creates a new JFrame and draws the polygon that the user chooses
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            Shape shape = shapeCollection.getShape(index);

            JFrame frame2 = new JFrame();

            frame2.setTitle("Polygon");
            frame2.setSize(500, 500);


            Container contentPane = frame2.getContentPane();
            contentPane.add(new Polygon(shape.getSideNumber(),shape.getSideLength(),shapeCollection.getAngle()));
            frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame2.setVisible(true);






        }
    }





    // Adds the shape to the shape collection
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // EFFECTS: constructs AddListener with button
        public AddListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: shape collection and listModel
        // EFFECTS: Adds and displays the shape that the user entered if it does not already exist
        public void actionPerformed(ActionEvent e) {
            String name = shapeNumber.getText();
            String name2 = shapeLength.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name,name2)) {
                Toolkit.getDefaultToolkit().beep();
                shapeNumber.requestFocusInWindow();
                shapeNumber.selectAll();
                return;
            }
            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {
                index++;
            }

            try {
                Shape shape = addNewShape(index);

                shapeCollection.insertShape(shape,index);
            } catch (SideNumberNotPossible sideNumberNotPossible) {
                creatingJFrame();


            }

            //Reset the text field.
            shapeNumberUpdate();

        }

        private Shape addNewShape(int index) throws SideNumberNotPossible {
            Shape shape = new Shape(Integer.parseInt(shapeLength.getText()),
                    Integer.parseInt(shapeNumber.getText()));
            listModel.insertElementAt("Shape with shape number " + shape.getSideNumber() + " shape length "
                    + shape.getSideLength(), index);
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
            return shape;
        }

        private void creatingJFrame() {
            JFrame frame2 = new JFrame();

            frame2.setTitle("SideNumberNotPossible");
            frame2.setSize(500, 100);


            Container contentPane = frame2.getContentPane();
            JLabel label = new JLabel("TRY AGAIN: SHAPE NUMBER 1 OR 2 NOT POSSIBLE");
            contentPane.add(label);
            frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame2.setVisible(true);
        }

        // EFFECTS: removes the text from the shape number and shape length fields
        private void shapeNumberUpdate() {
            shapeNumber.requestFocusInWindow();
            shapeNumber.setText("");
            shapeLength.requestFocusInWindow();
            shapeLength.setText("");
        }

        // EFFECTS: checks if the shape is already in the list
        protected boolean alreadyInList(String name, String name2) {
            return listModel.contains("Shape with shape number " + Integer.parseInt(name) + " shape length "
                    + Integer.parseInt(name2));
        }

        // EFFECTS: calls enableButton when the user enters text into the text fields
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // EFFECTS: calls handleEmptyTextField if there is not text
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // EFFECTS: enables the button if not enabled
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // EFFECTS: enables the button if not already enabled
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // EFFECTS: disables the button if there is not text in text field
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // EFFECTS: Changes the state of the delete button
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteButton.setEnabled(false);


            } else {
                //Selection, enable the fire button.
                deleteButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: Creates the graphical user interface with the shape collection
    public static void createAndShowGUI(ShapeCollection shapeCollection) {
        JFrame frame = new JFrame("Shape Collection");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JComponent newContentPane = new ShapeGUI(shapeCollection);

        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);


        //Display the window.
        frame.pack();
        frame.setVisible(true);




    }


}