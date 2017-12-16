import javafx.scene.paint.Color;

public class HorizontalGamePiece extends GamePiece {
    public HorizontalGamePiece(int w, Color c, int x, int y) {
        super(w, 1, c, x, y);
    }

    @Override
    public boolean canMoveLeftIn(GameBoard b) {
        if (b.checkCollisionAt(this.topLeftX-1, this.topLeftY) == null && (this.topLeftX-1) >= 0)
            return true;
        return false;
    }

    @Override
    public boolean canMoveRightIn(GameBoard b) {
        if (b.checkCollisionAt(this.topLeftX+this.width, this.topLeftY) == null && (this.topLeftX+this.width+1) <= GameBoard.WIDTH)
            return true;
        return false;
    }
}