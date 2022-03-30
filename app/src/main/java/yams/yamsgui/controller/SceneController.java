package yams.yamsgui.controller;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    private HashMap<String, Scene> scenes = new HashMap<>();
    private Stage primaryStage;

    public SceneController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void addScene(String sceneName, Scene scene){
        scenes.put(sceneName, scene);
    }

    public void removeScene(String sceneName){
        scenes.remove(sceneName);
    }

    public void switchScene(String sceneName){
        primaryStage.setScene(scenes.get(sceneName) );
    }

}
