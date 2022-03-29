package yams.yamsgui.view.component;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class SubscribeButton extends Button{

    private String author;

    public SubscribeButton(String author) {
        super("Subscribe");

        this.setStyle("-fx-background-color: #74992e; -fx-text-fill: white;");

        this.setPadding(Insets.EMPTY);

        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("SubscribeButton.handle()");
            }
        });
    }
    
    
    
}
