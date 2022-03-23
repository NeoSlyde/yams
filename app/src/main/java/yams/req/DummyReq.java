package yams.req;

import java.net.Socket;

public record DummyReq(String header, String body) implements Req {
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
