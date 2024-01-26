package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ArrayList<ArrayList<ChessPiece>> _grid = new ArrayList<>(8);
    public ChessBoard() {
        // Initialize empty grid with null
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
        this._grid.get(position.getRow() - 1).set(position.getColumn() - 1, piece);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return this._grid.get(position.getRow() - 1).get(position.getColumn() - 1);
    }
    public void removePiece(ChessPosition position) {
        this._grid.get(position.getRow() - 1).set(position.getColumn() - 1, null);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.addPiece(new ChessPosition(1, 1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        this.addPiece(new ChessPosition(1, 2), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        this.addPiece(new ChessPosition(1, 3), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        this.addPiece(new ChessPosition(1, 4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
        this.addPiece(new ChessPosition(1, 5), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
        this.addPiece(new ChessPosition(1, 6), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
        this.addPiece(new ChessPosition(1, 7), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
        this.addPiece(new ChessPosition(1, 8), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        for (int i = 1; i <= 8; i++) {
            this.addPiece(new ChessPosition(2, i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
        }
        for (int j = 3; j <= 6; j++) {
            for (int i = 1; i <= 8; i++) {
                this.removePiece(new ChessPosition(j, i));
            }
        }
        for (int i = 1; i <= 8; i++) {
            this.addPiece(new ChessPosition(7, i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }
        this.addPiece(new ChessPosition(8, 1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        this.addPiece(new ChessPosition(8, 2), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        this.addPiece(new ChessPosition(8, 3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        this.addPiece(new ChessPosition(8, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        this.addPiece(new ChessPosition(8, 5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        this.addPiece(new ChessPosition(8, 6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        this.addPiece(new ChessPosition(8, 7), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        this.addPiece(new ChessPosition(8, 8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.equals(_grid, that._grid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_grid);
    }
    private String pieceString(ChessPiece piece) {
        Map<ChessPiece.PieceType, Character> TypeToCharMap = Map.of(
                ChessPiece.PieceType.PAWN, 'p',
                ChessPiece.PieceType.KNIGHT, 'n',
                ChessPiece.PieceType.ROOK, 'r',
                ChessPiece.PieceType.QUEEN, 'q',
                ChessPiece.PieceType.KING, 'k',
                ChessPiece.PieceType.BISHOP, 'b');

        if (piece == null) {
            return " ";
        }
        String s = TypeToCharMap.get(piece.getPieceType()).toString();
        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            return s;
        } else {
            return s.toUpperCase();
        }
    }
    @Override
    public String toString() {
        String s = "";
        for (int i = 1; i <= 8; i++) {
            s += "|\n";
            for (int j = 1; j <= 8; j++) {
                var piece = this.getPiece(new ChessPosition(i, j));
                s += "|" + this.pieceString(piece);
            }
        }
        s += "|\n";
//        s += _grid.toString();
        return s;
    }
}
