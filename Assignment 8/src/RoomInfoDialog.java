import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class RoomInfoDialog extends Dialog {
    public RoomInfoDialog(Building model, int currentFloor, int r, int c) {
        int currentColor = model.getFloorPlan(currentFloor).roomAt(r, c).getColorIndex();
        setTitle("Room Details");
        setHeaderText(null);

        ButtonType okButtonType = new ButtonType("OK",
                ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButtonType,
                ButtonType.CANCEL);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextField occupant = new TextField();
        TextField position = new TextField();
        TextField number = new TextField();

        occupant.setPrefWidth(300);
        position.setPrefWidth(300);
        number.setPrefWidth(150);

        // Check if occupant information for this room exists
        if (model.getFloorPlan(currentFloor).roomWithColor(currentColor).getOccupant() == null)
            occupant.setPromptText("Person who occupies this room");
        else
            occupant.setText(model.getFloorPlan(currentFloor).roomWithColor(currentColor).getOccupant());
        grid.add(new Label("Occupant:"), 0, 0);
        grid.add(occupant, 1, 0, 2, 1);

        // Check if position of occupant information for this room exists
        if (model.getFloorPlan(currentFloor).roomWithColor(currentColor).getPosition() == null)
            position.setPromptText("Job position/title of this person");
        else
            position.setText(model.getFloorPlan(currentFloor).roomWithColor(currentColor).getPosition());
        grid.add(new Label("Position:"), 0, 1);
        grid.add(position, 1, 1, 2, 1);

        // Check if number information for this room exists
        if (model.getFloorPlan(currentFloor).roomWithColor(currentColor).getNumber() == null)
            number.setPromptText("The room number");
        else
            number.setText(model.getFloorPlan(currentFloor).roomWithColor(currentColor).getNumber());
        grid.add(new Label("Number:"), 0, 2);
        grid.add(number, 1, 2);

        Button colourButton = new Button();
        colourButton.setStyle("-fx-base: " + FloorBuilderView.ROOM_COLORS[currentColor]);
        colourButton.setFocusTraversable(false);
        colourButton.setPrefWidth(150);
        grid.add(colourButton, 2, 2);

        TextField floor = new TextField(model.getFloorPlan(currentFloor).getName());
        floor.setEditable(false);
        floor.setPrefWidth(300);
        grid.add(new Label("Floor:"), 0, 3);
        grid.add(floor, 1, 3, 2, 1);

        TextField size = new TextField(model.getFloorPlan(currentFloor).roomWithColor(currentColor).getNumberOfTiles() + " Sq. Ft.");
        size.setEditable(false);
        size.setPrefWidth(300);
        grid.add(new Label("Size:"), 0, 4);
        grid.add(size, 1, 4, 2, 1);


        getDialogPane().setContent(grid);

        Optional<ButtonType> result = showAndWait();
        if (result.get() == okButtonType) {
            if (!occupant.getText().equals("")) // Checks if any changes were made in the occupant textfield.
                model.getFloorPlan(currentFloor).roomWithColor(currentColor).setOccupant(occupant.getText());
            else
                model.getFloorPlan(currentFloor).roomWithColor(currentColor).setOccupant(null);
            if (!position.getText().equals("")) // Checks if any changes were made in the position textfield.
                model.getFloorPlan(currentFloor).roomWithColor(currentColor).setPosition(position.getText());
            else
                model.getFloorPlan(currentFloor).roomWithColor(currentColor).setPosition(null);
            if (!number.getText().equals("")) // Checks if any changes were made in the number textfield.
                model.getFloorPlan(currentFloor).roomWithColor(currentColor).setNumber(number.getText());
            else
                model.getFloorPlan(currentFloor).roomWithColor(currentColor).setNumber(null);
        }
    }
}
