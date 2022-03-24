package yams.interaction.req;

import yams.interaction.res.ErrRes;
import yams.interaction.res.OkRes;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public record RepubReq(MsgDb db, String user, long republishId, String msg) implements Req {
    @Override
    public String serializeHeader() {
        return "REPLY author:@" + user + " msg_id:" + republishId;
    }

    @Override
    public String serializeBody() {
        return msg;
    }

    @Override
    public Res handle() {
        try {
            db.republish(user, republishId);
        } catch (ErrRes e) {
            return e;
        }
        return new OkRes();
    }
}
