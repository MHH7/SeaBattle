package Controller;

import Model.Cell;
import Model.Game;
import Model.Player;
import Model.Responses.GamesData;
import Model.Responses.PlayersData;
import Model.Responses.Response;
import Model.Responses.UpdateData;
import Model.Type;
import View.MainView;
import com.google.gson.Gson;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;

public class ResponseHandler {
    private static ResponseHandler responseHandler;

    public static ResponseHandler getResponseHandler() {
        if(responseHandler == null){
            responseHandler = new ResponseHandler();
        }
        return responseHandler;
    }

    public void handle(Response response){
        if(response == null)return;
        Gson gson = new Gson();
        Type type = response.getType();
        String data = response.getData();
        if(type == Type.SignIn){
            if(data.equals("Null")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MainView.getMV().getLoginViewer().getErrorBox().setText("UserName or Password is empty!");
                    }
                });
            }
            else if(data.equals("DExist")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MainView.getMV().getLoginViewer().getErrorBox().setText("This Username doesn't exist!");
                    }
                });
            }
            else if(data.equals("Wrong")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MainView.getMV().getLoginViewer().getErrorBox().setText("Wrong Password!");
                    }
                });
            }
            else{
                Player player = gson.fromJson(data,Player.class);
                Player.setCurrentPlayer(player);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MainView.getMV().setScene("MainMenu.fxml");
                        } catch (IOException e) {
                        }
                    }
                });
            }
        }
        else if(type == Type.SignUp){
            if(data.equals("Null")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MainView.getMV().getLoginViewer().getErrorBox().setText("UserName or Password is empty!");
                    }
                });
            }
            else if(data.equals("Duplicate")){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MainView.getMV().getLoginViewer().getErrorBox().setText("UserName has already taken!");
                    }
                });
            }
            else{
                Player player = gson.fromJson(data,Player.class);
                Player.setCurrentPlayer(player);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MainView.getMV().setScene("MainMenu.fxml");
                        } catch (IOException e) {
                        }
                    }
                });
            }
        }
        else if(type == Type.NewGame){
            if(data.equals("")){
                MainView.getMV().getMainMenuViewer().waitForOpp();
            }
        }
        else if(type == Type.StartGame){
            UpdateData updateData = gson.fromJson(data,UpdateData.class);
            if(updateData == null)return;
            if(!GameManager.getGameManager().isStarted()) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MainView.getMV().setScene("GameBoard.fxml");
                        } catch (IOException e) {
                        }
                    }
                });
            }
            if(GameManager.getGameManager().getGame() != null && updateData.getGame() != null) {
                if (!GameManager.getGameManager().getGame().isGameRunning() && updateData.getGame().isGameRunning())
                    GameManager.getGameManager().startGame();
            }
            if(GameManager.getGameManager().getGame() != null) {
                if (GameManager.getGameManager().getGame().getGameTurn() != updateData.getGame().getGameTurn())
                    GameManager.getGameManager().setChanged(true);
            }
            GameManager.getGameManager().setGame(updateData.getGame());
            Player.setCurrentPlayer(updateData.getPlayer());
            GameManager.getGameManager().setGameStarted(true);
        }
        else if(type == Type.Rebuild){
            GameManager.getGameManager().setGame(gson.fromJson(data,Game.class));
            GameManager.getGameManager().setTimeLimit(GameManager.getGameManager().getTimeLimit() + 10);
            GameManager.getGameManager().setChanged(true);
        }
        else if(type == Type.GetGames){
            GamesData gamesData = gson.fromJson(response.getData(),GamesData.class);
            ArrayList<Game> games = gamesData.getGames();
            TV.getTv().setAllGames(games);
        }
        else if(type == Type.UpdateGame){
            GameManager.getGameManager().setChanged(true);
        }
        else if(type == Type.GetPlayers){
            PlayersData playersData = gson.fromJson(response.getData(),PlayersData.class);
            GameManager.getGameManager().setAllPlayers(playersData.getPlayers());
        }
    }
}
