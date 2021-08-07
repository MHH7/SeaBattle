package Model.Responses;

import Model.Game;
import Model.Player;

import java.util.ArrayList;

public class GamesData {
    ArrayList<Game> games;

    public GamesData(ArrayList<Game> games){
        this.games = games;
    }

    public ArrayList<Game> getGames() {
        return games;
    }
}
