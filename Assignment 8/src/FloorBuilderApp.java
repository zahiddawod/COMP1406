import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static java.awt.image.ImageObserver.WIDTH;

public class FloorBuilderApp extends Application  {
    private FloorBuilderView   view;
    private Building           model;
    private int                currentFloor;    // Index of the floor being displayed
    private int                currentColor;    // Index of the current room color

    private MenuItem Floors[];

    public void start(Stage primaryStage) {
        model = Building.example();
        currentFloor = 0;
        currentColor = 0;

        VBox aPane = new VBox();
        view = new FloorBuilderView(model);
        view.setPrefWidth(Integer.MAX_VALUE);
        view.setPrefHeight(Integer.MAX_VALUE);

        // Menu Bar
        Menu selectFloor = new Menu("Select Floor");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(selectFloor);
        view.add(menuBar, 0, 0, 3, 1);

        Floors = new MenuItem[model.getNumFloors()];
        for (int i = 0; i < model.getNumFloors(); i++) {
            Floors[(model.getNumFloors()-1) - i] = new MenuItem(model.getFloorPlan((model.getNumFloors()-1) - i).getName());
            if (Floors[(model.getNumFloors()-1) - i].getText() != "Basement")
                selectFloor.getItems().add(Floors[(model.getNumFloors()-1) - i]);
        }
        selectFloor.getItems().addAll(new SeparatorMenuItem(), Floors[model.getNumFloors()-1]);

        menuBar.setPrefSize(WIDTH, 20);

        aPane.getChildren().add(view);
        primaryStage.setTitle("Floor Plan Builder");
        primaryStage.setScene(new Scene(aPane, 370,320));
        primaryStage.show();

        // Menu Bar event handlers
        for (int i = 0; i < model.getNumFloors(); i++)
            Floors[i].setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) { handleFloorSelection(event); }});

        // Plug in the floor panel event handlers:
        for (int r=0; r<model.getFloorPlan(0).size(); r++) {
            for (int c=0; c<model.getFloorPlan(0).size(); c++) {
                view.getFloorTileButton(r, c).setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        handleTileSelection(event);
                    }});
            }
        }

        // Plug in the radioButton event handlers
        view.getEditWallsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});
        view.getSelectExitsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});
        view.getEditRoomsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});
        view.getDefineRoomsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});

        // Plug in the office color button
        view.getRoomColorButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                currentColor = (currentColor + 1)%view.ROOM_COLORS.length;
                view.update(currentFloor, currentColor);
            }});

        // Building Dialog (Building Overview event handler)
        view.getBuildingOverviewButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                BuildingDialog buildingDialog = new BuildingDialog(model);
                buildingDialog.showAndWait();
            }});

        view.update(currentFloor, currentColor);
    }

    private void handleFloorSelection(ActionEvent e) {
        for (int i = 0; i < model.getFloorPlans().length; i++) {
            if (e.getSource() == Floors[i]) {
                currentFloor = i;
                view.update(currentFloor, currentColor);
            }
        }
    }

    // Handle a Floor Tile Selection
    private void handleTileSelection(ActionEvent e) {
        // Determine which row and column was selected
        int r=0, c=0;
        OUTER:
        for (r=0; r<model.getFloorPlan(0).size(); r++) {
            for (c=0; c<model.getFloorPlan(0).size(); c++) {
                if (e.getSource() == view.getFloorTileButton(r, c))
                    break OUTER;
            }
        }

        // Check if we are in edit wall mode, then toggle the wall
        if (view.getEditWallsButton().isSelected()) {
            model.getFloorPlan(currentFloor).setWallAt(r, c, !model.getFloorPlan(currentFloor).wallAt(r, c));
            // Remove this tile from the room if it is on one, because it is now a wall
            Room room = model.getFloorPlan(currentFloor).roomAt(r, c);
            if (room != null)
                room.removeTile(r, c);
        }

        // Otherwise check if we are in edit exits mode
        else if (view.getSelectExitsButton().isSelected()) {
            if (model.exitAt(r, c) != null)
                model.removeExit(r, c);
            else {
                model.addExit(r, c);
                // Remove this tile from the room if it is on one, because it is now an exit
                Room off = model.getFloorPlan(currentFloor).roomAt(r, c);
                if (off != null)
                    off.removeTile(r, c);
            }
        }

        // Otherwise check if we are selecting a room tile
        else if (view.getEditRoomsButton().isSelected()) {
            if (!model.getFloorPlan(currentFloor).wallAt(r, c) && !model.hasExitAt(r, c)) {
                Room room = model.getFloorPlan(currentFloor).roomAt(r, c);
                if (room != null) {
                    room.removeTile(r, c);
                    if (room.getNumberOfTiles() == 0)
                        model.getFloorPlan(currentFloor).removeRoom(room);
                }
                else {
                    room = model.getFloorPlan(currentFloor).roomWithColor(currentColor);
                    if (room == null) {
                        room = model.getFloorPlan(currentFloor).addRoomAt(r, c);
                        room.setColorIndex(currentColor);
                    }
                    else {
                        room.addTile(r, c);
                    }
                }
            }
        }

        // Otherwise check if the select room radio button is pressed
        else if (view.getDefineRoomsButton().isSelected()) {
            if (model.getFloorPlan(currentFloor).roomAt(r, c) == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Selection");
                alert.setContentText("You must select a tile that belongs to a room");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                new RoomInfoDialog(model, currentFloor, r, c);
            }
        }

        view.update(currentFloor, currentColor);
    }

    public static void main(String[] args) {
        launch(args);
    }
}