package yams.msg;

import java.net.Socket;

public record MsgPrinterDecorator(Msg msgHandler) implements Msg {
    @Override
    public void handle(Socket socket) {
        msgHandler.handle(socket);
        System.out.println("HEADER: " + serializeHeader());
        System.out.println("BODY: " + serializeBody());
        System.out.println();
    }

    @Override
    public String serializeHeader() {
        return msgHandler.serializeHeader();
    }

    @Override
    public String serializeBody() {
        return msgHandler.serializeBody();
    }
}
