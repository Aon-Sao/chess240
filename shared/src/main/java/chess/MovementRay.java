package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MovementRay {
    private ChessBoard board;
    private ChessPosition startPosition;
    private RayDirection direction;
    private double maxRayLength;
    private boolean isAttackRay;
    private boolean isMoveRay;
    private static Map<RayDirection, IntPair> rayDirectionArrayMap;
    static{
        rayDirectionArrayMap = new HashMap() {{
            put(RayDirection.UP, new IntPair(1, 0));
            put(RayDirection.DOWN, new IntPair(-1, 0));
            put(RayDirection.LEFT, new IntPair(0, -1));
            put(RayDirection.RIGHT, new IntPair(0, 1));
            put(RayDirection.UP_LEFT, new IntPair(1, -1));
            put(RayDirection.UP_RIGHT, new IntPair(1, 1));
            put(RayDirection.DOWN_LEFT, new IntPair(-1, -1));
            put(RayDirection.DOWN_RIGHT, new IntPair(-1, 1));
        }};
    }
    public MovementRay(ChessBoard board, ChessPosition startPosition, RayDirection direction, double maxRayLength, RayType rayType) {
        this.board = board;
        this.startPosition = startPosition;
        this.direction = direction;
        this.maxRayLength = maxRayLength;
        if (rayType == RayType.MOVE_ATTACK) {
            this.isAttackRay = true;
            this.isMoveRay = true;
        } else if (rayType == RayType.MOVE_ONLY) {
            this.isAttackRay = false;
            this.isMoveRay = true;
        } else {
            this.isAttackRay = true;
            this.isMoveRay = false;
        }
    }
    public enum RayDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT
    }
    public enum RayType {
        ATTACK_ONLY,
        MOVE_ONLY,
        MOVE_ATTACK,
    }
    private IntPair incrementPosition(IntPair position) {
        int new_row = position.first() + rayDirectionArrayMap.get(this.direction).first();
        int new_col = position.second() + rayDirectionArrayMap.get(this.direction).second();
        if (ChessPosition.isValidPosition(new_row, new_col)) {
            return new IntPair(new_row, new_col);
        } else {
            return null;
        }
    }
    public Collection<ChessPosition> trace() {
        var team = this.board.getPiece(this.startPosition).getTeamColor();
        ArrayList<ChessPosition> positions = new ArrayList<>();
        IntPair _nextPosition = incrementPosition(new IntPair(startPosition.getRow(), startPosition.getColumn()));
        while ((_nextPosition != null) && (positions.size() < this.maxRayLength)) {
            ChessPosition currentPosition = new ChessPosition(_nextPosition.first(), _nextPosition.second());
            var occupant = this.board.getPiece(currentPosition);

            if (occupant == null) {
                if (this.isMoveRay) {
                    positions.add(currentPosition);
                }
            } else if ((occupant.getTeamColor() != team) && (this.isAttackRay)) {
                positions.add(currentPosition);
                break;
            } else {
                break;
            }

            _nextPosition = incrementPosition(new IntPair(currentPosition.getRow(), currentPosition.getColumn()));
        }
        return positions;
    }
}
