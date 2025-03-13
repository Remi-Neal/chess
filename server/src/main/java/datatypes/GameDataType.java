package datatypes;

import chess.ChessGame;

public record GameDataType(int gameID, String whiteUsername, String blackUsername, String gameName) {
    private static ChessGame chessGame;

    @Override
    public String toString(){
        return "{\n" +
                "\r\t'gameId': " + gameID +
                "\r\t'whiteUserName': " + whiteUsername +
                "\r\t'blackUserName': " + blackUsername +
                "\r\t'gameName': " + gameName +
                "\n}";
    }


    // Setters
    public void setGameBoard(ChessGame game){ chessGame = game; }

    // Getters
    public ChessGame getChessGame(){ return chessGame; }
}
