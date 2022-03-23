package yams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.function.Function;

import yams.msg.DummyMsg;
import yams.msg.Msg;
import yams.msg.MsgPrinterDecorator;

public class ServerMain {
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
        private static Function<Msg, Msg> msgHandlerDecorator = (h) -> new MsgPrinterDecorator(h);

        @Override
        public void run() {
            try {
                var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String header, body;
                while ((header = in.readLine()) != null && (body = in.readLine()) != null) {
                    Msg msgHandler = new DummyMsg(header, body);
                    msgHandlerDecorator.apply(msgHandler).handle(client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
