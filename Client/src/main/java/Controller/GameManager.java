package Controller;

import Model.*;
import Model.Requests.Request;
import Model.Responses.UpdateData;
import View.MainView;
import com.google.gson.Gson;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;

public class GameManager {
    private Game game;
    private Boolean isGameStarted;
    private static GameManager gameManager;
    private final Loop loop;
    private int timeLimit;
    private boolean isChanged;
    private ArrayList<Player> allPlayers;

    private GameManager(){
        isChanged = true;
        isGameStarted = false;
        timeLimit = 30;
        loop = new Loop(10,this::updateGame);
        loop.start();
        allPlayers = new ArrayList<>();
    }

    public static GameManager getGameManager() {
        if(gameManager == null)gameManager = new GameManager();
        return gameManager;
    }

    public void setGameStarted(Boolean gameStarted) {
        if(gameStarted && !isGameStarted)loop.restart();
        isGameStarted = gameStarted;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public Boolean isStarted() {
        return isGameStarted;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public void startGame(){
        loop.restart();
        timeLimit = 25;
    }

    public void updateGame(){
        Request req = new Request(Type.StartGame, Player.getCurrentPlayer().getAuthToken(),"");
        RequestHandler.getRequestHandler().getRequests().add(req);
        if(isGameStarted){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    int x = timeLimit - (int)(loop.getElapsedTime() / 1000);
                    if(x <= 0 && !(game.getPlayer1().isReady() && game.getPlayer2().isReady())){
                        x = 0;
                        if(Player.getCurrentPlayer().getGameId() == 1)game.getPlayer1().setReady(true);
                        if(Player.getCurrentPlayer().getGameId() == 2)game.getPlayer2().setReady(true);
                        Player.getCurrentPlayer().setReady(true);
                    }
                    else if(x <= 0){
                        GameManager.getGameManager().sendData();
                    }
                    MainView.getMV().getGameBoardViewer().getTimeLabel().setText(String.valueOf(x));
                    MainView.getMV().getMainStage().setTitle("Player" + Player.getCurrentPlayer().getGameId());
                    if(isChanged){
                        MainView.getMV().getGameBoardViewer().showBoard();
                        isChanged = false;
                    }
                }
            });
        }
    }

    public void sendData(){
        Gson gson = new Gson();
        UpdateData updateData = new UpdateData(GameManager.getGameManager().getGame(),Player.getCurrentPlayer());
        Request request = new Request(Type.UpdateGame,Player.getCurrentPlayer().getAuthToken(),gson.toJson(updateData));
        RequestHandler.getRequestHandler().getRequests().add(request);
        loop.restart();
    }

    public void startScoreBoard(boolean status){
        Loop loop2 = new Loop(10, this::updateScoreBoard);
        if(status) loop2.start();
        else loop2.stop();
    }

    public void updateScoreBoard(){
        Request request = new Request(Type.GetPlayers,Player.getCurrentPlayer().getAuthToken(), "");
        RequestHandler.getRequestHandler().getRequests().add(request);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                MainView.getMV().getScoreBoardViewer().showTable();
            }
        });
    }

    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = new ArrayList<>();
        this.allPlayers.addAll(allPlayers);
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }
}
