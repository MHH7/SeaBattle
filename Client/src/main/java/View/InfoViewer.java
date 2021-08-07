package View;

import Model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoViewer implements Initializable {

    @FXML
    private Label mainLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainLabel.setText(Player.getCurrentPlayer().getUserName() + "\n" + "Wins : " +
                Player.getCurrentPlayer().getWins() + "\n" + "Losses : " + Player.getCurrentPlayer().getLosses()
        + "\n" + "Total Score : " + Player.getCurrentPlayer().getTotalScore());
    }

    @FXML
    void Return() throws IOException {
        MainView.getMV().back();
    }
}
