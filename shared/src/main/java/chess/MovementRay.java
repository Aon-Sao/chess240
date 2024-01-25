package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MovementRay {
    private ChessBoard board;
    private ChessPosition startPosition;
    private RayDirection direction;
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
    public MovementRay(ChessBoard board, ChessPosition startPosition, RayDirection direction) {
        this.board = board;
        this.startPosition = startPosition;
        this.direction = direction;
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
        while (_nextPosition != null) {
            ChessPosition currentPosition = new ChessPosition(_nextPosition.first(), _nextPosition.second());
            var occupant = this.board.getPiece(currentPosition);

            if (occupant == null) {
                positions.add(currentPosition);
            } else if (occupant.getTeamColor() == team) {
                break;
            } else if (occupant.getTeamColor() != team) {
                positions.add(currentPosition);
                break;
            }

            _nextPosition = incrementPosition(new IntPair(currentPosition.getRow(), currentPosition.getColumn()));
        }
        return positions;
    }
}
