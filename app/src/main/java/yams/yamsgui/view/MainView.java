package yams.yamsgui.view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import yams.interaction.Interaction;
import yams.interaction.fluxreq.SubReq;
import yams.interaction.fluxreq.SubReq.User;
import yams.interaction.req.PubReq;
import yams.interaction.req.ReplyReq;
import yams.msg.Msg;
import yams.yamsgui.controller.SceneController;
import yams.yamsgui.model.BackgroundSocket;
import yams.yamsgui.view.component.TextComponent;

public class MainView extends Scene {

    private MessageBoxView messageView = new MessageBoxView();
    private BackgroundSocket bgSocket;
    private TextField input;

    public MainView(SceneController sceneController, BackgroundSocket bgSocket) {
        super(new GridPane(), 920, 720);
        this.bgSocket = bgSocket;
        GridPane grid = (GridPane) this.getRoot();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        TextField inputSub = new TextField();
        inputSub.setPromptText("Entrez le nom d'utilisateur au quel vous souhaitez vous abonner");
        grid.add(inputSub, 0, 0);

        Button sendSub = new Button("S'abonner");
        HBox hbBtnSub = new HBox(10);
        hbBtnSub.getChildren().add(sendSub);
        grid.add(hbBtnSub, 1, 0);

        sendSub.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (inputSub.getText() != bgSocket.getUsername()) {
                        User subReq = new SubReq.User(null, null, inputSub.getText());
                        bgSocket.sendToFlux((Interaction) subReq);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        grid.add(messageView, 0, 1, 2, 1);

        input = new TextField();
        input.setPromptText("Entrez votre message ici");
        grid.add(input, 0, 6, 2, 1);

        Button send = new Button("Envoyer");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(send);
        grid.add(hbBtn, 3, 6);

        input.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String message = input.getText();
                if (message.contains("Replying to")) {
                    String[] split = message.split(" ");
                    String msgId = split[4];
                    long id = Long.parseLong(msgId);
                    try {
                        messageView.addMessage(new TextComponent(message.length(), message, bgSocket.getUsername(),
                                false, bgSocket, input, true));
                        ReplyReq replyReq = new ReplyReq(null, bgSocket.getUsername(), id,
                                message.substring(message.indexOf(":") + 2));
                        bgSocket.send(replyReq);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    input.clear();
                }

                else if (!message.isEmpty()) {
                    messageView.addMessage(new TextComponent(message.length(), message, bgSocket.getUsername(), false,
                            bgSocket, input, true));
                    PubReq pubReq = new PubReq(null, bgSocket.getUsername(), message);
                    try {
                        bgSocket.send(pubReq);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    input.clear();
                }
            }
        });

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String message = input.getText();
                if (message.contains("Replying to")) {
                    String[] split = message.split(" ");
                    String msgId = split[4];
                    long id = Long.parseLong(msgId);
                    try {
                        messageView.addMessage(new TextComponent(message.length(), message, bgSocket.getUsername(),
                                false, bgSocket, input, true));
                        ReplyReq replyReq = new ReplyReq(null, bgSocket.getUsername(), id, message);
                        bgSocket.send(replyReq);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    input.clear();
                } else if (message.length() > 0) {
                    messageView.addMessage(new TextComponent(message.length(), message, bgSocket.getUsername(), false,
                            bgSocket, input, true));
                    PubReq pubReq = new PubReq(null, bgSocket.getUsername(), message);
                    try {
                        bgSocket.send(pubReq);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    input.clear();
                }
            }
        });

    }

    public void addMessage(Msg msg) {
        Platform.runLater(() -> {
            messageView.addMessage(
                    new TextComponent(msg.id(), msg.msg(), msg.user(), msg.republished(), this.bgSocket, input, false));
        });
    }

}
