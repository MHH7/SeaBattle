package Model.Responses;

import Model.Game;
import Model.Player;

import java.util.ArrayList;

public class GamesData {
    ArrayList<Game> games;

    public GamesData(ArrayList<Game> games){
        this.games = new ArrayList<>();
        this.games.addAll(games);
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public Game getGame(Player player){
        for(Game g : games){
            if(g.getPlayer1().getUserName().equals(player.getUserName()) || g.getPlayer2().getUserName().equals(player.getUserName()))
                return g;
        }
        return null;
    }
}
