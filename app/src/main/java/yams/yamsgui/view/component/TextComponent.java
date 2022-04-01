package yams.yamsgui.view.component;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import yams.yamsgui.model.BackgroundSocket;

public class TextComponent extends HBox {

    private long id;
    private Text text;
    private String author;
    private Boolean republished;

    public TextComponent(long id, String text, String author, Boolean republished, BackgroundSocket bgSocket,
            TextField input, Boolean sameUser) {
        this.text = new Text("@" + author + " : " + text);
        this.text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        this.text.setFill(Color.BLACK);
        this.getChildren().add(this.text);

        final Pane spacer = new Pane();
        spacer.setMinWidth(40);
        this.getChildren().add(spacer);

        RepostButton repostButton = new RepostButton(id, bgSocket);
        this.getChildren().add(repostButton);
        repostButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                input.setText("@" + author + " : " + text);
            }
        });

        ReplyButton replyButton = new ReplyButton(id, bgSocket);
        replyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                input.setText("Replying to " + author + " msgId " + id + " : ");
            }
        });

        UnSubscribeButton subscribeButton = new UnSubscribeButton(author, bgSocket);

        this.getChildren().add(replyButton);
        this.getChildren().add(subscribeButton);

        replyButton.setVisible(false);
        subscribeButton.setVisible(false);
        repostButton.setVisible(false);

        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-background-color: #f0f0f0;");
            if (sameUser) {
                replyButton.setVisible(false);
                subscribeButton.setVisible(false);
                repostButton.setVisible(false);
            } else {
                replyButton.setVisible(true);
                subscribeButton.setVisible(true);
                repostButton.setVisible(true);
            }
        });
        this.setOnMouseExited(event -> {
            this.setStyle("-fx-background-color: #FFFFFF;");
            replyButton.setVisible(false);
            subscribeButton.setVisible(false);
            repostButton.setVisible(false);
        });

        final Pane spacer2 = new Pane();
        spacer2.setMinWidth(530);
        this.getChildren().add(spacer2);

        this.setSpacing(10);

    }

    public String getText() {
        return this.text.getText();
    }

    public long getMsgId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Boolean getRepublished() {
        return republished;
    }

}
