package yams.req;

import java.net.Socket;

public interface Req {
    public void handle(Socket socket);

    public String serializeHeader();

    public String serializeBody();
}
