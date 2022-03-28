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
        this.setContent(messageView);
    }

    public void addMessage(TextComponent text) {
        this.messageView.addMessage(text);
    }

    public MessageView getMessageView() {
        return messageView;
    }

}
