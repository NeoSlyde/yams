package yams.yamsgui.view;

import javafx.scene.layout.GridPane;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import yams.yamsgui.controller.SceneController;
import yams.yamsgui.model.BackgroundSocket;

public class StartView extends Scene {

    private BackgroundSocket bgSocket;

    public StartView(SceneController sceneController) {
        super(new GridPane(), 300, 275);
        GridPane grid = (GridPane) this.getRoot();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("YAMS");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Pseudo:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label hostName = new Label("Hostname:");
        grid.add(hostName, 0, 2);

        TextField hostTextField = new TextField();
        grid.add(hostTextField, 1, 2);

        Label portName = new Label("Port:");
        grid.add(portName, 0, 3);

        TextField portTextField = new TextField();
        grid.add(portTextField, 1, 3);

        Button btn = new Button("Se connecter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if (userTextField.getText().trim().isEmpty()) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Pseudo invalide");
                } else {
                    if (bgSocket == null) {
                        try {
                            bgSocket = new BackgroundSocket(hostTextField.getText(),
                                    Integer.parseInt(portTextField.getText()), userTextField.getText());
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("Connexion en cours...");
                            MainView mainScene = new MainView(sceneController, bgSocket);
                            sceneController.addScene("main", mainScene);
                            sceneController.switchScene("main");

                        } catch (NumberFormatException | IOException e1) {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("Erreur de connexion");
                        }

                    }

                }
            }
        });
    }
}