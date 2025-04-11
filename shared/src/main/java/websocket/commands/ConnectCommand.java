package websocket.commands;

import websocket.commands.commandenums.PlayerTypes;

public class ConnectCommand extends UserGameCommand {
    PlayerTypes playerType;
    public ConnectCommand(CommandType commandType, String authToken, Integer gameID, PlayerTypes playerType) {
        super(commandType, authToken, gameID);
        this.playerType = playerType;
    }
    public PlayerTypes getPlayerType(){ return this.playerType; }
}
