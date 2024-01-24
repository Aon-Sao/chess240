package chess;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.abs;

public class MovementHelpers {
    public static ArrayList<ChessPosition> positionsList() {
        // This method is just for iterating on valid positions...probably a bad move
        var positions = new ArrayList<ChessPosition>(8);
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                positions.add(new ChessPosition(i, j));
            }
        }
        return positions;
    }
    public static boolean is_directly_diagonal(ChessPosition startPosition, ChessPosition endPosition) {
        // The start and end columns differ by 1, and the start and end rows differ by 1
        return (abs(startPosition.getColumn() - endPosition.getColumn()) == 1) &&
                (abs(startPosition.getRow() - endPosition.getRow()) == 1);
    }
    public static boolean is_directly_adjacent(ChessPosition startPosition, ChessPosition endPosition) {
        // Either but not both of the start and end columns / rows differ by exactly 1.
        var col_diff = abs(startPosition.getColumn() - endPosition.getColumn());
        var row_diff = abs(startPosition.getRow() - endPosition.getRow());
        return (0 < col_diff && col_diff <= 1) || (0 < row_diff && row_diff <= 1);
    }
    private static boolean diagonal_helper(ChessPosition startPosition, ChessPosition endPosition, int direction1, int direction2) {
        if (startPosition.equals(endPosition)) {
            return true;
        } else if ((ChessPosition.isValidPosition(endPosition.getRow() + direction1, endPosition.getColumn() + direction2))
                 && (diagonal_helper(startPosition, new ChessPosition(endPosition.getRow() + direction1, endPosition.getColumn() + direction2), direction1, direction2))) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean is_on_diagonal(ChessPosition startPosition, ChessPosition endPosition) {
        if (is_directly_diagonal(startPosition, endPosition)) {
            return true;
        }
        if (diagonal_helper(startPosition, endPosition, 1, 1)) { // Up right
            return true;
        } else if (diagonal_helper(startPosition, endPosition, 1, -1)) { // Up left
            return true;
        } else if (diagonal_helper(startPosition, endPosition, -1, 1)) { // Down right
            return true;
        } else if (diagonal_helper(startPosition, endPosition, -1, -1)) { // Down left
            return true;
        } else {
            return false;
        }
    }

    public static boolean is_on_straightaway(ChessPosition startPosition, ChessPosition endPosition) {
        var up = new ChessPosition(endPosition.getRow() + 1, endPosition.getColumn());
        var down = new ChessPosition(endPosition.getRow() - 1, endPosition.getColumn());
        var right = new ChessPosition(endPosition.getRow(), endPosition.getColumn() + 1);
        var left = new ChessPosition(endPosition.getRow(), endPosition.getColumn() - 1);
        if (is_directly_adjacent(startPosition, endPosition)) {
            return true;
        } else if (is_on_straightaway(startPosition, up)) {
            return true;
        } else if (is_on_straightaway(startPosition, down)) {
            return true;
        } else if (is_on_straightaway(startPosition, right)) {
            return true;
        } else if (is_on_straightaway(startPosition, left)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean is_horse_adjacent(ChessPosition startPosition, ChessPosition endPosition) {
        throw new RuntimeException("NYI");
    }
    public static boolean is_on_bishop_path(ChessPosition startPosition, ChessPosition endPosition) {
        return is_on_diagonal(startPosition, endPosition);
    }
    public static boolean is_on_rook_path(ChessPosition startPosition, ChessPosition endPosition) {
        return is_on_straightaway(startPosition, endPosition);
    }
    public static boolean is_on_horse_path(ChessPosition startPosition, ChessPosition endPosition) {
        throw new RuntimeException("NYI");
    }
    public static boolean is_on_pawn_path(ChessPosition startPosition, ChessPosition endPosition) {
        throw new RuntimeException("NYI");
    }
    public static boolean is_on_king_path(ChessPosition startPosition, ChessPosition endPosition) {
        return (is_directly_adjacent(startPosition, endPosition) || is_directly_diagonal(startPosition, endPosition));
    }
    public static boolean is_on_queen_path(ChessPosition startPosition, ChessPosition endPosition) {
        return (is_on_straightaway(startPosition, endPosition) || is_on_diagonal(startPosition, endPosition));
    }
}
