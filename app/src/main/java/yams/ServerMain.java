package yams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.function.Function;

import yams.logger.Logger;
import yams.logger.StdOutLogger;
import yams.req.DummyReq;
import yams.req.Req;
import yams.req.ReqLoggerDecorator;

public class ServerMain {
    private static Logger logger = new StdOutLogger();

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
            Function<Req, Req> reqDecorator = (h) -> new ReqLoggerDecorator(h, logger);

            try {
                var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String header, body;
                while ((header = in.readLine()) != null && (body = in.readLine()) != null) {
                    Req req = new DummyReq(header, body);
                    reqDecorator.apply(req).handle(client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
