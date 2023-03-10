package yams.interaction.req;

import java.io.IOException;
import java.net.Socket;

import yams.interaction.Interaction;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public interface Req extends Interaction {
    Res handle();

    MsgDb db();

    @Override
    default void receive(Socket socket) throws IOException {
        Res res = handle();
        res.sendTo(socket.getOutputStream());
    }
}
