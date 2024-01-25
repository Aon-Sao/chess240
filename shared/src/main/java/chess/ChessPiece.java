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
            var moves = new ArrayList<ChessMove>();
            MovementRay.RayDirection[] directions = {
                    MovementRay.RayDirection.UP,
                    MovementRay.RayDirection.DOWN,
                    MovementRay.RayDirection.LEFT,
                    MovementRay.RayDirection.RIGHT};
            for (MovementRay.RayDirection direction : directions) {
                for (ChessPosition position : new MovementRay(board, myPosition, direction, Double.POSITIVE_INFINITY).trace()) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
            return moves;
        } else if (piece.getPieceType() == PieceType.BISHOP) {
            var moves = new ArrayList<ChessMove>();
            MovementRay.RayDirection[] directions = {
                    MovementRay.RayDirection.UP_LEFT,
                    MovementRay.RayDirection.UP_RIGHT,
                    MovementRay.RayDirection.DOWN_LEFT,
                    MovementRay.RayDirection.DOWN_RIGHT};
            for (MovementRay.RayDirection direction : directions) {
                for (ChessPosition position : new MovementRay(board, myPosition, direction, Double.POSITIVE_INFINITY).trace()) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
            return moves;
        } else if (piece.getPieceType() == PieceType.KNIGHT) {
            throw new RuntimeException("Knight not implemented");
        } else if (piece.getPieceType() == PieceType.KING) {
            var moves = new ArrayList<ChessMove>();
            MovementRay.RayDirection[] directions = {
                    MovementRay.RayDirection.UP,
                    MovementRay.RayDirection.DOWN,
                    MovementRay.RayDirection.LEFT,
                    MovementRay.RayDirection.RIGHT,
                    MovementRay.RayDirection.UP_LEFT,
                    MovementRay.RayDirection.UP_RIGHT,
                    MovementRay.RayDirection.DOWN_LEFT,
                    MovementRay.RayDirection.DOWN_RIGHT};
            for (MovementRay.RayDirection direction : directions) {
                for (ChessPosition position : new MovementRay(board, myPosition, direction, 1).trace()) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
            return moves;
        } else if (piece.getPieceType() == PieceType.QUEEN) {
            var moves = new ArrayList<ChessMove>();
            MovementRay.RayDirection[] directions = {
                    MovementRay.RayDirection.UP,
                    MovementRay.RayDirection.DOWN,
                    MovementRay.RayDirection.LEFT,
                    MovementRay.RayDirection.RIGHT,
                    MovementRay.RayDirection.UP_LEFT,
                    MovementRay.RayDirection.UP_RIGHT,
                    MovementRay.RayDirection.DOWN_LEFT,
                    MovementRay.RayDirection.DOWN_RIGHT};
            for (MovementRay.RayDirection direction : directions) {
                for (ChessPosition position : new MovementRay(board, myPosition, direction, Double.POSITIVE_INFINITY).trace()) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
            return moves;
        } else {
            return null;
        }
    }
}
