package yams.interaction.fluxreq;

import yams.interaction.flux.FluxDb;

public class UnSubReq {
    
    public static record User(FluxDb fluxDb, String author) implements FluxReq {
        @Override
        public String serializeHeader() {
            return "UNSUBSCRIBE author:@" + author;
        }

        @Override
        public String serializeBody() {
            return "";
        }
    }

    public static record Tag(FluxDb fluxDb, String tag) implements FluxReq {
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
