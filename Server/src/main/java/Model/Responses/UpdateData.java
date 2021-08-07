package Model.Responses;

import Model.Game;
import Model.Player;

public class UpdateData {
    private final Game game;
    private final Player player;

    public UpdateData(Game game, Player player){
        this.game = game;
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }
}
