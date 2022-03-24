package yams.interaction.fluxreq;


import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public record ConnReq(MsgDb db, String user) implements FluxReq {
    @Override
    public String serializeHeader() {
        return "CONNECT user:@" + user;
    }

    @Override
    public String serializeBody() {
        return "";
    }

    @Override
    public Res handle() {
        //TODO
        return null;
    }
}
