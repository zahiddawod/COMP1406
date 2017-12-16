import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BuildingDialog extends Dialog {
    public BuildingDialog(Building model) {
        setTitle("Building Overview");
        setHeaderText(null);

        getDialogPane().getButtonTypes().addAll(new ButtonType("OK",
                ButtonBar.ButtonData.OK_DONE));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label labels[] = {new Label("Num Floors"),
                new Label("Num Exits:"),
                new Label("Num Rooms:"),
                new Label("Total Size:")};
        TextField textField[] = new TextField[labels.length];

        for (int i = 0; i < textField.length; i++)
            textField[i] = new TextField();

        for (int i = 0; i < labels.length; i++) {
            grid.add(labels[i], 0, i);
            grid.add(textField[i], 1, i);
            textField[i].setEditable(false);
            textField[i].setPrefWidth(80);
        }

        int numOfRooms = 0;
        int totalSize = 0;

        textField[0].setText(model.getNumFloors() + "");
        textField[1].setText(model.getNumExits() + "");

        for (int i = 0; i < model.getNumFloors(); i++) // All rooms in entire building
            numOfRooms += model.getFloorPlan(i).getNumberOfRooms();
        textField[2].setText(numOfRooms + "");

        for (int floor = 0; floor < model.getNumFloors(); floor++) // All tiles excluding walls and exits
            for (int col = 0; col < model.getFloorPlan(floor).size(); col++)
                for (int row = 0; row < model.getFloorPlan(floor).size(); row++)
                    if (!model.getFloorPlan(floor).wallAt(row, col))
                        totalSize ++;
        textField[3].setText(totalSize + " Sq. Ft.");

        Button directoryListing = new Button("Directory Listing");
        directoryListing.setPrefWidth(160);
        grid.add(directoryListing, 0, labels.length, 2, 1);

        getDialogPane().setContent(grid);

        directoryListing.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                DirectoryDialog directoryDialog = new DirectoryDialog(model);
                directoryDialog.showAndWait();
            }
        });
    }
}
