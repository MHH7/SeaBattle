package View;

import Controller.RequestHandler;
import Model.Player;
import Model.Requests.Request;
import Model.Requests.UserPassData;
import Model.Type;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewer implements Initializable {
    @FXML
    private TextField userNameTextBox;

    @FXML
    private TextField passwordTextBox;

    @FXML
    private Label errorBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainView.getMV().setLoginViewer(this);
        errorBox.setText("");
    }

    @FXML
    void signUp() {
        errorBox.setText("");
        Gson gson = new Gson();
        UserPassData data = new UserPassData(userNameTextBox.getText(),passwordTextBox.getText());
        long authToken = 0;
        if(Player.getCurrentPlayer() != null)authToken = Player.getCurrentPlayer().getAuthToken();
        Request request = new Request(Type.SignUp,authToken,gson.toJson(data));
        RequestHandler.getRequestHandler().getRequests().add(request);
    }

    @FXML
    void singIn() {
        errorBox.setText("");
        Gson gson = new Gson();
        UserPassData data = new UserPassData(userNameTextBox.getText(),passwordTextBox.getText());
        long authToken = 0;
        if(Player.getCurrentPlayer() != null)authToken = Player.getCurrentPlayer().getAuthToken();
        Request request = new Request(Type.SignIn,authToken,gson.toJson(data));
        RequestHandler.getRequestHandler().getRequests().add(request);
    }

    public Label getErrorBox() {
        return errorBox;
    }
}
