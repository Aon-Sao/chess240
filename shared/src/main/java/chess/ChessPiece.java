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
        var piece = board.getPiece(myPosition);
        var team = piece.getTeamColor();
        var type = piece.getPieceType();
        var moveDirections = new MovementRay.RayDirection[] {};
        var moves = new ArrayList<ChessMove>();

        if (type == PieceType.BISHOP) {
            moveDirections = new MovementRay.RayDirection[] {
                    MovementRay.RayDirection.UP_RIGHT,
                    MovementRay.RayDirection.UP_LEFT,
                    MovementRay.RayDirection.DOWN_RIGHT,
                    MovementRay.RayDirection.DOWN_LEFT};
            for (MovementRay.RayDirection direction : moveDirections) {
                for (ChessPosition position : new MovementRay(board, myPosition, direction).trace()) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        } else if (type == PieceType.ROOK) {
            moveDirections = new MovementRay.RayDirection[] {
                    MovementRay.RayDirection.UP,
                    MovementRay.RayDirection.DOWN,
                    MovementRay.RayDirection.LEFT,
                    MovementRay.RayDirection.RIGHT};
            for (MovementRay.RayDirection direction : moveDirections) {
                for (ChessPosition position : new MovementRay(board, myPosition, direction).trace()) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        } else if (type == PieceType.QUEEN) {
            for (MovementRay.RayDirection direction : MovementRay.RayDirection.values()) {
                for (ChessPosition position : new MovementRay(board, myPosition, direction).trace()) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        } else if (type == PieceType.KING) {
            for (MovementRay.RayDirection direction : MovementRay.RayDirection.values()) {
                for (ChessPosition position : new MovementRay(board, myPosition, direction, 1).trace()) {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        } else if (type == PieceType.KNIGHT) {
            var horseMoves = new IntPair[] {
                    new IntPair(2, 1),
                    new IntPair(2, -1),
                    new IntPair(1, 2),
                    new IntPair(1, -2),
                    new IntPair(-1, 2),
                    new IntPair(-1, -2),
                    new IntPair(-2, 1),
                    new IntPair(-2, -1),
            };
            for (IntPair relativeMove : horseMoves) {
                var _newPosition = new IntPair(myPosition.getRow() + relativeMove.first(), myPosition.getColumn() + relativeMove.second());
                if (ChessPosition.isValidPosition(_newPosition)) {
                    var newPosition = new ChessPosition(_newPosition);
                    var targetPiece = board.getPiece(newPosition);
                    if (targetPiece == null || targetPiece.getTeamColor() != team) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                }
            }
        } else if (type == PieceType.PAWN) {
            int maxMoveDist = 1;
            MovementRay.RayDirection[] attackDirections;
            var promotionPieces = new PieceType[] {
                    PieceType.BISHOP,
                    PieceType.ROOK,
                    PieceType.QUEEN,
                    PieceType.KNIGHT,
            };
            if (team == ChessGame.TeamColor.WHITE) {
                moveDirections = new MovementRay.RayDirection[] {MovementRay.RayDirection.UP};
                attackDirections = new MovementRay.RayDirection[] {MovementRay.RayDirection.UP_LEFT, MovementRay.RayDirection.UP_RIGHT};
                if (myPosition.getRow() != 7) {
                    promotionPieces = new PieceType[] {null};
                }
                if (myPosition.getRow() == 2) {
                    maxMoveDist = 2;
                }
            } else {
                moveDirections = new MovementRay.RayDirection[] {MovementRay.RayDirection.DOWN};
                attackDirections = new MovementRay.RayDirection[] {MovementRay.RayDirection.DOWN_LEFT, MovementRay.RayDirection.DOWN_RIGHT};
                if (myPosition.getRow() != 2) {
                    promotionPieces = new PieceType[] {null};
                }
                if (myPosition.getRow() == 7) {
                    maxMoveDist = 2;
                }
            }
            for (PieceType promotionPiece : promotionPieces) {
                for (MovementRay.RayDirection direction : moveDirections) {
                    for (ChessPosition position : new MovementRay(board, myPosition, direction, maxMoveDist, MovementRay.RayType.MOVE_ONLY).trace()) {
                        moves.add(new ChessMove(myPosition, position, promotionPiece));
                    }
                }
            }
            for (PieceType promotionPiece : promotionPieces) {
                for (MovementRay.RayDirection direction : attackDirections) {
                    for (ChessPosition position : new MovementRay(board, myPosition, direction, maxMoveDist, MovementRay.RayType.ATTACK_ONLY).trace()) {
                        moves.add(new ChessMove(myPosition, position, promotionPiece));
                    }
                }
            }
        }
        return moves;
    }
}
