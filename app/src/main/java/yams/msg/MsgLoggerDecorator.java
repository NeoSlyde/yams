package yams.msg;

import java.net.Socket;

import yams.logger.Logger;

public record MsgLoggerDecorator(Msg msgHandler, Logger logger) implements Msg {
    @Override
    public void handle(Socket socket) {
        msgHandler.handle(socket);
        logger.log("HEADER: " + serializeHeader(), "BODY: " + serializeBody(), "");
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
