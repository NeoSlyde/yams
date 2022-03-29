package yams.yamsgui.view.component;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class ReplyButton extends Button{

    private long id;

    public ReplyButton(long id) {
        super("Reply");


        this.id = id;

        this.setPadding(Insets.EMPTY);
        
        this.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");

        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("ReplyButton.handle()");
            }
        });
    }

    public long getMsgId() {
        return id;
    }
    
    
    
}
