package yams;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

import yams.interaction.parser.InteractionParser;
import yams.interaction.req.RcvIdsReq;
import yams.interaction.req.RepubReq;
import yams.interaction.res.ErrRes;
import yams.interaction.res.MsgIdsRes;

public class ReposterClient {
    public static void run(String host, int port) {
        try {
            var stdinScanner = new Scanner(System.in);
            var socket = new Socket(host, port);
            var out = socket.getOutputStream();
            var in = new Scanner(socket.getInputStream()).useDelimiter("\\r\\n");

            System.out.println("Started YAMS Reposter Client on " + host + ":" + port);

            System.out.print("Enter your name: ");
            String nickname = stdinScanner.nextLine();

            System.out.print("Enter a space separated list of users to repost: ");
            String[] users = stdinScanner.nextLine().split(" ");

            var parser = new InteractionParser(null,null);

            // Get IDs

            for (String user : users) {
                var idsReq = new RcvIdsReq(null,
                        Optional.of(user), Optional.empty(), Optional.empty(), Optional.of(48));

                idsReq.sendTo(out);
                var idsHeader = in.next().trim();
                var idsBody = in.next().trim();
                try {
                    var res = parser.parse(idsHeader, idsBody);
                    if (!(res instanceof MsgIdsRes))
                        throw new RuntimeException("Expected MsgIdsRes");
                    long[] msgIds = ((MsgIdsRes) res).ids();

                    for (long id : msgIds) {
                        var repubReq = new RepubReq(null, nickname, id);
                        repubReq.sendTo(out);
                        var repubHeader = in.next().trim();
                        var repubBody = in.next().trim();
                        try {
                            var repubRes = parser.parse(repubHeader, repubBody);
                            if ((repubRes instanceof ErrRes))
                                throw (ErrRes) repubRes;
                        } catch (Exception e) {
                            System.out.println("Error republishing message " + id + ": " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
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
