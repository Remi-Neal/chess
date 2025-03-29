package ui.clientstates;
import static ui.EscapeSequences.*;

public class ClientGamePlay {
    private enum Orientation{
        BLACK_VIEW, WHITE_VIEW
    }

    public static void joinGame(){

    }
    public static void observeGame(){

    }

    private static String renderGame(Orientation orient){
        StringBuilder strBuild = new StringBuilder();
        for(int i = 0; i < 8; i++){
            // Create one side of the board for indexing
            for(int j; j < 8; j++){

            }
            // Create other side of the board of indexing
        }
    }

    private final String[][] DEFAULT_BOARD = {
            {BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK},
            {BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN,BLACK_PAWN,BLACK_PAWN,BLACK_PAWN,BLACK_PAWN},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,WHITE_PAWN,},
            {WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK}
    };
}
