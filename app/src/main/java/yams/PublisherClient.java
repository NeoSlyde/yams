package yams;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import yams.interaction.parser.InteractionParser;
import yams.interaction.req.PubReq;
import yams.interaction.res.ErrRes;
import yams.logger.Logger;
import yams.logger.StdOutLogger;

public class PublisherClient {
    private static Logger logger = new StdOutLogger();

    public static void run(String host, int port) {
        try {
            var stdinScanner = new Scanner(System.in);
            var socket = new Socket(host, port);
            var out = socket.getOutputStream();
            var in = new Scanner(socket.getInputStream());

            logger.log("Started YAMS Client on " + host + ":" + port);

            logger.lognln("Enter your username: ");
            var user = stdinScanner.nextLine().trim();
            var parser = new InteractionParser(null);

            while (stdinScanner.hasNextLine()) {
                String msg = stdinScanner.nextLine().trim();
                var req = new PubReq(null, user, msg);
                req.sendTo(out);
                var header = in.nextLine().trim();
                var body = in.nextLine().trim();
                try {
                    var res = parser.parse(header, body);
                    logger.log("HEADER: " + res.serializeHeader(), "BODY: " + res.serializeBody());
                } catch (ErrRes e) {
                    logger.lognln("ERROR! " + e.getMessage());
                }
            }

            socket.close();
            stdinScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
