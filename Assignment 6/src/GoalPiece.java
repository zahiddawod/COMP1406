import javafx.scene.paint.Color;

public class GoalPiece extends HorizontalGamePiece {
    public GoalPiece(int x, int y) {
        super(2, Color.RED, x, y);
    }

    @Override
    public boolean canMoveRightIn(GameBoard b) {
        if (b.checkCollisionAt(this.topLeftX+this.width, this.topLeftY) == null && (this.topLeftX) <= GameBoard.WIDTH)
            return true;
        return false;
    }
}