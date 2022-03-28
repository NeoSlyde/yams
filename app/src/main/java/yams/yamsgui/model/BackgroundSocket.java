package yams.yamsgui.model;

import java.io.IOException;
import java.net.Socket;

public class BackgroundSocket {
    Socket socket;
    String username;

    public BackgroundSocket(String host, int port,String username) throws IOException {
        socket = new Socket(host, port);
        this.username = username;
    }
}
