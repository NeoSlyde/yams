package yams.yamsgui.view.component;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import yams.yamsgui.model.BackgroundSocket;

public class ReplyButton extends Button {

    private long id;

    public ReplyButton(long id, BackgroundSocket bgSocket) {
        super("Reply");

        this.id = id;

        this.setPadding(Insets.EMPTY);

        this.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");

        
    }

    public long getMsgId() {
        return id;
    }

}
