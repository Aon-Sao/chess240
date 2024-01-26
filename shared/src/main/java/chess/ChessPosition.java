package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private int row = -1;
    private int col = -1;
    public ChessPosition(int row, int col) {
        if (!isValidPosition(row, col)) {
            throw new RuntimeException("ChessPosition must be in range 1-8");
        }
        this.col = col;
        this.row = row;
    }
    public ChessPosition(IntPair intPair) {
        this(intPair.first(), intPair.second());
    }
    public static boolean isValidPosition(int row, int col) {
        return !((row > 8) || (row < 1) || (col > 8) || (col < 1));
    }
    public static boolean isValidPosition(IntPair intPair) {
        return ChessPosition.isValidPosition(intPair.first(), intPair.second());
    }
    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return col;
    }

    @Override
    public String toString() {
//        return " ChessPosition{" + "row=" + row + ", col=" + col + '}';
        return "(" + row + ", " + col + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition position = (ChessPosition) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
