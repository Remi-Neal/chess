package database.datamodels;

import chess.ChessGame;

public class GameData {
    private static int gameId;
    private static String whiteUserName = "";
    private static String blackUserName = "";
    private static String gameName = "";
    private static ChessGame chessGame;

    public GameData(int id, String name){
        gameId = id;
        gameName = name;
    }

    // Setters
    public static void setWhite(String name){ whiteUserName = name; }
    public static void setBlack(String name){ blackUserName = name; }
    public static void setGame(ChessGame game){ chessGame = game; }

    // Getters
    public int getGameId(){ return gameId; }
    public static String getWhiteUserName(){ return whiteUserName; }
    public static String getBlackUserName(){ return blackUserName; }
    public static String getGameName(){ return gameName; }
    public static ChessGame getChessGame(){ return chessGame; }
}
