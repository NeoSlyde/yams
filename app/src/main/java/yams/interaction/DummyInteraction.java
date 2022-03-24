package yams.interaction;

import java.net.Socket;

public record DummyInteraction(String header, String body) implements Interaction {
    @Override
    public void receive(Socket socket) {
    }

    @Override
    public String serializeHeader() {
        return header;
    }

    @Override
    public String serializeBody() {
        return body;
    }
}
