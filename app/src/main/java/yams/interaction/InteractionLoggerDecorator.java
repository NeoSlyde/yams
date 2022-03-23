package yams.interaction;

import java.io.IOException;
import java.io.OutputStream;

import yams.logger.Logger;

public record InteractionLoggerDecorator(Interaction reqHandler, Logger logger) implements Interaction {
    @Override
    public void receive(OutputStream out) throws IOException {
        reqHandler.receive(out);
        logger.log("HEADER: " + serializeHeader(), "BODY: " + serializeBody(), "");
    }

    @Override
    public String serializeHeader() {
        return reqHandler.serializeHeader();
    }

    @Override
    public String serializeBody() {
        return reqHandler.serializeBody();
    }
}
