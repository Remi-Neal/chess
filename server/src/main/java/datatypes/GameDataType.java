package datatypes;

import chess.ChessGame;

public record GameDataType(
        int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame chessGame, Boolean active) {

    @Override
    public String toString(){
        return "{\n" +
                "\r\t'gameId': " + gameID +
                "\r\t'whiteUserName': " + whiteUsername +
                "\r\t'blackUserName': " + blackUsername +
                "\r\t'gameName': " + gameName +
                "\n}";
    }
}
