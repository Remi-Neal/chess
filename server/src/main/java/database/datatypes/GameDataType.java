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
    public void setGameBoard(ChessGame game){ chessGame = game; }

    // Getters
    public String getWhiteUserName(){ return whiteUserName; }
    public String getBlackUserName(){ return blackUserName; }
    public ChessGame getChessGame(){ return chessGame; }

    public GameDataType copy(){
        GameDataType newGameData = new GameDataType(gameId, gameName);
        newGameData.setBlack(blackUserName);
        newGameData.setWhite(whiteUserName);
        newGameData.setGameBoard(chessGame);
        return newGameData;
    }
}
