package yams.interaction.fluxreq;

import java.io.IOException;
import java.net.Socket;

import yams.interaction.flux.FluxDb;
import yams.interaction.res.ErrRes;
import yams.interaction.res.OkRes;
import yams.msg.db.MsgDb;

public class SubReq {

    public static record User(MsgDb msgDb, FluxDb fluxDb, String author) implements FluxReq {

        @Override
        public String serializeHeader() {
            return "SUBSCRIBE author:@" + author;
        }

        @Override
        public String serializeBody() {
            return "";
        }

        @Override
        public void receive(Socket socket) throws IOException {
            if(msgDb.userExists(author)){
                fluxDb.subscribeToUser(author, socket);
                new OkRes().sendTo(socket.getOutputStream());
            }
            else{
                new ErrRes.UserNotFound().sendTo(socket.getOutputStream());
            } 
        }
    }

    public static record Tag(FluxDb fluxDb, String tag) implements FluxReq {

        @Override
        public String serializeHeader() {
            return "SUBSCRIBE tag:#" + tag;
        }

        @Override
        public String serializeBody() {
            return "";
        }

        @Override
        public void receive(Socket socket) throws IOException {
            fluxDb.subscribeToTag(tag, socket);
            new OkRes().sendTo(socket.getOutputStream());
        }
    }

}
