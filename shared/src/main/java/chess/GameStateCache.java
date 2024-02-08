package chess;

import java.util.ArrayList;

public class GameStateCache {
    private boolean checkFlagsValid;
    private boolean mateFlagsValid;
    private boolean moveListsValid;
    private boolean checkFlagWhite;
    private boolean checkFlagBlack;
    private boolean checkmateFlagWhite;
    private boolean checkmateFlagBlack;
    private boolean stalemateFlagWhite;
    private boolean stalemateFlagBlack;
    private ArrayList<ChessMove> possibleMovesWhite;
    private ArrayList<ChessMove> possibleMovesBlack;
    public enum cacheCategory {
        CHECK_FLAGS,
        MATE_FLAGS,
        MOVE_LISTS,
        ALL,
    }
    public GameStateCache() {
        checkFlagsValid = false;
        mateFlagsValid = false;
        moveListsValid = false;
    }
    public boolean isValid(cacheCategory cacheCategory) {
        switch (cacheCategory) {
            case CHECK_FLAGS -> {
                return checkFlagsValid;
            } case MATE_FLAGS -> {
                return mateFlagsValid;
            } case MOVE_LISTS -> {
                return moveListsValid;
            // All
            } default -> {
                return checkFlagsValid && mateFlagsValid && moveListsValid;
            }
        }
    }
    public void invalidate(cacheCategory cacheCategory) {
        switch (cacheCategory) {
            case CHECK_FLAGS -> checkFlagsValid = false;
            case MATE_FLAGS -> mateFlagsValid = false;
            case MOVE_LISTS -> moveListsValid = false;
            //ALL
            default -> {
                checkFlagsValid = false;
                mateFlagsValid = false;
                moveListsValid = false;
            }
        }
    }
    public void validate(cacheCategory cacheCategory) {
        // Should I add checking here?
        switch (cacheCategory) {
            case CHECK_FLAGS -> checkFlagsValid = true;
            case MATE_FLAGS -> mateFlagsValid = true;
            case MOVE_LISTS -> moveListsValid = true;
            //ALL
            default -> {
                checkFlagsValid = true;
                mateFlagsValid = true;
                moveListsValid = true;
            }
        }
    }
    public boolean checkFlag(ChessGame.TeamColor teamColor) {
        if (isValid(cacheCategory.CHECK_FLAGS)) {
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
        if (isValid(cacheCategory.MATE_FLAGS)) {
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
        if (isValid(cacheCategory.MATE_FLAGS)) {
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
        if (isValid(cacheCategory.MOVE_LISTS)) {
            return teamColor == ChessGame.TeamColor.WHITE ? possibleMovesWhite : possibleMovesBlack;
        } else {
            throw new RuntimeException("InvalidCache");
        }
    }
    public void setPossibleMoves(ChessGame.TeamColor teamColor, ArrayList<ChessMove> possibleMoves) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            this.possibleMovesWhite = possibleMoves;
        } else {
            this.possibleMovesBlack = possibleMoves;
        }
    }

}
