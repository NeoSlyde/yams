package yams.interaction.fluxreq;

import yams.interaction.flux.FluxDb;

public class SubReq {

    public static record User(FluxDb fluxDb, String author) implements FluxReq {

        @Override
        public String serializeHeader() {
            return "SUBSCRIBE author:@" + author;
        }

        @Override
        public String serializeBody() {
            return "";
        }
    }

    public static record Tag(FluxDb fluxDb, String tag) implements FluxReq {

        @Override
        public String serializeHeader() {
            return "SUBSCRIBE tag:#" + tag;
        }

        @Override
        public String serializeBody() {
            return "";
        }
    }

}
