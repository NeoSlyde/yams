package yams.interaction.req;

import yams.interaction.res.ErrRes;
import yams.interaction.res.MsgRes;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public record RcvMsg(MsgDb db, long id) implements Req {
    @Override
    public String serializeHeader() {
        return "RCV_MSG msg_id:" + id;
    }

    @Override
    public String serializeBody() {
        return "";
    }

    @Override
    public Res handle() {
        return db.getById(id).<Res>map(MsgRes::new).orElse(new ErrRes.UnknownMsgId());
    }
}
