package yams.yamsgui.view.component;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import yams.interaction.Interaction;
import yams.interaction.fluxreq.UnSubReq;
import yams.interaction.fluxreq.UnSubReq.User;
import yams.yamsgui.model.BackgroundSocket;

public class UnSubscribeButton extends Button{

    private String author;

    public UnSubscribeButton(String author, BackgroundSocket bgSocket) {
        super("Unsubscribe");

        this.setStyle("-fx-background-color: #B22222; -fx-text-fill: white;");

        this.setPadding(Insets.EMPTY);

        this.author = author;

        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    User unSubReq = new UnSubReq.User(null, author);
                    bgSocket.sendToFlux((Interaction) unSubReq);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String getAuthor() {
        return author;
    }
    
    
    
}
