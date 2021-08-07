package View;

import Controller.GameManager;
import Controller.RequestHandler;
import Model.*;
import Model.Requests.Request;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameBoardViewer implements Initializable {

    @FXML
    private Label timeLabel;

    @FXML
    private Label scoreLabel1;

    @FXML
    private Label scoreLabel2;

    @FXML
    private Label turnLabel;

    @FXML
    private GridPane gridPane1;

    @FXML
    private GridPane gridPane2;

    @FXML
    private Button rebuildButton;

    @FXML
    private Button readyButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainView.getMV().setGameBoardViewer(this);
        showBoard();
        if(Player.getCurrentPlayer().getRebuildCnt() == 0)rebuildButton.setVisible(false);
        if(Player.getCurrentPlayer().isReady())readyButton.setVisible(false);
    }

    @FXML
    void rebuildBoard() {
        Request request = new Request(Type.Rebuild,Player.getCurrentPlayer().getAuthToken(), "");
        RequestHandler.getRequestHandler().getRequests().add(request);
        if(Player.getCurrentPlayer().getGameId() == 1)
            GameManager.getGameManager().getGame().getPlayer1().setRebuildCnt(Player.getCurrentPlayer().getRebuildCnt() - 1);
        if(Player.getCurrentPlayer().getGameId() == 2)
            GameManager.getGameManager().getGame().getPlayer2().setRebuildCnt(Player.getCurrentPlayer().getRebuildCnt() - 1);
        Player.getCurrentPlayer().setRebuildCnt(Player.getCurrentPlayer().getRebuildCnt() - 1);
        if(Player.getCurrentPlayer().getRebuildCnt() == 0){
            rebuildButton.setVisible(false);
        }
    }

    @FXML
    void setReady() {
        Gson gson = new Gson();
        Player.getCurrentPlayer().setReady(true);
        if(Player.getCurrentPlayer().getGameId() == 1)
            GameManager.getGameManager().getGame().getPlayer1().setReady(true);
        if(Player.getCurrentPlayer().getGameId() == 2)
            GameManager.getGameManager().getGame().getPlayer2().setReady(true);
        Request request = new Request(Type.ReadyPlayer,Player.getCurrentPlayer().getAuthToken(),gson.toJson(Player.getCurrentPlayer()));
        RequestHandler.getRequestHandler().getRequests().add(request);
        rebuildButton.setVisible(false);
        readyButton.setVisible(false);
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public void showBoard(){
        for(int i = 1;i <= 10;i++){
            for(int j = 1;j <= 10;j++){
                if(Player.getCurrentPlayer().getGameId() == 1){
                    Node node = gridPane1.getChildren().get(0);
                    gridPane1.getChildren().clear();
                    gridPane1.getChildren().add(0,node);
                    gridPane1.setGridLinesVisible(true);
                }
                if(Player.getCurrentPlayer().getGameId() == 2){
                    Node node = gridPane2.getChildren().get(0);
                    gridPane2.getChildren().clear();
                    gridPane2.getChildren().add(0,node);
                    gridPane2.setGridLinesVisible(true);
                }
            }
        }
        gridPane1.setGridLinesVisible(true);
        gridPane2.setGridLinesVisible(true);
        Board board = GameManager.getGameManager().getGame().getBoard();
        for(int i = 1;i <= 10;i++){
            for(int j = 1;j <= 10;j++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Graphic/Cell.fxml"));
                Cell c = board.getCell(i,j, Player.getCurrentPlayer().getGameId());
                Label cell = null;
                CellViewer cellViewer = null;
                try {
                    cell = loader.load();
                    cellViewer = loader.getController();
                    cellViewer.setCell(c);
                } catch (IOException e) {
                }
                if(c.isDestroyed()){
                    cell.setStyle("-fx-background-color: black;");
                }
                if(c.getShip() != null){
                    if(c.getShip().getType() == ShipType.BattleShip){
                        cell.setStyle("-fx-background-color: blue;");
                    }
                    if(c.getShip().getType() == ShipType.Destroyer){
                        cell.setStyle("-fx-background-color: red;");
                    }
                    if(c.getShip().getType() == ShipType.Cruiser){
                        cell.setStyle("-fx-background-color: green;"); }
                    if(c.getShip().getType() == ShipType.Frigate){
                        cell.setStyle("-fx-background-color: yellow;");
                    }
                    if(c.isDestroyed()){
                        cell.setStyle("-fx-background-color: gray;");
                    }
                }
                cell.setVisible(true);
                int n = Player.getCurrentPlayer().getGameId();
                if(n == 1){
                    gridPane1.add(cell,i - 1,j - 1);
                }
                else if(n == 2){
                    gridPane2.add(cell,i - 1,j - 1);
                }
                gridPane1.setVisible(true);
                gridPane2.setVisible(true);
                GridPane.setMargin(cell,new Insets(1));
            }
        }
        for(int i = 1;i <= 10;i++){
            for(int j = 1;j <= 10;j++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Graphic/Cell.fxml"));
                Cell c = board.getCell(i,j, 3 - Player.getCurrentPlayer().getGameId());
                Label cell = null;
                CellViewer cellViewer = null;
                try {
                    cell = loader.load();
                    cellViewer = loader.getController();
                    cellViewer.setCell(c);
                } catch (IOException e) {
                }
                if(c.isDestroyed()){
                    cell.setStyle("-fx-background-color: black;");
                }
                if(c.getShip() != null){
                    if(!c.getShip().isAlive()){
                        cell.setStyle("-fx-background-color: gray;");
                    }
                }
                cell.setVisible(true);
                int n = Player.getCurrentPlayer().getGameId();
                if(n == 1){
                    gridPane2.add(cell,i - 1,j - 1);
                }
                else if(n == 2){
                    gridPane1.add(cell,i - 1,j - 1);
                }
                gridPane1.setVisible(true);
                gridPane2.setVisible(true);
                GridPane.setMargin(cell,new Insets(1));
            }
        }
        scoreLabel1.setText(String.valueOf(GameManager.getGameManager().getGame().getPlayer1().getTotalScore()));
        scoreLabel2.setText(String.valueOf(GameManager.getGameManager().getGame().getPlayer2().getTotalScore()));
        turnLabel.setText("Player " + GameManager.getGameManager().getGame().getGameTurn() + "'s turn");
    }

    public void shootCell(Cell cell){
        if(!Player.getCurrentPlayer().isReady())return;
        if(Player.getCurrentPlayer().getGameId() != GameManager.getGameManager().getGame().getGameTurn())return;
        if(cell.getId() == Player.getCurrentPlayer().getGameId())return;
        if(cell.isDestroyed())return;
        cell.setDestroyed(true);
        if(cell.getShip() != null){
            if(Player.getCurrentPlayer().getGameId() == 1)GameManager.getGameManager().getGame().getPlayer1().addScore(1);
            if(Player.getCurrentPlayer().getGameId() == 2)GameManager.getGameManager().getGame().getPlayer2().addScore(1);
            Player.getCurrentPlayer().addScore(1);
        }
        if(Player.getCurrentPlayer().getGameId() == 1){
            for(Cell c : GameManager.getGameManager().getGame().getBoard().getField2())
                if(c.getCord() == cell.getCord())c.setDestroyed(true);
        }
        if(Player.getCurrentPlayer().getGameId() == 2){
            for(Cell c : GameManager.getGameManager().getGame().getBoard().getField1())
                if(c.getCord() == cell.getCord())c.setDestroyed(true);
        }
        GameManager.getGameManager().sendData();
    }
}
