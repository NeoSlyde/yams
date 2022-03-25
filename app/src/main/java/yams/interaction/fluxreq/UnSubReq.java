package yams.interaction.fluxreq;

import java.io.IOException;
import java.net.Socket;

import yams.interaction.flux.FluxDb;
import yams.interaction.res.ErrRes;
import yams.interaction.res.OkRes;

public class UnSubReq {
    
    public static record User(FluxDb fluxDb, String author) implements FluxReq {
        @Override
        public String serializeHeader() {
            return "UNSUBSCRIBE author:@" + author;
        }

        @Override
        public String serializeBody() {
            return "";
        }

        @Override
        public void receive(Socket socket) throws IOException {
            if(fluxDb.isSubscribedToUser(author, socket)){
                fluxDb.unsubscribeFromUser(author, socket);
                new OkRes().sendTo(socket.getOutputStream());
            }
            else{
                new ErrRes.NotSubscribed().sendTo(socket.getOutputStream());
            }
        }
    }

    public static record Tag(FluxDb fluxDb, String tag) implements FluxReq {
        @Override
        public String serializeHeader() {
            return "UNSUBSCRIBE tag:#" + tag;
        }

        @Override
        public String serializeBody() {
            return "";
        }

        @Override
        public void receive(Socket socket) throws IOException {
            if(fluxDb.isSubscribedToTag(tag, socket)){
                fluxDb.unsubscribeFromTag(tag, socket);
                new OkRes().sendTo(socket.getOutputStream());
            }
            else{
                new ErrRes.NotSubscribed().sendTo(socket.getOutputStream());
            }
        }
        
    }

}
