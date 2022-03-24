package yams.interaction;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public interface Interaction {
    // Called on the *receiving* side (i.e the server)
    void receive(Socket socket) throws IOException;

    String serializeHeader();

    String serializeBody();

    // Called on the *sending* side (i.e the client)
    default public void sendTo(OutputStream out) throws IOException {
        out.write(this.serializeHeader().getBytes());
        out.write("\r\n".getBytes());
        out.write(this.serializeBody().getBytes());
        out.write("\r\n".getBytes());
    }
}
