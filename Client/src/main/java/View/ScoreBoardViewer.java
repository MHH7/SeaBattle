package View;

import Controller.GameManager;
import Model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScoreBoardViewer implements Initializable {

    @FXML
    private GridPane table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainView.getMV().setScoreBoardViewer(this);
        GameManager.getGameManager().startScoreBoard(true);
    }

    public void showTable(){
        ArrayList<Player> players = GameManager.getGameManager().getAllPlayers();
        for(int i = 0;i < players.size() + 1;i++){
            for(int j = 0;j < 6;j++) {
                Node node = table.getChildren().get(0);
                table.getChildren().clear();
                table.getChildren().add(0,node);
                table.setGridLinesVisible(true);
            }
        }
        Label rank = new Label("Rank");
        Label name = new Label("Name");
        Label status = new Label("Status");
        Label wins = new Label("Wins");
        Label losses = new Label("Losses");
        Label score = new Label("Current Score");
        GridPane.setMargin(rank,new Insets(1));
        GridPane.setMargin(name,new Insets(1));
        GridPane.setMargin(status,new Insets(1));
        GridPane.setMargin(wins,new Insets(1));
        GridPane.setMargin(losses,new Insets(1));
        GridPane.setMargin(score,new Insets(1));
        table.add(rank,0,0);
        table.add(name,1,0);
        table.add(status,2,0);
        table.add(wins,3,0);
        table.add(losses,4,0);
        table.add(score,5,0);
        for(int i = 0;i < players.size();i++){
            for(int j = i + 1;j < players.size();j++){
                if(players.get(i).getWins() > players.get(j).getWins()){
                    Player temp = players.get(i);
                    players.set(i,players.get(j));
                    players.set(j,temp);
                }
            }
        }
        for(int i = 0;i < players.size();i++){
            rank = new Label(String.valueOf(i + 1));
            name = new Label(players.get(i).getUserName());
            if(players.get(i).isOnline()) status = new Label(String.valueOf("Online"));
            else status = new Label(String.valueOf("Offline"));
            wins = new Label(String.valueOf(players.get(i).getWins()));
            losses = new Label(String.valueOf(players.get(i).getLosses()));
            score = new Label(String.valueOf(players.get(i).getTotalScore()));
            table.addRow(i + 1,rank,name,status,wins,losses,score);
        }
        table.setVisible(true);
    }

    @FXML
    void Return() throws IOException {
        GameManager.getGameManager().startScoreBoard(false);
        MainView.getMV().back();
    }
}
