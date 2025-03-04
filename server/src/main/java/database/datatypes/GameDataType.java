package database.datatypes;

import chess.ChessGame;

public class GameDataType {
    private static int gameId;
    private static String whiteUserName = "";
    private static String blackUserName = "";
    private static String gameName = "";
    private static ChessGame chessGame;

    public GameDataType(int id, String name){
        gameId = id;
        gameName = name;
    }

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
