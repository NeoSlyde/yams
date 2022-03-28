package yams.yamsgui.view;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import yams.yamsgui.view.component.TextComponent;

public class MessageView extends GridPane{

    public MessageView() {
        this.setPrefWidth(400);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
    }

    public void addMessage(TextComponent text){
        this.add(text, 0, this.getRowCount());
    }
    
    
}
