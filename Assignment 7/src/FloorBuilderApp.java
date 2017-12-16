import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sun.nio.cs.ext.MacRoman;

public class FloorBuilderApp extends Application {
    public static final String       TITLE = "Floor Plan Builder";
    public static final short        WIDTH = 370;
    public static final short        HEIGHT = 320;

    private final boolean            isResizable = true;

    private FloorBuilderView         view;
    private Building                 building;

    public static final String[]    ROOM_COLORS =
            {"ORANGE", "YELLOW", "LIGHTGREEN", "DARKGREEN",
             "LIGHTBLUE", "BLUE", "CYAN", "DARKCYAN",
             "PINK", "DARKRED", "PURPLE", "GRAY"};

    private static byte                 currentColour = 0;

    public static byte getCurrentColour() { return currentColour; }

    public void start(Stage primaryStage) {
        building = Building.example();
        view = new FloorBuilderView(building);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(view, WIDTH, HEIGHT));
        primaryStage.setResizable(isResizable);
        primaryStage.sizeToScene();
        primaryStage.show();

        primaryStage.setMinWidth(primaryStage.getWidth()); // Sets the minimum width of the stage to what it currently looks like
        primaryStage.setMinHeight(primaryStage.getHeight()); // Sets the minimum height of the stage to what it currently looks like


        // ------------------------------------------------------------ CONTROLLER ------------------------------------------------------------ //
        // Colour of Room Button Event Handler
        view.getRoomColourButton().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentColour ++;
                if (currentColour >= ROOM_COLORS.length)
                    currentColour = 0;
                view.getRoomColourButton().setStyle("-fx-base: " + ROOM_COLORS[currentColour]);
            }
        });
        // Room Tiles Radio Button Event Handler
        view.getRadioButtons()[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.getRoomColourButton().setDisable(true);
            }
        });
        view.getRadioButtons()[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.getRoomColourButton().setDisable(true);
            }
        });
        view.getRadioButtons()[2].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.getRoomColourButton().setDisable(false);
            }
        });
        view.getRadioButtons()[3].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.getRoomColourButton().setDisable(true);
            }
        });
        // Grid Tiles Event Handler
        for (int row = 0; row < view.getSizeOfMap(); row++) {
            for (int col = 0; col < view.getSizeOfMap(); col++) {
                view.getGrid()[col][row].setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        handleGridSelectionTile(event);
                    }
                });
            }
        }
    }

    public void handleGridSelectionTile(MouseEvent event) {
        for (int row = 0; row < view.getSizeOfMap(); row++) {
            for (int col = 0; col < view.getSizeOfMap(); col++) {
                if (view.getGrid()[col][row].isPressed()) {
                    if (view.getRadioButtons()[0].isSelected()) { // If Walls button is selected
                        if (view.getBuilding().hasExitAt(row, col)) { // If selected tile is an exit
                            changeToExit(col, row, false); // Removes exit
                            changeToWall(col, row, true); // Adds wall
                        } else {
                            if (view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).wallAt(row, col)) // If selected tile is a wall
                                changeToWall(col, row, false); // Removes wall
                            else
                                changeToWall(col, row, true); // Adds wall
                        }
                    } else if (view.getRadioButtons()[1].isSelected()) { // If Exits button is selected
                        if (view.getBuilding().hasExitAt(row, col)) { // If selected tile is an exit
                            changeToExit(col, row, false); // Removes exit
                        } else {
                            changeToWall(col, row, false); // Removes wall
                            changeToExit(col, row, true); // Adds exit
                        }
                    } else if (view.getRadioButtons()[2].isSelected()) { // If Room Tiles button is selected
                        if (!view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).wallAt(row, col)
                                && !view.getBuilding().hasExitAt(row, col)) { // If selected tile isn't a wall or an exit
                            if (view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomAt(row, col) != null) { // If selected button is a room
                                int colourOfOtherButton = view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomAt(row, col).getColorIndex(); // Stores index colour of selected button
                                if (colourOfOtherButton != currentColour) { // Checks if it equals the currentColour
                                    view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(colourOfOtherButton).removeTile(row, col); // Removes tile from room
                                    System.out.println(view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(colourOfOtherButton).getNumberOfTiles()); //
                                    if (view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(colourOfOtherButton).getNumberOfTiles() == 0) { // Checks if number of tiles for this room is 0
                                        view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).removeRoom(view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(colourOfOtherButton)); // Deletes room if it is
                                        view.getTextField().setText(view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).getName() + " with " + view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).getNumberOfRooms() + " rooms."); // Redisplays the number of rooms in the current floor
                                    }
                                }
                            }
                            if (view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(currentColour) == null) { // If room with that colour doesn't already exist.
                                Room room = view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).addRoomAt(row, col); // Add the room to the floor
                                room.setColorIndex(currentColour); // Set the colour index of the room being added to the currentColour
                                view.getGrid()[col][row].setStyle("-fx-base: " + ROOM_COLORS[currentColour]); // Change background of button to currentRoomColour
                                // Update textfield ..
                                view.getTextField().setText(view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).getName() + " with " + view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).getNumberOfRooms() + " rooms.");
                            } else { // Else if room with that colour already exists ..
                                // If room tile isn't already in the location the user is clicking ..
                                if (!view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(currentColour).contains(row, col)) {
                                    view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(currentColour).addTile(row, col); // Adds tile to room
                                    view.getGrid()[col][row].setStyle("-fx-base: " + ROOM_COLORS[currentColour]); // Changes background of button to currentRoomColour
                                } else { // Else if it is ..
                                    view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(currentColour).removeTile(row, col); // Removes tile from room
                                    view.getGrid()[col][row].setStyle("-fx-base: WHITE");
                                    if (view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(currentColour).getNumberOfTiles() == 0) {
                                        view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).removeRoom(view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).roomWithColor(currentColour));
                                        view.getTextField().setText(view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).getName() + " with " + view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).getNumberOfRooms() + " rooms.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void changeToWall(int c, int r, boolean value) {
        view.getBuilding().getFloorPlan(view.getCurrentFloorPlan()).setWallAt(r, c, value);
        if (value)
            view.getGrid()[c][r].setStyle("-fx-base: BLACK");
        else
            view.getGrid()[c][r].setStyle("-fx-base: WHITE");
    }
    public void changeToExit(int c, int r, boolean value) {
        if (value) {
            view.getBuilding().addExit(r, c);
            // Thanks for not having a get method for number of exits :(
            if (view.getNumOfExits() < Building.MAXIMUM_EXITS) {
                view.setNumOfExits(1); // Increases number of exits on floor
                view.getGrid()[c][r].setStyle("-fx-base: RED");
                view.getGrid()[c][r].setText("Exit");
            }
        } else {
            view.getBuilding().removeExit(r, c);
            view.setNumOfExits(-1); // Decreases number of exits on floor
            view.getGrid()[c][r].setStyle("-fx-base: WHITE");
            view.getGrid()[c][r].setText("");
        }
    }

    public static void main (String[] args) { launch(args); }
}
