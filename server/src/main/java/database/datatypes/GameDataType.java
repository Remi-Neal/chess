package database.datatypes;

import chess.ChessGame;

public record GameDataType(int gameId, String gameName) {
    private static String whiteUserName = "";
    private static String blackUserName = "";
    private static ChessGame chessGame;

    @Override
    public String toString(){
        return "{\n" +
                "\r\t'gameId': " + gameId +
                "\r\t'whiteUserName': " + whiteUserName +
                "\r\t'blackUserName': " + blackUserName +
                "\r\t'gameName': " + gameName +
                "\r\t'chessGame': " + chessGame +
                "\n}";
    }

    // Setters
    public void setWhite(String name){ whiteUserName = name; }
    public void setBlack(String name){ blackUserName = name; }
    public void setGame(ChessGame game){ chessGame = game; }

    // Getters
    public int getGameId(){ return gameId; }
    public String getWhiteUserName(){ return whiteUserName; }
    public String getBlackUserName(){ return blackUserName; }
    public String getGameName(){ return gameName; }
    public ChessGame getChessGame(){ return chessGame; }
}
