package yams.msg;

import java.net.Socket;

public record DummyMsg(String header, String body) implements Msg {
    @Override
    public void handle(Socket socket) {
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
