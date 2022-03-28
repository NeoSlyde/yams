package yams.yamsgui.view.component;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TextComponent extends Pane{

    private Text text;
    
    public TextComponent(String text) {
        this.text = new Text(text);
        this.text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.text.setFill(Color.WHITE);
        this.getChildren().add(this.text);
    }
    
    public void setText(String text) {
        this.text.setText(text);
    }
    
    public String getText() {
        return this.text.getText();
    }
    
}
