package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static chess.ChessGame.TeamColor.NONE;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ArrayList<ArrayList<ChessPiece>> _grid = new ArrayList<>(8);
    public ChessBoard() {
        // Initialize empty grid with pieces of TeamColor NONE and PieceType NONE
        for (int i = 0; i < 8; i++) {
            var sublist = new ArrayList<ChessPiece>(8);
            for (int j = 0; j < 8; j++) {
                sublist.add(null);
            }
            this._grid.add(sublist);
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        this._grid.get(position.getRow()).set(position.getColumn(), piece);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return this._grid.get(position.getRow()).get(position.getColumn());
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        throw new RuntimeException("Not implemented");
    }
}
