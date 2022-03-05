package yams.view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import yams.controller.StartController;
import yams.model.StartModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartView extends GridPane{
    private final StartModel model;
    private final StartController controller;
    
    public StartView(StartModel model, StartController controller) {
        this.model = model;
        this.controller = controller;


        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("YAMS");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Pseudo:");
        this.add(userName, 0, 1);

        TextField userTextField = new TextField();
        this.add(userTextField, 1, 1);
        
        Button btn = new Button("Se connecter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        this.add(hbBtn, 1, 2);

        final Text actiontarget = new Text();
        this.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if(userTextField.getText().trim().isEmpty()) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Met un pseudo sale fdp");
                }
                else{
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Pas encore fait la page main lol");
                }
            }
        });
    }

    private
}
