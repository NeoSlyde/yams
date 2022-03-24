package yams.interaction.fluxreq;

import java.io.IOException;
import java.net.Socket;

import yams.interaction.Interaction;
import yams.interaction.flux.FluxDb;

public interface FluxReq extends Interaction {
    FluxDb fluxDb();

    @Override
    default void receive(Socket socket) throws IOException {
        //TODO
    }
}
