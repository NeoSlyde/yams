package yams.interaction;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import yams.interaction.parser.InteractionParser;
import yams.interaction.res.ErrRes;
import yams.interaction.res.Res;

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

    static public Res getResponse(InputStream in) throws IOException, ErrRes {
        try (Scanner scanner = new Scanner(in).useDelimiter("\r\n")) {
            String header = scanner.next();
            String body = scanner.next();
            InteractionParser parser = new InteractionParser(null, null);
            Interaction interaction = parser.parse(header, body);
            if(!(interaction instanceof Res)) {
                throw new IOException("Expected a Res, got a " + interaction.getClass().getSimpleName());
            }
            return (Res) interaction;
        }

    }
}
