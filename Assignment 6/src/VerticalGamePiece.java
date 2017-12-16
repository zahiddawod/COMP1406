import javafx.scene.paint.Color;

public class VerticalGamePiece extends GamePiece {
    public VerticalGamePiece(int h, Color c, int x, int y) {
        super(1, h, c, x, y);
    }

    @Override
    public boolean canMoveDownIn(GameBoard b) {
        if (b.checkCollisionAt(this.topLeftX, this.topLeftY+height) == null && (this.topLeftY+this.height+1) <= GameBoard.HEIGHT)
            return true;
        return false;
    }

    @Override
    public boolean canMoveUpIn(GameBoard b) {
        if (b.checkCollisionAt(this.topLeftX, this.topLeftY-1) == null && (this.topLeftY-1) >= 0)
            return true;
        return false;
    }
}