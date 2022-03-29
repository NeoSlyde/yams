package yams.yamsgui.model;

import java.io.IOException;
import java.net.Socket;
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
        this.username = username;

        new Thread(()-> {
            try {
                fluxSocket = new Socket(host, port);
                new ConnReq(null, username).sendTo(fluxSocket.getOutputStream());
                Interaction.getResponse(fluxSocket.getInputStream());
                while(true){
                    var res = Interaction.getResponse(fluxSocket.getInputStream());
                    if(res instanceof MsgRes && onReceiveMsg != null){
                        onReceiveMsg.accept(((MsgRes) res).msg());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ErrRes e) {
                e.printStackTrace();
            }
            
        });

        
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
}
