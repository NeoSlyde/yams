package yams.yamsgui.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import yams.yamsgui.controller.SceneController;
import yams.yamsgui.model.BackgroundSocket;
import yams.yamsgui.view.component.TextComponent;

public class MainView extends Scene {

    public MainView(SceneController sceneController, BackgroundSocket bgSocket) {
        super(new GridPane(), 920, 720);
        GridPane grid = (GridPane) this.getRoot();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        MessageBoxView messageView = new MessageBoxView();

        grid.add(messageView, 0, 0, 2, 1);


        TextField input = new TextField();
        input.setPromptText("Entrez votre message ici");
        grid.add(input, 0, 5, 2, 1);

        Button send = new Button("Envoyer");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(send);
        grid.add(hbBtn, 2, 5);

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String message = input.getText();
                if (message.length() > 0) {
                    messageView.addMessage(new TextComponent(message.length(), message, bgSocket.getUsername(), false));
                    input.setText("");
                    //TODO
                }
            }
        });




        



    }
    
}
