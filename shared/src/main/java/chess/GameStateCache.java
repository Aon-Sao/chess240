package chess;

import java.util.ArrayList;
import java.util.Collection;

public class GameStateCache {
    private boolean valid;
    private boolean movesListsValid;
    private boolean checkFlagWhite;
    private boolean checkFlagBlack;
    private boolean checkmateFlagWhite;
    private boolean checkmateFlagBlack;
    private boolean stalemateFlagWhite;
    private boolean stalemateFlagBlack;
    private ArrayList<ChessMove> possibleMovesWhite;
    private ArrayList<ChessMove> possibleMovesBlack;
    public GameStateCache() {
        possibleMovesWhite = new ArrayList<>();
        possibleMovesBlack = new ArrayList<>();
        invalidate();
    }
    public boolean isValid() {
        return valid && movesListsValid;
    }
    public void invalidate() {
        valid = false;
        movesListsValid = false;
        possibleMovesWhite.clear();
        possibleMovesBlack.clear();
    }
    public void validate() {
        // Should I add checking here?
        valid = true;
        movesListsValid = true;
    }
    public boolean areMovesListsValid() {
        return movesListsValid;
    }
    public void setMovesListsValid(boolean value) {
        movesListsValid = value;
    }
    public boolean checkFlag(ChessGame.TeamColor teamColor) {
        if (isValid()) {
            return teamColor == ChessGame.TeamColor.WHITE ? checkFlagWhite : checkFlagBlack;
        } else {
            throw new RuntimeException("InvalidCache");
        }
    }
    public void setCheckFlag(ChessGame.TeamColor teamColor, boolean flagValue) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            this.checkFlagWhite = flagValue;
        } else {
            this.checkFlagBlack = flagValue;
        }
    }
    public boolean checkmateFlag(ChessGame.TeamColor teamColor) {
        if (isValid()) {
            return teamColor == ChessGame.TeamColor.WHITE ? checkmateFlagWhite : checkmateFlagBlack;
        } else {
            throw new RuntimeException("InvalidCache");
        }
    }
    public void setCheckmateFlag(ChessGame.TeamColor teamColor, boolean flagValue) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            this.checkmateFlagWhite = flagValue;
        } else {
            this.checkmateFlagBlack = flagValue;
        }
    }

    public boolean stalemateFlag(ChessGame.TeamColor teamColor) {
        if (isValid()) {
            return teamColor == ChessGame.TeamColor.WHITE ? stalemateFlagWhite : stalemateFlagBlack;
        } else {
            throw new RuntimeException("InvalidCache");
        }
    }
    public void setStalemateFlag(ChessGame.TeamColor teamColor, boolean flagValue) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            this.stalemateFlagWhite = flagValue;
        } else {
            this.stalemateFlagBlack = flagValue;
        }
    }

    public ArrayList<ChessMove> getPossibleMoves(ChessGame.TeamColor teamColor) {
        if (areMovesListsValid()) {
            return teamColor == ChessGame.TeamColor.WHITE ? possibleMovesWhite : possibleMovesBlack;
        } else {
            throw new RuntimeException("InvalidCache");
        }
    }
    public void addPossibleMoves(ChessGame.TeamColor teamColor, Collection<ChessMove> possibleMoves) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            this.possibleMovesWhite.addAll(possibleMoves);
        } else {
            this.possibleMovesBlack.addAll(possibleMoves);
        }
    }

}
