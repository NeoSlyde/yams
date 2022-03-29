package yams.yamsgui;

import javafx.application.Application;
import javafx.stage.Stage;
import yams.yamsgui.controller.SceneController;
import yams.yamsgui.view.StartView;

public class App extends Application {
    StartView startScene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneController sceneController = new SceneController(primaryStage);
        primaryStage.setTitle("YAMS");
        startScene = new StartView(sceneController);

        sceneController.addScene("start", startScene);
        sceneController.switchScene("start");

        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
