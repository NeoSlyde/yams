package yams.yamsgui.view.component;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import yams.yamsgui.model.BackgroundSocket;

public class RepostButton extends Button {

    private long id;

    public RepostButton(long id, BackgroundSocket bgSocket) {
        super("Repost");

        this.id = id;

        this.setPadding(Insets.EMPTY);

        this.setStyle("-fx-background-color: #001080; -fx-text-fill: white;");

    }

    public long getMsgId() {
        return id;
    }

}
