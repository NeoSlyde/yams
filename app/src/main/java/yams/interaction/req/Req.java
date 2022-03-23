package yams.interaction.req;

import java.io.IOException;
import java.io.OutputStream;

import yams.interaction.Interaction;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public interface Req extends Interaction {
    Res handle();

    MsgDb db();

    @Override
    default void receive(OutputStream out) throws IOException {
        Res res = handle();
        res.sendTo(out);
    }
}
