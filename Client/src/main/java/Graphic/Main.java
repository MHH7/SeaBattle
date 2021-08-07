package Graphic;

import Controller.RequestHandler;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        RequestHandler.getRequestHandler().start();
        MainView.getMV().setMainStage(primaryStage);
        primaryStage.setResizable(false);
        MainView.getMV().setScene("Login.fxml");
        primaryStage.show();
    }
}
