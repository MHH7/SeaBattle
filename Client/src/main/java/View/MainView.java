package View;

import Graphic.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Stack;

public class MainView {
    private Stage mainStage;
    private static MainView MV;
    private final Stack<String> all;
    private LoginViewer loginViewer;
    private MainMenuViewer mainMenuViewer;
    private GameBoardViewer gameBoardViewer;
    private ScoreBoardViewer scoreBoardViewer;
    private GamesViewer gamesViewer;

    private MainView(){
        all = new Stack<>();
    }

    public static MainView getMV(){
        if(MV == null)MV = new MainView();
        return MV;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setScene(String scene) throws IOException {
        String name = "";
        for(int i = 0;i < scene.length();i++){
            if(scene.charAt(i) == '.' || scene.charAt(i) == '/')break;
            name += scene.charAt(i);
        }
        all.push(scene);
        Parent pane = FXMLLoader.load(Main.class.getResource(scene));
        mainStage.setScene(new Scene(pane));
    }

    public void back() throws IOException{
        all.pop();
        Parent pane = FXMLLoader.load(Main.class.getResource(all.lastElement()));
        mainStage.setScene(new Scene(pane));
    }

    public void setLoginViewer(LoginViewer loginViewer) {
        this.loginViewer = loginViewer;
    }

    public LoginViewer getLoginViewer() {
        return loginViewer;
    }

    public void setMainMenuViewer(MainMenuViewer mainMenuViewer) {
        this.mainMenuViewer = mainMenuViewer;
    }

    public MainMenuViewer getMainMenuViewer() {
        return mainMenuViewer;
    }

    public void setGameBoardViewer(GameBoardViewer gameBoardViewer) {
        this.gameBoardViewer = gameBoardViewer;
    }

    public GameBoardViewer getGameBoardViewer() {
        return gameBoardViewer;
    }

    public void setScoreBoardViewer(ScoreBoardViewer scoreBoardViewer) {
        this.scoreBoardViewer = scoreBoardViewer;
    }

    public ScoreBoardViewer getScoreBoardViewer() {
        return scoreBoardViewer;
    }

    public void setGamesViewer(GamesViewer gamesViewer) {
        this.gamesViewer = gamesViewer;
    }

    public GamesViewer getGamesViewer() {
        return gamesViewer;
    }
}
