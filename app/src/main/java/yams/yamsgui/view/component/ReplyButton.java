package yams.yamsgui.view.component;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ReplyButton extends Button{

    public ReplyButton(String text) {
        super(text);

        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("ReplyButton.handle()");
            }
        });
    }
    
    
    
}
