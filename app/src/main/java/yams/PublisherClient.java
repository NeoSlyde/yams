package yams;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import yams.interaction.parser.InteractionParser;
import yams.interaction.req.PubReq;
import yams.interaction.res.ErrRes;

public class PublisherClient {
    public static void run(String host, int port) {
        try {
            var stdinScanner = new Scanner(System.in);
            var socket = new Socket(host, port);
            var out = socket.getOutputStream();
            var in = new Scanner(socket.getInputStream()).useDelimiter("\\r\\n");

            System.out.println("Started YAMS Client on " + host + ":" + port);

            System.out.print("Enter your username: ");
            var user = stdinScanner.nextLine().trim();
            var parser = new InteractionParser(null, null);

            while (stdinScanner.hasNextLine()) {
                String msg = stdinScanner.nextLine().trim();
                var req = new PubReq(null, user, msg);
                req.sendTo(out);
                var header = in.next().trim();
                var body = in.next().trim();
                try {
                    var res = parser.parse(header, body);
                    System.out.println("HEADER: " + res.serializeHeader() + '\n' + "BODY: " + res.serializeBody());
                } catch (ErrRes e) {
                    System.out.println("ERROR! " + e.getMessage());
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
