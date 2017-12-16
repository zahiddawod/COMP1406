import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class FloorBuilderView extends GridPane {
    private final String[] labelTexts = {"FLOOR LAYOUT", "SELECT/EDIT", "FLOOR SUMMARY"};
    private final String[] radioTexts = {"Walls", "Exits", "Room Tiles", "Select Room"};

    private GridPane         aPane;
    private Building      building;
    private Button[][]        grid;
    private Label[]         labels;
    private RadioButton[]   button;

    private Button          buildingOverview = new Button("Building Overview");
    private Button          roomColour = new Button();
    private TextField       textField;

    private ToggleGroup settingGroup = new ToggleGroup();

    private byte            currentFloorPlan = 0;
    private short           sizeOfMap;
    private byte            numOfExits = 0;

    public Building getBuilding() { return building; }
    public Button[][] getGrid() { return grid; }
    public RadioButton[] getRadioButtons() { return button; }
    public Button getRoomColourButton() { return roomColour; }
    public TextField getTextField() { return textField; }

    public byte getCurrentFloorPlan() { return currentFloorPlan; }
    public short getSizeOfMap() { return sizeOfMap; }
    public byte getNumOfExits() { return numOfExits; }

    public void setNumOfExits(int increment) { numOfExits += increment; }

    public FloorBuilderView(Building building) {
        this.building = building;
        setPadding(new Insets(5, 10,10,10));
        setMinSize(FloorBuilderApp.WIDTH, FloorBuilderApp.HEIGHT);
        setPrefSize(FloorBuilderApp.WIDTH, FloorBuilderApp.HEIGHT);
        aPane = new GridPane();

        roomColour.setDisable(true); // Disables the colour button
        textField = new TextField(this.building.getFloorPlan(currentFloorPlan).getName() + " with " + this.building.getFloorPlan(currentFloorPlan).getNumberOfRooms() + " rooms.");
        add(textField, 0, 3, 3, 1);
        textField.setEditable(false); // Disables the textfield from being editable
        textField.setFocusTraversable(false); // Unselects the textfield
        textField.setPrefSize(FloorBuilderApp.WIDTH, 30);

        // Adding Labels
        labels = new Label[labelTexts.length];
        for (int i = 0; i < labelTexts.length; i++) {
            labels[i] = new Label(labelTexts[i]);
            setMargin(labels[i], new Insets(5, 0, 5, 0));
        }
        add(labels[0], 0, 0);
        add(labels[1], 1, 0);
        add(labels[2], 0, 2);
        labels[1].setMinWidth(120);

        // Adding Map
        add(aPane, 0, 1);
        aPane.setMinSize(200, 220);
        sizeOfMap = (short) this.building.getFloorPlan(currentFloorPlan).size();
        grid = new Button[sizeOfMap][sizeOfMap];
        for (int r = 0; r < sizeOfMap; r++) {
            for (int c = 0; c < sizeOfMap; c++) {
                grid[c][r] = new Button();
                grid[c][r].setMinSize(aPane.getMinWidth()/20, aPane.getMinHeight()/20);
                grid[c][r].setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

                if (this.building.hasExitAt(r, c)) {
                    numOfExits ++;
                    grid[c][r].setStyle("-fx-base: RED");
                    grid[c][r].setText("Exit");
                } else if (this.building.getFloorPlan(currentFloorPlan).wallAt(r, c))
                    grid[c][r].setStyle("-fx-base: BLACK");
                else
                    grid[c][r].setStyle("-fx-base: WHITE");
                aPane.add(grid[c][r], c, r);
            }
        }

        // Adding Radio Buttons
        button = new RadioButton[radioTexts.length];
        for (int i = 0; i < radioTexts.length; i++) {
            button[i] = new RadioButton(radioTexts[i]);
            add(button[i], 1, 1);
            setValignment(button[i], VPos.TOP);
            setMargin(button[i], new Insets(10+ i*30, 0, 0, 15));
            button[i].setToggleGroup(settingGroup);
        }
        button[0].setSelected(true); // Default selection of radio buttons (Walls)

        // BuildingOverview button
        add(buildingOverview, 1, 1, 2, 1);
        setValignment(buildingOverview, VPos.TOP);
        buildingOverview.setMinSize(140, 30);
        setMargin(buildingOverview, new Insets(140, 0, 0, 10));

        roomColour.setMinSize(30, 30);
        add(roomColour, 2, 1);
        setValignment(roomColour, VPos.TOP);
        roomColour.setStyle("-fx-base: " + FloorBuilderApp.ROOM_COLORS[FloorBuilderApp.getCurrentColour()]);
        setMargin(roomColour, new Insets(60, 0, 0, 0));
    }
}
