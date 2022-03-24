package yams.interaction.fluxreq;

import yams.interaction.res.OkRes;
import yams.interaction.res.Res;
import yams.msg.db.MsgDb;

public class SubReq {

    public static record User(MsgDb db, String author) implements FluxReq {

        @Override
        public String serializeHeader() {
            return "SUBSCRIBE author:@" + author;
        }

        @Override
        public String serializeBody() {
            return "";
        }

        @Override
        public Res handle() {
            // TODO
            return new OkRes();
        }
    }

    public static record Tag(MsgDb db, String tag) implements FluxReq {

        @Override
        public String serializeHeader() {
            return "SUBSCRIBE tag:#" + tag;
        }

        @Override
        public String serializeBody() {
            return "";
        }

        @Override
        public Res handle() {
            // TODO
            return new OkRes();
        }
    }

}
