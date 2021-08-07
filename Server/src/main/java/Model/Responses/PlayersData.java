package Model.Responses;

import Model.Player;

import java.util.ArrayList;

public class PlayersData {
    private final ArrayList<Player> players = new ArrayList<>();

    public PlayersData(ArrayList<Player> players){
        this.players.addAll(players);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String name){
        for(Player player : players){
            if(player.getUserName().equals(name))return player;
        }
        return null;
    }
}
