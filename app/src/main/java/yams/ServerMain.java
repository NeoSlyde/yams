package yams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.function.Function;

import yams.interaction.Interaction;
import yams.interaction.InteractionLoggerDecorator;
import yams.interaction.parser.InteractionParser;
import yams.interaction.res.ErrRes;
import yams.logger.Logger;
import yams.logger.StdOutLogger;
import yams.msg.db.ListMsgDb;
import yams.msg.db.MsgDb;

public class ServerMain {
    private static Logger logger = new StdOutLogger();
    private static MsgDb msgDb = new ListMsgDb();

    public static void run(int port) {
        try {
            var pool = Executors.newFixedThreadPool(16);
            var server = new ServerSocket(port);
            logger.log("Started YAMS on localhost:" + port);
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
            Function<Interaction, Interaction> reqDecorator = (h) -> new InteractionLoggerDecorator(h, logger);

            try {
                var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String header, body;
                while ((header = in.readLine()) != null && (body = in.readLine()) != null) {
                    var parser = new InteractionParser(msgDb);
                    try {
                        Interaction req = parser.parse(header, body);
                        reqDecorator.apply(req).receive(client.getOutputStream());
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
