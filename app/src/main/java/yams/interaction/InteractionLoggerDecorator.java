package yams.interaction;

import java.io.IOException;
import java.net.Socket;

public record InteractionLoggerDecorator(Interaction reqHandler) implements Interaction {
    @Override
    public void receive(Socket socket) throws IOException {
        reqHandler.receive(socket);
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
