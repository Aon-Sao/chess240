package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private int _row = -1;
    private int _col = -1;
    public ChessPosition(int row, int col) {
        if ((row > 8) || (row < 1) || (col > 8) || (col < 1)) {
            throw new RuntimeException("ChessPosition must be in range 1-8");
        }
        this._col = col;
        this._row = row;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return _row - 1;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return _col - 1;
    }
}
