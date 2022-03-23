package yams.msg;

import java.net.Socket;

public interface Msg {
    public void handle(Socket socket);

    public String serializeHeader();

    public String serializeBody();
}
