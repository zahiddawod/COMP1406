import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SliderPuzzleApp extends Application {
    private SliderPuzzleGame    model;
    private SliderPuzzleView    view;

    private GamePiece           selectedPiece;
    private boolean             justGrabbed;
    private int                 lastX;
    private int                 lastY;

    public void start(Stage primaryStage) {
        model = new SliderPuzzleGame();
        view = new SliderPuzzleView(model);

        // Add event handlers to the inner game board buttons
        for (int w=1; w<=(GameBoard.WIDTH); w++) {
            for (int h=1; h<=(GameBoard.HEIGHT); h++) {
                view.getGridSection(w, h).setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        handleGridSectionSelection(mouseEvent);
                    }
                });
                view.getGridSection(w, h).setOnMouseDragged(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        handleGridSectionMove(mouseEvent);
                    }
                });
            }
        }

        // Plug in the Start button and NeaxtBoard button event handlers
        view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.startBoard();
                view.update();
            }
        });
        view.getNextBoardButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.moveToNextBoard();
                view.update();
            }
        });

        primaryStage.setTitle("Slide Puzzle Application");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(view, -10+SliderPuzzleView.GRID_UNIT_SIZE*(GameBoard.WIDTH+2),45+SliderPuzzleView.GRID_UNIT_SIZE*(GameBoard.HEIGHT+2)));
        primaryStage.show();

        // Update the view upon startup
        view.update();
    }


    private void handleGridSectionSelection(MouseEvent mouseEvent) {
        lastX = (int)mouseEvent.getSceneX() / SliderPuzzleView.GRID_UNIT_SIZE;
        lastY = (int)mouseEvent.getSceneY() / SliderPuzzleView.GRID_UNIT_SIZE;
        selectedPiece = model.getCurrentBoard().pieceAt(lastX-1, lastY-1);
    }
    private void handleGridSectionMove(MouseEvent mouseEvent) {
        int currentGridX = (int) mouseEvent.getSceneX() / SliderPuzzleView.GRID_UNIT_SIZE;
        int currentGridY = (int) mouseEvent.getSceneY() / SliderPuzzleView.GRID_UNIT_SIZE;


        if (currentGridX >= 0 && currentGridX <= GameBoard.WIDTH+1 && currentGridY >= 0 && currentGridY <= GameBoard.HEIGHT+1) {
            if (selectedPiece instanceof HorizontalGamePiece) {
                if (currentGridX < lastX) {
                    if (selectedPiece.canMoveLeftIn(model.getCurrentBoard())) {
                        selectedPiece.moveLeft();
                        model.makeAMove();
                    }
                } else if (currentGridX > lastX) {
                    if (selectedPiece.canMoveRightIn(model.getCurrentBoard())) {
                        selectedPiece.moveRight();
                        model.makeAMove();

                        model.getCurrentBoard().checkCompletion(selectedPiece);
                        if (model.getCurrentBoard().isCompleted()) {
                            model.completeBoard();
                            model.moveToNextBoard();
                        }
                    }
                }
                lastX = currentGridX;
            } else if (selectedPiece instanceof VerticalGamePiece) {
                if (currentGridY < lastY) {
                    if (selectedPiece.canMoveUpIn(model.getCurrentBoard())) {
                        selectedPiece.moveUp();
                        model.makeAMove();
                    }
                } else if (currentGridY > lastY) {
                    if (selectedPiece.canMoveDownIn(model.getCurrentBoard())) {
                        selectedPiece.moveDown();
                        model.makeAMove();
                    }
                }
                lastY = currentGridY;
            }
        }
        view.update();
    }

    public static void main(String[] args) { launch(args); }
}