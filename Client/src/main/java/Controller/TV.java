package Controller;

import Model.Game;
import Model.Loop;
import Model.Player;
import Model.Requests.Request;
import Model.Type;
import View.MainView;
import javafx.application.Platform;

import java.util.ArrayList;

public class TV {
    private static TV tv;
    private ArrayList<Game> allGames;
    private Game currentGame;
    private int mode;

    private TV(){
        allGames = new ArrayList<>();
        mode = 1;
    }

    public static TV getTv() {
        if(tv == null)tv = new TV();
        return tv;
    }

    public void startGamesViewr(boolean status){
        Loop loop = new Loop(10, this::updateGames);
        if(status) loop.start();
        else loop.stop();
    }

    public void updateGames(){
        Request request = new Request(Type.GetGames,Player.getCurrentPlayer().getAuthToken(), "");
        RequestHandler.getRequestHandler().getRequests().add(request);
        if(currentGame != null){
            for(Game game : allGames)if(game.getPlayer1().getUserName().equals(currentGame.getPlayer1().getUserName())
                    || game.getPlayer1().getUserName().equals(currentGame.getPlayer2().getUserName()))currentGame = game;
        }
        if(mode == 1) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    MainView.getMV().getGamesViewer().showGames();
                }
            });
        }
        else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    MainView.getMV().getGamesViewer().showBoard();
                }
            });
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public void setAllGames(ArrayList<Game> allGames) {
        this.allGames = new ArrayList<>();
        this.allGames.addAll(allGames);
    }

    public ArrayList<Game> getAllGames() {
        return allGames;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Game getCurrentGame(){
        return currentGame;
    }
}
