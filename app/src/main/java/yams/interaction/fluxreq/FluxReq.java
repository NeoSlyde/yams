package yams.interaction.fluxreq;

import java.io.IOException;
import java.net.Socket;

import yams.interaction.Interaction;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public interface FluxReq extends Interaction {
    Res handle();

    MsgDb db();

    @Override
    default void receive(Socket socket) throws IOException {
        //TODO
    }
}
