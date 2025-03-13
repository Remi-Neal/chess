import chess.*;
import dataaccess.sql.SqlDAO;
import server.Server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        var server = new Server(new SqlDAO());
        int port = server.run(8080);
    }
}