package yams.req;

import java.net.Socket;

import yams.logger.Logger;

public record ReqLoggerDecorator(Req reqHandler, Logger logger) implements Req {
    @Override
    public void handle(Socket socket) {
        reqHandler.handle(socket);
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
