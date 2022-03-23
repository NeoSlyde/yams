package yams.interaction;

import java.io.OutputStream;

public record DummyInteraction(String header, String body) implements Interaction {
    @Override
    public void receive(OutputStream out) {
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
