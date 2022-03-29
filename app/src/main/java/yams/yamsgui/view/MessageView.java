package yams.yamsgui.view;

import javafx.scene.layout.GridPane;
import yams.yamsgui.view.component.TextComponent;

public class MessageView extends GridPane{

    public MessageView() {
        this.setPrefWidth(880);
        this.setHgap(10);
        this.setVgap(1);
    }

    public void addMessage(TextComponent text){
        this.add(text, 0, this.getRowCount());
    }
    
    
}
