package yams.yamsgui.model;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

import yams.interaction.Interaction;
import yams.interaction.fluxreq.ConnReq;
import yams.interaction.res.ErrRes;
import yams.interaction.res.MsgRes;
import yams.msg.Msg;

public class BackgroundSocket {
    Socket reqSocket;
    Socket fluxSocket;
    String username;
    Consumer<Msg> onReceiveMsg = null;


    public BackgroundSocket(String host, int port,String username) throws IOException {
        reqSocket = new Socket(host, port);
        fluxSocket = new Socket(host, port);
        
        this.username = username;

        new Thread(()-> {
            try {
                Scanner scanner = new Scanner(fluxSocket.getInputStream()).useDelimiter("\r\n");
                new ConnReq(null, username).sendTo(fluxSocket.getOutputStream());
                Interaction.getResponse(scanner);
                while(scanner.hasNext()){
                    var res = Interaction.getResponse(scanner);
                    if(res instanceof MsgRes && onReceiveMsg != null){
                        onReceiveMsg.accept(((MsgRes) res).msg());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ErrRes e) {
                e.printStackTrace();
            }
            
        }).start();

        
    }

    public String getUsername() {
        return username;
    }

    public void send(Interaction interaction) throws IOException{
        interaction.sendTo(reqSocket.getOutputStream());
    }

    public void setOnReceiveMsg(Consumer<Msg> onReceiveMsg) {
        this.onReceiveMsg = onReceiveMsg;
    }

    public void sendToFlux(Interaction interaction) throws IOException{
        interaction.sendTo(fluxSocket.getOutputStream());
    }
}
