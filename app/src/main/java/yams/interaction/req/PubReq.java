package yams.interaction.req;

import yams.interaction.res.OkRes;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public record PubReq(MsgDb db, String author, String msg) implements Req {
    @Override
    public Res handle() {
        db.publish(author, msg);
        return new OkRes();
    }

    @Override
    public String serializeHeader() {
        return "PUBLISH author:@" + author;
    }

    @Override
    public String serializeBody() {
        return msg.replace('\n', ' ').replace('\r', ' ');
    }
}
