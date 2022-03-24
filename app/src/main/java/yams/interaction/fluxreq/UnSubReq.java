package yams.interaction.fluxreq;

import yams.interaction.res.OkRes;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public class UnSubReq {
    
    public static record User(MsgDb db, String author) implements FluxReq {

        @Override
        public Res handle() {
            //TODO
            return new OkRes();
        }

        @Override
        public String serializeHeader() {
            return "UNSUBSCRIBE author:@" + author;
        }

        @Override
        public String serializeBody() {
            return "";
        }
    }

    public static record Tag(MsgDb db, String tag) implements FluxReq {

        @Override
        public Res handle() {
            //TODO
            return new OkRes();
        }

        @Override
        public String serializeHeader() {
            return "UNSUBSCRIBE tag:#" + tag;
        }

        @Override
        public String serializeBody() {
            return "";
        }
    }

}
