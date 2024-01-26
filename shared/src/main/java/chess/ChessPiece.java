package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private PieceType _piece_type;
    private ChessGame.TeamColor _team_color;
    private boolean has_moved_ever;
    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this._piece_type = type;
        this._team_color = pieceColor;
        this.has_moved_ever = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return _piece_type == that._piece_type && _team_color == that._team_color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_piece_type, _team_color);
    }

    @Override
    public String toString() {
        return "ChessPiece{" + _piece_type + ", " + _team_color + '}';
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
            var moves = new ArrayList<ChessMove>();
            var positions = new ArrayList<ChessPosition>();
            IntPair one_ahead;
            IntPair left_ahead;
            IntPair right_ahead;
            PieceType promotionPiece = null;
            if (this.getTeamColor() == ChessGame.TeamColor.WHITE) {
                one_ahead = new IntPair(myPosition.getRow() + 1, myPosition.getColumn());
                left_ahead = new IntPair(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                right_ahead = new IntPair(myPosition.getRow() + 1, myPosition.getColumn() + 1);
                if (myPosition.getRow() == 7) {
                    promotionPiece = PieceType.QUEEN;
                }
            } else {
                one_ahead = new IntPair(myPosition.getRow() - 1, myPosition.getColumn());
                left_ahead = new IntPair(myPosition.getRow() - 1, myPosition.getColumn() + 1);
                right_ahead = new IntPair(myPosition.getRow() - 1, myPosition.getColumn() - 1);
                if (myPosition.getRow() == 2) {
                    promotionPiece = PieceType.QUEEN;
                }
            }
            if (ChessPosition.isValidPosition(one_ahead)) {
                var newPosition = new ChessPosition(one_ahead);
                if (board.getPiece(newPosition) == null) {
                    positions.add(newPosition);
                }
            }
            var diagonals = new IntPair[]{left_ahead, right_ahead};
            for (IntPair intPair : diagonals) {
                if (ChessPosition.isValidPosition(intPair)) {
                    var target_piece = board.getPiece(new ChessPosition(intPair));
                    if ((target_piece != null) && (target_piece.getTeamColor() != this.getTeamColor())) {
                        positions.add(new ChessPosition(intPair));
                    }
                }
            }
            for (PieceType type : PieceType.values()) {
                for (ChessPosition position : positions) {
                    moves.add(new ChessMove(myPosition, position, type));
                }
            }
            return moves;
        } else if (piece.getPieceType() == PieceType.KNIGHT) {
            var moves = new ArrayList<ChessMove>();
            var start_row = myPosition.getRow();
            var start_col = myPosition.getColumn();
            IntPair[] horseMoves = {
                    new IntPair(2, 1),
                    new IntPair(2, -1),
                    new IntPair(1, 2),
                    new IntPair(1, -2),
                    new IntPair(-1, 2),
                    new IntPair(-1, -2),
                    new IntPair(-2, 1),
                    new IntPair(-2, -1)
            };
            for (IntPair pair : horseMoves) {
                var new_row = start_row + pair.first();
                var new_col = start_col + pair.second();
                if (ChessPosition.isValidPosition(new_row, new_col)) {
                    var newPosition = new ChessPosition(new_row, new_col);
                    var target_piece = board.getPiece(newPosition);
                    if ((target_piece == null) || (target_piece.getTeamColor() != this.getTeamColor())) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                }
            }
            return moves;
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
