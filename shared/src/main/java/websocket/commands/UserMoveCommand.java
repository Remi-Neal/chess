package websocket.commands;

import chess.ChessMove;

import java.util.Objects;

public class UserMoveCommand extends UserGameCommand{
    private ChessMove move;
    public UserMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessMove move) {
        super(commandType, authToken, gameID);
        this.move = move;
    }

    public ChessMove getChessMove(){ return this.move; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserMoveCommand)) {
            return false;
        }
        UserMoveCommand that = (UserMoveCommand) o;
        return getCommandType() == that.getCommandType() &&
                Objects.equals(getAuthToken(), that.getAuthToken()) &&
                Objects.equals(getGameID(), that.getGameID()) &&
                Objects.equals(getChessMove(), that.getChessMove());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthToken(), getGameID(), getChessMove());
    }
}
