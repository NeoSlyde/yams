package yams;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.function.Function;

import yams.interaction.Interaction;
import yams.interaction.InteractionLoggerDecorator;
import yams.interaction.parser.InteractionParser;
import yams.interaction.res.ErrRes;
import yams.msg.db.ListMsgDb;
import yams.msg.db.MsgDb;

public class ServerMain {
    private static MsgDb msgDb = new ListMsgDb();

    public static void run(int port) {
        try {
            var pool = Executors.newFixedThreadPool(16);
            var server = new ServerSocket(port);
            System.out.println("Started YAMS on localhost:" + port);
            while (!server.isClosed()) {
                var client = server.accept();
                pool.execute(new ConnHandler(client));
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private record ConnHandler(Socket client) implements Runnable {
        @Override
        public void run() {
            Function<Interaction, Interaction> reqDecorator = (h) -> new InteractionLoggerDecorator(h);

            try (var in = new Scanner(client.getInputStream()).useDelimiter("\\r\\n")) {
                while (true) {
                    if (!in.hasNext())
                        break;
                    String header = in.next();
                    if (!in.hasNext())
                        break;

                    String body = in.next();
                    var parser = new InteractionParser(msgDb);
                    try {
                        Interaction req = parser.parse(header, body);
                        reqDecorator.apply(req).receive(client);
                    } catch (ErrRes e) {
                        e.sendTo(client.getOutputStream());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
