import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class DirectoryDialog extends Dialog {
    private Building        model;

    public DirectoryDialog(Building model) {
        this.model = model;
        setTitle("Directory Listing");
        setHeaderText(null);

        getDialogPane().getButtonTypes().addAll(new ButtonType("OK",
                ButtonBar.ButtonData.OK_DONE));

        GridPane aPane = new GridPane();
        aPane.setHgap(10);
        aPane.setVgap(10);
        aPane.setPadding(new Insets(10, 10, 10, 10));

        ListView<String> listView = new ListView<>();
        listView.setPrefSize(380, 200);
        ObservableList<String> list = FXCollections.observableArrayList();
        Button Search = new Button("Search");
        Search.setPrefWidth(380);

        aPane.add(listView, 0, 0);
        aPane.add(Search, 0, 1);

        for (int floor = 0; floor < model.getNumFloors(); floor++ ) {
            for (int room = 0; room < model.getFloorPlan(floor).getNumberOfRooms(); room++) {
                Room currentRoom = model.getFloorPlan(floor).getRooms()[room];
                list.add(currentRoom.getNumber() + " - " + currentRoom.getOccupant() + " (" + currentRoom.getPosition() + ")");
            }
        }
        listView.setItems(list);

        Search.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                TextInputDialog searchPerson = new TextInputDialog();
                searchPerson.setTitle("Input Required");
                searchPerson.setContentText("Please enter the full name of the person that you are searching for:");
                searchPerson.setHeaderText(null);
                Optional<String> result = searchPerson.showAndWait();
                if (result.isPresent())
                    searchForPerson(result);
            }
        });

        getDialogPane().setContent(aPane);
    }

    public boolean searchForPerson(Optional<String> result) {
        for (int floor = 0; floor < model.getNumFloors(); floor++ ) {
            for (int room = 0; room < model.getFloorPlan(floor).getNumberOfRooms(); room++) {
                Room currentRoom = model.getFloorPlan(floor).getRooms()[room];
                if (currentRoom.getOccupant().equals(result.get())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search Results");
                    alert.setContentText(result.get() + " is our " + currentRoom.getPosition()
                            + " and can be located on the " + model.getFloorPlan(floor).getName()
                            + " in room " + currentRoom.getNumber());
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    return true;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Results");
        alert.setContentText("That name does not match anyone in our records, please try again.");
        alert.setHeaderText(null);
        alert.showAndWait();
        return false;
    }
}
