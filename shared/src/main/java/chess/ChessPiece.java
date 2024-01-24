package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private PieceType _piece_type;
    private ChessGame.TeamColor _team_color;
    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this._piece_type = type;
        this._team_color = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this._team_color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this._piece_type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Step 1
        // Iterate through all board positions, check if they are on-path for the
        // movement pattern of the relevant piece. If they are, add them to a collection.
        // Step 2
        // Iterate through prior collection and filter out positions that are blocked
        // Return the final collection

        // Useful helpers
        // Iterator for positions
        // is_on_path lambdas for each piece
        // Blocking detection

        // Drawback: it would be more efficient not to consider past blocked positions
        var piece = board.getPiece(myPosition);
        if (piece.getPieceType() == PieceType.PAWN) {
            throw new RuntimeException("Pawn not implemented");
        } else if (piece.getPieceType() == PieceType.ROOK) {
            throw new RuntimeException("Rook not implemented");
        } else if (piece.getPieceType() == PieceType.BISHOP) {
            var moves = new ArrayList<ChessMove>();
            var diagonalPositions = new ArrayList<ChessPosition>();
            for (ChessPosition position : MovementHelpers.positionsList()) {
                if (MovementHelpers.is_on_diagonal(myPosition, position)) {
                    diagonalPositions.add(position);
                }
            }
            // Remove own position from viable positions to move to
            diagonalPositions.removeIf(n -> (n.equals(myPosition)));

            // Populate ChessMoves for each viable position
            for (ChessPosition position : diagonalPositions) {
                moves.add(new ChessMove(myPosition, position, null));
            }
//            throw new RuntimeException("Bishop not implemented");
            return moves;
        } else if (piece.getPieceType() == PieceType.KNIGHT) {
            throw new RuntimeException("Knight not implemented");
        } else if (piece.getPieceType() == PieceType.KING) {
            throw new RuntimeException("King not implemented");
        } else if (piece.getPieceType() == PieceType.QUEEN) {
            throw new RuntimeException("Queen not implemented");
        } else {
            return null;
        }
    }
}
