package yams.interaction.fluxreq;


import yams.interaction.flux.FluxDb;

public record ConnReq(FluxDb fluxDb, String user) implements FluxReq {
    @Override
    public String serializeHeader() {
        return "CONNECT user:@" + user;
    }

    @Override
    public String serializeBody() {
        return "";
    }
}
