package yams.yamsgui.view;

import javafx.scene.control.ScrollPane;
import yams.yamsgui.view.component.TextComponent;

public class MessageBoxView extends ScrollPane {

    private MessageView messageView = new MessageView();

    public MessageBoxView() {
        this.setPrefWidth(880);
        this.setPrefHeight(400);
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //make scrollpane background white
        this.setStyle("-fx-background: #FFFFFF; -fx-border-color: #FFFFFF;");
        this.setContent(messageView);
    }

    public void addMessage(TextComponent text) {
        this.messageView.addMessage(text);
        this.vvalueProperty().bind(this.vmaxProperty());
    }

    public MessageView getMessageView() {
        return messageView;
    }

}
