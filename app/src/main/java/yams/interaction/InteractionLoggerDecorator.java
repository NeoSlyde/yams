package yams.interaction;

import java.io.IOException;
import java.io.OutputStream;

public record InteractionLoggerDecorator(Interaction reqHandler) implements Interaction {
    @Override
    public void receive(OutputStream out) throws IOException {
        reqHandler.receive(out);
        System.out.println("HEADER: " + serializeHeader() + '\n' + "BODY: " + serializeBody() + '\n');
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
