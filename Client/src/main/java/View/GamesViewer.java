package View;

import Config.Config;
import Controller.GameManager;
import Controller.TV;
import Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GamesViewer implements Initializable {

    @FXML
    private VBox vBox;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainView.getMV().setGamesViewer(this);
        TV.getTv().startGamesViewr(true);
    }

    public void showGames(){
        ArrayList<Game> games = TV.getTv().getAllGames();
        vBox.getChildren().clear();
        for(int i = 0;i < games.size();i++){
            Button button = new Button(games.get(i).getPlayer1().getUserName() +
                    " VS " + games.get(i).getPlayer2().getUserName());
            Game game = games.get(i);
            button.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    TV.getTv().setCurrentGame(game);
                    TV.getTv().setMode(2);
                    try {
                        MainView.getMV().setScene("WatchGame.fxml");
                    } catch (IOException e) {
                    }
                }
            });
            button.setAlignment(Pos.CENTER);
            button.setTextAlignment(TextAlignment.CENTER);
            button.setVisible(true);
            vBox.getChildren().add(button);
        }
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
        Board board = TV.getTv().getCurrentGame().getBoard();
        for(int i = 1;i <= 10;i++){
            for(int j = 1;j <= 10;j++){
                Cell c = board.getCell(i,j, 1);
                Label cell = new Label();
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
                gridPane1.add(cell,i - 1,j - 1);
                gridPane1.setVisible(true);
                GridPane.setMargin(cell,new Insets(1));
            }
        }
        for(int i = 1;i <= 10;i++){
            for(int j = 1;j <= 10;j++){
                Cell c = board.getCell(i,j, 1);
                Label cell = new Label();
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
                gridPane1.add(cell,i - 1,j - 1);
                gridPane1.setVisible(true);
                GridPane.setMargin(cell,new Insets(1));
            }
        }
        scoreLabel1.setText(String.valueOf(TV.getTv().getCurrentGame().getPlayer1().getTotalScore()));
        scoreLabel2.setText(String.valueOf(TV.getTv().getCurrentGame().getPlayer2().getTotalScore()));
        turnLabel.setText("Player " + TV.getTv().getCurrentGame().getGameTurn() + "'s turn");
    }

    @FXML
    void Return() throws IOException {
        if(TV.getTv().getMode() == 1)TV.getTv().startGamesViewr(false);
        else TV.getTv().setMode(1);
        MainView.getMV().back();
    }
}
