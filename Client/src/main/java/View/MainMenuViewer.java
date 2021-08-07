package View;

import Controller.GameManager;
import Controller.RequestHandler;
import Model.Player;
import Model.Requests.Request;
import Model.Type;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuViewer implements Initializable {

    @FXML
    private Button newGameButton;

    @FXML
    private Button watchButton;

    @FXML
    private Button scoreBoardButton;

    @FXML
    private Button infoButton;

    @FXML
    private Label waitingLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        waitingLabel.setVisible(false);
        MainView.getMV().setMainMenuViewer(this);
    }

    public void waitForOpp(){
        waitingLabel.setVisible(true);
        infoButton.setVisible(false);
        scoreBoardButton.setVisible(false);
        watchButton.setVisible(false);
        newGameButton.setVisible(false);
    }

    @FXML
    void createNewGame() {
        Request req = new Request(Type.NewGame, Player.getCurrentPlayer().getAuthToken(),"");
        RequestHandler.getRequestHandler().getRequests().add(req);
        GameManager.getGameManager();
        waitForOpp();
    }

    @FXML
    void showPersonalInfo() throws IOException {
        MainView.getMV().setScene("Information.fxml");
    }

    @FXML
    void showScoreBoard() throws IOException{
        MainView.getMV().setScene("ScoreBoard.fxml");
    }

    @FXML
    void watch() throws IOException{
        MainView.getMV().setScene("GamesViewer.fxml");
    }
}
