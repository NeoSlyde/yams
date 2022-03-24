package yams.interaction.req;

import yams.interaction.res.ErrRes;
import yams.interaction.res.OkRes;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public record ReplyReq(MsgDb db, String user, long replyToId, String msg) implements Req {
    @Override
    public String serializeHeader() {
        return "REPLY author:@" + user + " reply_to_id:" + replyToId;
    }

    @Override
    public String serializeBody() {
        return msg;
    }

    @Override
    public Res handle() {
        try {
            db.reply(user, replyToId);
        } catch (ErrRes e) {
            return e;
        }
        return new OkRes();
    }
}
